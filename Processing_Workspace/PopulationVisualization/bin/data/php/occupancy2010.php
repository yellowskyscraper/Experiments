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
	
	$username = 'jim@yellowskyscraper.com';
	$password = 'weaponx1021';

	$token = ClientLogin::getAuthToken($username, $password);
	$ftclient = new FTClientLogin($token);  
	
	/*//| Bay Occupancy Blocks: 1992482 
	echo $ftclient->query(SQLBuilder::showTables());
	echo "<br />"; */  
                              
	$query = $ftclient->query('SELECT TOTAL, LAT, LON FROM 1992482 WHERE TOTAL > 0');   
	$deliminator = substr($query, 13, 1);     
	$bay = explode($deliminator, $query); 
	unset($bay[0]);
	unset($bay[count($bay)]);
	
	echo '<?xml version="1.0" encoding="UTF-8"?>';
	echo '<census>';  
	foreach($bay as &$a)
	{                             
		$arr = explode(",", $a);
		/*//| Distance Restriction
		$dist = distance($arr[2], $arr[1], -122.28195190429688, 37.849916893514944);
		if($dist <= 45) echo '<block countID="'.$count .'" total="'.$arr[0].'" lat="'.$arr[1].'" lon="'.$arr[2].'"></block>'; */  
		echo '<block countID="'.$count .'" total="'.$arr[0].'" lat="'.$arr[1].'" lon="'.$arr[2].'"></block>';                                                
	}     
	echo '</census>';  
?>