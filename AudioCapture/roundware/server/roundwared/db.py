import MySQLdb
import string
import logging
from roundwared import settings

def create_session (udid):
	dbh = get_connection()
	cursor = dbh.cursor()
	cursor.execute ("insert into session (udid) values ('"+udid+"')")
	rowid = cursor.lastrowid
	cursor.close ()
	dbh.close ()
	return rowid

def log_event (eventtypeid, form):
	scalar_columns = [
		"sessionid",
		"clienttime",
		"latitude",
		"longitude",
		"course",
		"haccuracy",
		"speed",
		"message",
	]
	list_columns = [
		"demographicid",
		"genderid",
		"ageid",
		"usertypeid",
		"questionid",
	]

	columns = ['eventtypeid']
	values = [str(eventtypeid)]

	for c in scalar_columns:
		if form.has_key(c):
			columns.append(c)
			values.append("'"+form[c]+"'")

	for c in list_columns:
		if form.has_key(c):
			columns.append(c)
			v = string.replace(form[c],"\t",",")
			values.append("'"+v+"'")
		
	sql = "insert into event (" \
		+ ",".join(columns) \
		+ ") values (" \
		+ ",".join(values) \
		+ ")"

	logging.debug(sql)
	dbh = get_connection()
	cursor = dbh.cursor()
	cursor.execute (sql)
	cursor.close()
	dbh.close()
	return True

def get_demographics (projectname = None):
	dbh = get_connection()
	projectid = get_projectid(dbh, projectname)
	if projectid: projectidsql = " where projectid = " + str(projectid)
	else: projectidsql = " where projectid is null"
	usertypesql = "select * from usertype" + projectidsql
	agesql = "select * from age" + projectidsql
	gendersql = "select * from gender"
	demographicsql = "select * from demographic"
	#TODO: Change this a client to accept usertypeid instead of usertypes
	result = {
		"usertypes" : sql_table_dict(dbh, usertypesql),
		"ages" : sql_table_dict(dbh, agesql),
		"genders" : sql_table_dict(dbh, gendersql),
		"demographics" : sql_table_dict(dbh, demographicsql),
	}
	dbh.close()
	return result

def get_categories (projectname = None):
	dbh = get_connection()
	projectid = get_projectid(dbh, projectname)
	categorysql = "select * from category where activeyn = 'Y'"
	if projectid: categorysql += "and projectid = " + str(projectid)
	else: categorysql += "and projectid is null"
	
	categories = sql_table_dict(dbh, categorysql)
	for category in categories:
		subcategorysql = """
			select
				subcategory.*,
				case when artist.id is null
				then ''
				else concat(artist.firstname, ' ', artist.lastname) end artist_name
			from
				subcategory
				left outer join artist on artist.id = subcategory.artistid
			where
				subcategory.categoryid = """ + str(category["id"]) + """
			order by
				subcategory.ordering
		"""
		questionssql = "select * from question where categoryid = " + str(category["id"])
		category["subcategories"] = sql_table_dict(dbh, subcategorysql)
		category["questions"] = sql_table_dict(dbh, questionssql)
	dbh.close()
	return categories

def get_questions (categoryids, subcategoryids):
	dbh = get_connection()
	questionsql = """
		select id, text, categoryid, subcategoryid, randordering, listenyn, speakyn, @rownum:=@rownum+1 'ordering'
		from question_v, (SELECT @rownum:=9) r
		where question_v.categoryid in (""" + ",".join(map(str,categoryids)) + """)
		and question_v.subcategoryid in (""" + ",".join(map(str,subcategoryids)) + """)
		order by question_v.randordering"""
	return sql_table_dict(dbh, questionsql)

def get_projectid (dbh, projectname):
	if projectname:
		return sql_value(dbh, "select id from project where name = '" + projectname + "'")
	else:
		return None

def get_recording_filename (recordingid):
	dbh = get_connection()
	filename = sql_value(dbh, "select filename from recording where id = " + str(recordingid))
	dbh.close()
	return filename

def store_recording (user, request, filename):
	conn = get_connection()
	cursor = conn.cursor()
	columns = ["filename"]
	values = ["'"+filename+"'"]
	for key in user.keys():
		if key == 'demographicid':
			columns.append('ageid')
			values.append("(select ageid from demographic where id = " + str(user[key]) + ")")
			columns.append('genderid')
			values.append("(select genderid from demographic where id = " + str(user[key]) + ")")
		elif user[key]:
			columns.append(key)
			if type(user[key]) is str:
				values.append("'"+user[key]+"'")
			else:
				values.append(str(user[key]))
	for key in request.keys():
		if request[key]:
			columns.append(key)
			values.append(str(request[key]))
	cursor.execute ("insert into recording (" + ",".join(columns) + ") values (" + ",".join(values) + ")")
	id = cursor.lastrowid
	cursor.close ()
	conn.close ()
	return id

def update_audiolength (filename, audiolength):
	conn = get_connection()
	cursor = conn.cursor()
	cursor.execute("update recording set audiolength = " \
		+ str(audiolength) + " where filename = '" + filename + "'")
	cursor.close ()
	conn.close ()

