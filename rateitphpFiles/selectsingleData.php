 <?php
require 'createDataBase.php';

$id=urldecode($_GET['id']);

$sql = "SELECT * FROM rateitteachersData WHERE id= $id";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $ans = mysqli_fetch_assoc($result);
    echo json_encode($ans);
} else {
    echo "0 results";
}
$conn->close();
?> 	