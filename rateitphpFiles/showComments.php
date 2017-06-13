<?php
require 'createCommentsDataBase.php';


$teacherid=urldecode($_GET['teacherid']);

$sql = "SELECT * FROM commentsrateit WHERE teacherid= '$teacherid' ORDER BY id DESC";
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