def submit_recording(recordingid):
	conn = get_connection()
	cursor = conn.cursor()
	cursor.execute ("update recording set submittedyn = 'Y' where id = " + str(recordingid))
	cursor.close()
	conn.close()
	
def get_recordings (request):
	dbh = get_connection()

	sql = "select * from recording where submittedyn = 'Y' and audiolength is not null"
	for p in ['categoryid', 'subcategoryid', 'questionid', 'usertypeid', 'genderid', 'ageid']:
		if len(request[p]) > 0:
			sql += " and " + p + " in (" + ",".join(map(str, request[p])) + ")"

	if len(request['demographicid']) > 0:
		sql += """ and exists (
			select 1
			from demographic
			where demographic.ageid = recording.ageid
			and demographic.genderid = recording.genderid
			and demographic.id in ("""+",".join(map(str, request['demographicid']))+"))"
	logging.debug("and now request is...")
	logging.debug(request)
	logging.debug(sql)
	files = sql_table_dict(dbh, sql)
	dbh.close()
	return files

def number_of_recordings (request):
	return len(get_recordings(request))

def sql_list(strs):
	def stringify (str):
		return "\"" + str + "\""
	return "(" + string.join(map(stringify, strs),",") + ")"

def get_music_settings (categoryid):
	conn = get_connection()
	cursor = conn.cursor(MySQLdb.cursors.DictCursor)
	cursor.execute ("select * from category where id = " + str(categoryid))
	row = cursor.fetchone()
	cursor.close()
	conn.close()
	return row

def get_speakers (categoryids):
	conn = get_connection()
	cursor = conn.cursor (MySQLdb.cursors.DictCursor)
	sql = "select * from speaker where categoryid in (" \
		+ ",".join(map(str,categoryids)) \
		+ ") and ifnull(activeyn, 'N') = 'Y'"
	logging.debug(sql)
	cursor.execute (sql)
	rows = cursor.fetchall()
	cursor.close()
	return rows

def get_composition_settings (categoryids):
	conn = get_connection()
	cursor = conn.cursor (MySQLdb.cursors.DictCursor)
	cursor.execute ("select * from composition where categoryid in ("
		+ ",".join(map(str,categoryids)) + ")")
	rows = cursor.fetchall()
	cursor.close()
	return rows

def insert_event (form):
	def quotify (s):
		if s: return "'" + s + "'"
		else: return "null"
	def sqlify (v):
		if v: return v
		else: return "null"

	columns = [
		'operationid',
		'udid',
		'sessionid',
		'servertime',
		'clienttime',
		'latitude',
		'longitude',
		'demographicid',
		'questionid',
		'course',
		'haccuracy',
		'speed',
	]

	values = [
		sqlify(form.getvalue('operationid')),
		quotify(form.getvalue('udid')),
		quotify(form.getvalue('sessionid')),
		'now()',
		quotify(form.getvalue('clienttime')),
		quotify(form.getvalue('latitude')),
		quotify(form.getvalue('longitude')),
		quotify(form.getvalue('demographicid')),
		quotify(form.getvalue('questionid')),
		quotify(form.getvalue('course')),
		quotify(form.getvalue('haccuracy')),
		quotify(form.getvalue('speed')),
	]

	q = "insert into event (" + ",".join(columns) + ") values (" \
		+ ",".join(values) + ")"
	conn = get_connection()
	cursor = conn.cursor()
	cursor.execute(q)
	id = cursor.lastrowid
	cursor.close()
	conn.close()
	return id


def get_connection ():
	return MySQLdb.connect (
		host = "localhost",
		user = settings.config["dbuser"],
		passwd = settings.config["dbpasswd"],
		db = settings.config["dbname"])

#FIXME: Abstract with sql_dict
def sql_values (dbh, sql):
	cursor = dbh.cursor()
	cursor.execute(sql)
	row = cursor.fetchone()
	cursor.close()
	return row

def sql_value (dbh, sql):
	row = sql_values(dbh, sql)
	if row:
		return row[0]
	else:
		return None

#FIXME: Abstract with sql_table_dict
def sql_table_values (dbh, sql):
	cursor = dbh.cursor()
	cursor.execute(sql)
	rows = []
	while (True):
		row = cursor.fetchone()
		if row == None: break
		rows.append(row)
	cursor.close()
	return rows

def sql_column_value (dbh, sql):
	data = sql_table_values(dbh, sql)
	if data:
		return map(lambda row: row[0], data)
	else:
		return None

#FIXME: Abstract with sql_values
def sql_dict (dbh, sql):
	conn = get_connection()
	cursor = conn.cursor(MySQLdb.cursors.DictCursor)
	cursor.execute (sql)
	row = cursor.fetchone()
	cursor.close()
	conn.close()
	return row

#FIXME: Abstract with sql_table_values
def sql_table_dict (dbh, sql):
	cursor = dbh.cursor(MySQLdb.cursors.DictCursor)
	cursor.execute(sql)
	rows = []
	while (True):
		row = cursor.fetchone()
		if row == None: break
		rows.append(row)
	cursor.close()
	return rows

