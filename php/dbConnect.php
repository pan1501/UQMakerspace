<?php
	include_once('constants.php');
	$conn = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);
	if($conn){
		//echo 'successed';
	}else{
		echo 'not successed';
	}

?>