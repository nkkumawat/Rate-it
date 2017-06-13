 <?php
require 'createLoginDataBase.php';

$email=urldecode($_GET['email']);
$password1=urldecode($_GET['password']);


$sql = "SELECT * FROM loginrateit WHERE email= '$email'";
$result = $conn->query($sql);
if ($result->num_rows > 0){
    $ans = mysqli_fetch_assoc($result);
    if($ans['password'] == $password1) {
       echo json_encode($ans); 
    	// echo '{  "status": "1" }';
    }
    else {
        echo '{  "id": "0" }';
    }
} else {
    echo '{  "id": "0" }';
}
$conn->close();
?> 	