 <?php
require 'createDataBase.php';

$id=urldecode($_GET['id']);

$sql = "SELECT * FROM rateitteachersData WHERE id= $id";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row/
    // while($row = $result->fetch_assoc()) {
    //     // echo "id: " . $row["id"]. " - Name: " . $row["name"]. " " . $row["email"]. "<br>";
        $ans = mysqli_fetch_assoc($result);
        echo json_encode($ans);
    // }
			
			
			// $outp = $result->fetch_all(MYSQLI_ASSOC);
		
		
} else {
    echo "0 results";
}
$conn->close();
?> 	