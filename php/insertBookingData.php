<?php
	include_once('dbConnect.php');
	$eid=$_POST["eid"];
	$query="INSERT INTO `equipment_bookings` (`Bid`, `Sid`, `Eid`, `Stime`, `Etime`) VALUES (NULL, '43123456', $eid, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
	$result=mysqli_query($conn, $query);
	include_once('disconnectDB.php');
?>