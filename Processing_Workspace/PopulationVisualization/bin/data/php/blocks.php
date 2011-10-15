<?php
	$handle = fopen("../census/CenPop2010_Mean_BG06.csv", "r");
 	$num = 0;

	while (($data = fgetcsv($handle, 100, ",")) !== FALSE) {	
	    echo $num . ": (";
		$num += 1;
		echo $data[2];
		echo ")<br>";
	}
?>