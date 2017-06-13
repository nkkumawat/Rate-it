 <?php
require 'createLoginDataBase.php';
//http://nkkumawat.me/rateit/signup.php?email=nk@nk.com&name=narendra&department=computer&password=asdf
$email=urldecode($_GET['email']);
$name=urldecode($_GET['name']);
$picture=urldecode($_GET['picture']);

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $sql = "INSERT INTO loginrateit (name, email, picture ) VALUES ('$name', '$email' , '$picture')";
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