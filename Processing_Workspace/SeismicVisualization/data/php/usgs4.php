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
		global $year;  
		 
		/*if($tag === 'kml' && $year == 0) echo '<?xml version="1.0" encoding="UTF-8"?><kml>';   */
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
			//$lonlat = explode(',', $data);   
			//$dist = distance($lonlat[0], $lonlat[1], -122.417253, 37.78554);   
			echo '<b>'. $hold_name .'</b><br>';
			//if($dist <= 50) echo '<b>'. $hold_name .'</b> ('. $lonlat[0] .', '. $lonlat[1] .') <br>';        
			//if($dist <= 50) echo '<quake name="' . $hold_name .'"><lon>'. $lonlat[0] .'</lon><lat>'. $lonlat[1] .'</lat></quake>';      
			$hold_name = 'empty';
		}  
	}   
	
	function element_end($sax, $tag)
	{  
		global $placemark;  
		global $name;         
		global $hold_name;
		global $coordinates; 
		global $year;
		global $years;   
		
		//if($tag === 'kml') echo '</kml>';                                        
		if($tag === 'Placemark') $placemark = FALSE;      
		if($placemark == TRUE && $tag === 'name') $name = FALSE;
		if($placemark == TRUE && $tag === 'coordinates') $coordinates = FALSE;
		
		if($tag === 'kml') {  
			$year += 1; 
			//if($year > (count($years)-1)) echo '</kml>';                 
			next_year();     
		}
	}       
	
	function next_year()
	{          
		global $year;
		global $years;   
		        
		//if($year > count($years)-1) return;
		echo '<h1>Load File: '. $year . ' of ' . (count($years) - 1) . '</h1>';    
		                          
		$sax = xml_parser_create();
		xml_parser_set_option($sax, XML_OPTION_CASE_FOLDING, false);
		xml_parser_set_option($sax, XML_OPTION_SKIP_WHITE, true);   
	    xml_set_element_handler($sax, 'element_start', 'element_end');
		xml_set_character_data_handler($sax, 'element_data');         
		xml_parse($sax, file_get_contents($years[$year]), true);  
		xml_parser_free($sax);    
	}  
	                                 
	$placemark = FALSE;  
	$name = FALSE;          
	$hold_name = 'empty';
	$coordinates = FALSE;          
	     
	$years = array('../seismic/2000_Earthquakes_ALL.kml', '../seismic/2001_Earthquakes_ALL.kml', '../seismic/2002_Earthquakes_ALL.kml', '../seismic/2003_Earthquakes_ALL.kml', '../seismic/2004_Earthquakes_ALL.kml', '../seismic/2005_Earthquakes_ALL.kml', '../seismic/2006_Earthquakes_ALL.kml', '../seismic/2007_Earthquakes_ALL.kml', '../seismic/2008_Earthquakes_ALL.kml', '../seismic/2009_Earthquakes_ALL.kml', '../seismic/2010_Earthquakes_ALL.kml');
	
	$year = 0;  
    next_year();
?>