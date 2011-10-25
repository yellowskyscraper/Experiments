<?php

	include('fusion-tables-client-php/clientlogin.php');
	include('fusion-tables-client-php/sql.php');
	include('fusion-tables-client-php/file.php');

	$token = ClientLogin::getAuthToken('jim@yellowskyscraper.com', 'weaponx1021');
	$ftclient = new FTClientLogin($token);

	//echo $ftclient->query(SQLBuilder::showTables());
	//echo "<br />";
	
	//| AMSFSJ BLOCKS: 1912856
	//| A BLOCKS: 1896474
	
	$query = $ftclient->query('SELECT TOTAL, INTPTLAT, INTPTLON FROM 1896474');
	$deliminator = substr($query, 23, 1); // no NAME
	//$deliminator = substr($query, 28, 1); // with NAME
	
	$alameda = explode($deliminator, $query);
	unset($alameda[0]);
	unset($alameda[count($alameda)]);

	echo '<?xml version="1.0" encoding="UTF-8"?>';
	echo '<census>';
	$count = 0;
	foreach($alameda as &$a){
		$count += 1;
		$arr = explode(",", $a);
		echo '<block countID="'.$count .'" total="'.$arr[0].'" lat="'.$arr[1].'" lon="'.$arr[2].'"></block>';
	}
	echo '</census>';
?>