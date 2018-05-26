<?php
	include_once('dbConnect.php');
	$query='SELECT * FROM workshops';
	$result=mysqli_query($conn, $query);
	if(mysqli_num_rows($result)>0){
		$row =mysqli_fetch_assoc($result);
		while($row){
			//echo $row['Wid']." ".$row['Wname']." ".$row['Waddress']."</br>";
			$resultList[]=$row;
			$row=mysqli_fetch_assoc($result);
		}
		print(json_encode($resultList));
	}else{
		echo 'not successed';
	}

	include_once('disconnectDB.php');
?>