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
	$sql = "CREATE TABLE if not exists rateitstatus (id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	studentid INT(10),
	teacherId INT(10),
	status INT(5),
	email VARCHAR(500)
	)";
	if ($conn->query($sql) === TRUE) {
	   // echo "Table MyGuests created successfully";
	} else {
	    //echo "Error creating table: " . $conn->error;
	}

	//$conn->close();
?>