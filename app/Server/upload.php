<?php
	$host='localhost';
	$uname='u836064230_test1';
	$pwd='test123';
	$db="u836064230_test";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$id=$_REQUEST['id'];
	$name=$_REQUEST['name'];

	$flag['code']=0;

	if($r=mysql_query("insert into test values('$id','$name') ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}

	print(json_encode($flag));
	mysql_close($con);
?>