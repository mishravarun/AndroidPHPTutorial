<?php
	$host='localhost';
	$uname='u836064230_test1';
	$pwd='test123';
	$db="u836064230_test";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$id=$_REQUEST['id'];
	 
	$r=mysql_query("select * from test where id='$id'",$con);

	while($row=mysql_fetch_array($r))
	{
		$flag[name]=$row[name];
	}
	 
	print(json_encode($flag));
	mysql_close($con);
?>