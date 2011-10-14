<?php                          
	$db_hostname  = "neon.exploratorium.edu";     
	$db_login     = "jhovell";
	$db_password  = "jho123&*"; 
	$db_name = 'Seismic_Local';
	         
	$db = mysql_connect($db_hostname,$db_login,$db_password) or die("Could not connect to the server.");
	mysql_select_db($db_name, $db) or die("Could not connect to the database.");

	$charset = 'utf8';
	if (!function_exists('mysql_set_charset')) {
		function mysql_set_charset($charset,$db) {                          
			return mysql_query("set names $charset",$db);
		} 
	}
	mysql_set_charset('utf8',$db);

	$sql = "select * from local_50 order by Year";	
	$result = mysql_query($sql); 
	$count = 1; 
	echo '<?xml version="1.0" encoding="UTF-8"?><kml>';
	
	while ($record = mysql_fetch_assoc($result)) {  
		$magnitude = $record["Magnitude"]; 
		$year = $record["Year"];
		$month = $record["Month"];
		$day = $record["Day"];
		$location = $record["Location"]; 
		$lon = $record["Lon"];
		$lat = $record["Lat"];  
		echo '<quake name="M '.$magnitude.' - '.$year.' '.$month.' '.$day.','.$location.'"><lon>'.$lon.'</lon><lat>'.$lat.'</lat></quake>'; 
		//echo $count . ' <b>' . $magnitude . ' '. $year . ' ' . $month . ' ' . $day . ' ' . $location . '</b><br>';
		$count += 1; 
	}                
	
	echo '</kml>';   
	mysql_close($db);
?>