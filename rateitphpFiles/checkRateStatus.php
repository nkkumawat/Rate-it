 <?php
require 'createRateDataBase.php';

$email=urldecode($_GET['email']);
$teaid=urldecode($_GET['teaid']);

$sql = "SELECT * FROM rateitstatus WHERE email= '$email' AND teacherId = '$teaid'";
$result = $conn->query($sql);
if ($result->num_rows > 0){
    $ans = mysqli_fetch_assoc($result);
    if($ans['status'] == 1 ) {
    	echo '{  "status": "1" }';
    }
    else {
        echo '{  "status": "0" }';
    }
} else {
    echo '{  "status": "0" }';
}
$conn->close();
?> 	