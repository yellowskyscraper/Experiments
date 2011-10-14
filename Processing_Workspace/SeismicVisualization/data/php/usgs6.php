<?php    

	function distance($lon1, $lat1, $lon2, $lat2) 
	{           
		$theta = $lon1 - $lon2; 
		$dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta)); 
		$dist = acos($dist); 
		$dist = rad2deg($dist); 
		$miles = $dist * 60 * 1.1515;
		$unit = strtoupper($unit);

		return $miles;      
	}    	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////| DB Log
          
    function element_start($sax, $tag, $attr)
	{         
		global $placemark;  
		global $name;         
		global $hold_name;
		global $coordinates;     
		                                                                      
		if($tag === 'Placemark') $placemark = TRUE;   
		if($placemark == TRUE && $tag === 'name') $name = TRUE;  
		if($placemark == TRUE && $tag === 'coordinates') $coordinates = TRUE; 
	}  

	function element_data($sax, $data) 
	{    
		global $placemark;  
		global $name;         
		global $hold_name;
		global $coordinates;                               

		if($placemark == TRUE && $name == TRUE) $hold_name = $data;
		if($placemark == TRUE && $coordinates == TRUE)
		{     
			$lonlat = explode(',', $data);  
			$dist = distance($lonlat[0], $lonlat[1], -122.417253, 37.78554); 
			     
			if($dist <= 50)
			{ 
				$name_array = explode(' ', $hold_name);
				$name = explode(',', $hold_name);                                                        
				echo '<b>' . $name_array[1] . ' : '. $name_array[3] . ' : ' . $name_array[4] . ' : ' . str_replace(',', '', $name_array[5]) . ' : ' . $name[1] . '</b> ('. $lonlat[0] .', '. $lonlat[1] .' )<br>';  
				$sql = 'INSERT INTO local_50 (Magnitude, Year, Month, Day, Location, Lon, Lat) VALUES ("'.$name_array[1].'", "'.$name_array[3].'", "'.$name_array[4].'", "'.str_replace(',', '', $name_array[5]).'", "'.$name[1].'", '.$lonlat[0].', '. $lonlat[1].')';
				mysql_query($sql);
			}       
			$hold_name = 'empty';
		} 

	}    
	
	function element_end($sax, $tag)
	{  
		global $placemark;  
		global $name;         
		global $hold_name;
		global $coordinates; 
		global $db;     
		                                                                                                          
		if($tag === 'Placemark') $placemark = FALSE;      
		if($placemark == TRUE && $tag === 'name') $name = FALSE;
		if($placemark == TRUE && $tag === 'coordinates') $coordinates = FALSE;   
		if($tag === 'kml') mysql_close($db); 
	}   
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////| DB Log
	
	$db_name = 'Seismic_Local';
	include 'includes/database_connect/'.$db_name.'/db_connect.php'; 
	
	$db = mysql_connect($db_hostname,$db_login,$db_password) or die("Could not connect to the server.");
	mysql_select_db($db_name, $db) or die("Could not connect to the database.");

	$charset = 'utf8';
	if (!function_exists('mysql_set_charset'))
	{
		function mysql_set_charset($charset,$db)
		{                        
			return mysql_query("set names $charset",$db);
		}   
	}   
	mysql_set_charset('utf8',$db);
      
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////| XML Parser   
	  
	$year = $_GET['year'];                               
	$placemark = FALSE;  
	$name = FALSE;          
	$hold_name = 'empty';
	$coordinates = FALSE;
	
	$sax = xml_parser_create();
	xml_parser_set_option($sax, XML_OPTION_CASE_FOLDING, false);
	xml_parser_set_option($sax, XML_OPTION_SKIP_WHITE, true);   
    xml_set_element_handler($sax, 'element_start', 'element_end');
	xml_set_character_data_handler($sax, 'element_data');      
	xml_parse($sax, file_get_contents('../seismic/'. $year .'_Earthquakes_ALL.kml'), true);
	xml_parser_free($sax);
?>