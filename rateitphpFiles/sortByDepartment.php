<?php
require 'createDataBase.php';

$department=urldecode($_GET['department']);

$sql = "SELECT * FROM rateitteachersData WHERE department='$department' ORDER BY points DESC ";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
	$outp = array();
	$outp = $result->fetch_all(MYSQLI_ASSOC);
	echo json_encode($outp);
		
		
} else {
    echo "0 results";
}
$conn->close();
?>