<?php      
	function distance($lat1, $lon1, $lat2, $lon2) 
	{           
		$theta = $lon1 - $lon2; 
		$dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta)); 
		$dist = acos($dist); 
		$dist = rad2deg($dist); 
		$miles = $dist * 60 * 1.1515;
		$unit = strtoupper($unit);
                  
		return $miles;   
	}
	
//| USGS Parser                                   
	$xml = ('../seismic/2011_Earthquakes_ALL.kml');
	$xmlDoc = new DOMDocument();
	$xmlDoc->load($xml);                                                                     
	                                            
	$folder_count = $xmlDoc->getElementsByTagName('Folder')->length;     
	for ($i = 0; $i <= ($folder_count-1); $i++) {                       
		
		$folder = $xmlDoc->getElementsByTagName('Folder')->item($i)->getElementsByTagName('name')->item(0)->nodeValue;      
		echo '<Magnitude title="' . $folder . '">';
		//echo $folder . " begin: <br>";    
		 
		$placemark_count = $xmlDoc->getElementsByTagName('Folder')->item($i)->getElementsByTagName('Placemark')->length; 
		for ($j = 0; $j <= ($placemark_count-1); $j++) 
		{       
			$placemark = $xmlDoc->getElementsByTagName('Folder')->item($i)->getElementsByTagName('Placemark')->item($j);
			$name = $placemark->getElementsByTagName('name')->item(0)->nodeValue;       
			$lon = $placemark->getElementsByTagName('LookAt')->item(0)->getElementsByTagName('longitude')->item(0)->nodeValue; 
			$lat = $placemark->getElementsByTagName('LookAt')->item(0)->getElementsByTagName('latitude')->item(0)->nodeValue;   
			    
			//echo "-<br>";
			                                                                                                    
			//$dist = distance(37.78554, -122.417253, $lat, $lon);
			//if($dist <= 1000)  
			//{             
				//echo "--" . $name . "<br>";
				echo '<quake>';  
			    echo '<name>' . $name .'</name>'; 
				echo '<longitude>' . $lon . '</longitude>'; 
				echo '<longitude>' . $lat . '</longitude>'; 
				echo '</quake>';   
			//}                                                                                                                                                                                                                          
		}                                                                      
		echo '</Magnitude>'; 
		//echo $folder . " :end <br>";                                                                     	  
	}                                         
?>