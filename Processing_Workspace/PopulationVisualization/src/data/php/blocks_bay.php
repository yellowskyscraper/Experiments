<?php
	include('fusion-tables-client-php/clientlogin.php');
	include('fusion-tables-client-php/sql.php');
	include('fusion-tables-client-php/file.php');
       
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
	
	$token = ClientLogin::getAuthToken('jim@yellowskyscraper.com', 'weaponx1021');
	$ftclient = new FTClientLogin($token);

	//echo $ftclient->query(SQLBuilder::showTables());
	//echo "<br />";
	//| AMSFSJ BLOCKS: 1912856
	//| A BLOCKS: 1896474  
	//| Bay Occupancy Blocks: 1992482
                                    
	$query = $ftclient->query('SELECT TOTAL, LAT, LON FROM 1992482 WHERE TOTAL > 0');   
	$deliminator = substr($query, 13, 1); 
	           
	$bay = explode($deliminator, $query); 
	unset($bay[0]);
	unset($bay[count($bay)]);

	echo '<?xml version="1.0" encoding="UTF-8"?>';
	echo '<census>';  
	$count = 0;
	foreach($bay as &$a){
		$count += 1;      
		//echo '<b>'.$count.'</b> '.$a.'<br/>';        
		$arr = explode(",", $a);
		$dist = distance($arr[2], $arr[1], -122.28195190429688, 37.849916893514944);
		if($dist <= 45) echo '<block countID="'.$count .'" total="'.$arr[0].'" lat="'.$arr[1].'" lon="'.$arr[2].'"></block>';  
                                                         
	}     
	echo '</census>';  
?>