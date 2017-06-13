<?php
	$servername = "localhost";
	$username = "id1715588_nk";
	$password = "ILOVENOKIA95";
	$dbname = "id1715588_nk";

	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}

	// sql to create table
	$sql = "CREATE TABLE if not exists commentsrateit (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
		teacherid INT(20) NOT NULL,
		comments VARCHAR(300) 
		name VARCHAR(50))";
	if ($conn->query($sql) === TRUE) {
	   echo "Table MyGuests created successfully";
	} else {
	    echo "Error creating table: " . $conn->error;
	}

	//$conn->close();
?>