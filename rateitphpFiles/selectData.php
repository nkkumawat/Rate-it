<?php
require 'createDataBase.php';
$sql = "SELECT * FROM rateitteachersData";
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