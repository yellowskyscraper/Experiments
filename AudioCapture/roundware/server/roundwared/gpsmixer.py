import gobject
gobject.threads_init()
import pygst
pygst.require("0.10")
import gst
import logging
import math
import httplib
import urlparse
from roundwared import gnomevfsmp3src

class GPSMixer (gst.Bin):
	def __init__ (self, listener, speakers):
		gst.Bin.__init__(self)
		self.sources = []
		self.speakers = []
		adder = gst.element_factory_make("adder")
		self.add(adder)
		pad = adder.get_pad("src")
		ghostpad = gst.GhostPad("src", pad)
		self.add_pad(ghostpad)
		for speaker in speakers:
			vol = calculate_volume(speaker, listener)
			uri = None
			if check_stream(speaker['uri']):
				uri = speaker['uri']
				#logging.debug("taking normal uri")
			elif check_stream(speaker['backupuri']):
				uri = speaker['backupuri']
				logging.warning("Stream " + speaker['uri'] \
					+ " is not a valid audio/mpeg stream." \
					+ " using backup.")
			else:
				logging.warning("Stream " + speaker['uri'] \
					+ " and backup " \
					#+ speaker['backupuri'] \
					+ " are not valid audio/mpeg streams." \
					+ " Not adding anything.")
				continue
			src = gnomevfsmp3src.GnomeVFSMP3Src(uri, vol)
			self.add(src)
			srcpad = src.get_pad('src')
			addersinkpad = adder.get_request_pad('sink%d')
			srcpad.link(addersinkpad)
			self.speakers.append(speaker)
			self.sources.append(src)
		self.move_listener(listener)

	def move_listener (self, new_listener):
		self.listener = new_listener
		for i in range(len(self.speakers)):
			vol = calculate_volume(self.speakers[i], self.listener)
			self.sources[i].set_volume(vol)
				
# FIXME: Attenuation is linear.
def calculate_volume (speaker, listener):
	distance = distance_in_meters(
		listener['latitude'],
		listener['longitude'],
		speaker['latitude'],
		speaker['longitude'])
	vol = 0
	if (distance <= speaker['mindistance']):
		vol = speaker['maxvolume']
	elif (distance >= speaker['maxdistance']):
		vol = speaker['minvolume']
	else:
		vol_frac = math.pow(
			2,
			lg(distance/speaker['mindistance']))
		vol = speaker['maxvolume'] / vol_frac
	#logging.debug(
	#	"Speaker: id=" + str(speaker['id']) + \
	#	" uri=" + speaker['uri'] + \
	#	" distance=" + str(distance) + \
	#	" volume=" + str(vol))
	return vol

def lg (x):
	return math.log(x) / math.log(2)

def distance_in_meters (lat1, lon1, lat2, lon2):
	return distance_in_km(lat1, lon1, lat2, lon2) * 1000

def distance_in_km (lat1, lon1, lat2, lon2):
	R = 6371
	dLat = math.radians(lat2-lat1)
	dLon = math.radians(lon2-lon1)
	a = math.sin(dLat/2) * math.sin(dLat/2) + \
		math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) * \
		math.sin(dLon/2) * math.sin(dLon/2)
	c = 2 * math.atan2(math.sqrt(a), math.sqrt(1-a))
	d = R * c
	return d

def check_stream(url):
        try:
                o = urlparse.urlparse(url)
                h = httplib.HTTPConnection(o.hostname, o.port, timeout=10)
                h.request('GET', o.path)
                r = h.getresponse()
                content_type = r.getheader('content-type')
                h.close()
                return content_type == 'audio/mpeg'
        except:
                return False

