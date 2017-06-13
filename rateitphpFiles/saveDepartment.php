 <?php
require 'createLoginDataBase.php';
//http://nkkumawat.me/rateit/signup.php?email=nk@nk.com&name=narendra&department=computer&password=asdf
$email=urldecode($_GET['email']);
$name=urldecode($_GET['name']);
$department=urldecode($_GET['department']);

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $sql = "INSERT INTO loginrateit where name = '$name' And email = '$email' (department) VALUES ('$department')";
    // use exec() because no results are returned
    $conn->exec($sql);
     // echo "New record created successfully";
    }
catch(PDOException $e)
    {
     echo $sql . $e->getMessage();
    }
$conn = null;
?> 	