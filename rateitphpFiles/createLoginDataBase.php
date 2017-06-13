<?php
	$servername = "localhost";
	$username = "id1715588_rateit";
	$password = "ilovenokia95";
	$dbname = "id1715588_rateit";

	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}

	// sql to create table
	$sql = "CREATE TABLE if not exists loginrateit (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
		name VARCHAR(30) NOT NULL,
		department VARCHAR(30) NOT NULL,
		email VARCHAR(50),
		password VARCHAR(50),
		picture VARCHAR(50))";
	if ($conn->query($sql) === TRUE) {
	   echo "Table MyGuests created successfully";
	} else {
	    echo "Error creating table: " . $conn->error;
	}

	//$conn->close();
?>