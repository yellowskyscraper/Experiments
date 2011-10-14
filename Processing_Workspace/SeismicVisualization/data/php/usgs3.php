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
          
    function element_start($sax, $tag, $attr)
	{         
		global $placemark;  
		global $name;         
		global $hold_name;
		global $coordinates;     
		                    
		if($tag === 'kml') echo '<?xml version="1.0" encoding="UTF-8"?><kml>';
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
			$lonlat = explode(",", $data);   
			$dist = distance($lonlat[0], $lonlat[1], -122.417253, 37.78554);      
			if($dist <= 50) echo '<quake name="' . $hold_name .'"><lon>'. $lonlat[0] .'</lon><lat>'. $lonlat[1] .'</lat></quake>';      
			$hold_name = 'empty';
		} 

	}    
	
	function element_end($sax, $tag)
	{  
		global $placemark;  
		global $name;         
		global $hold_name;
		global $coordinates;      
		
		if($tag === 'kml') echo '</kml>';                                        
		if($tag === 'Placemark') $placemark = FALSE;      
		if($placemark == TRUE && $tag === 'name') $name = FALSE;
		if($placemark == TRUE && $tag === 'coordinates') $coordinates = FALSE; 
	}   
	 
	$year = '2010';                                
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