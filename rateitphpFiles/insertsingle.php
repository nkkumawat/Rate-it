<?php
    require 'createDataBase.php';
// http://nkkumawat.me/rateit/insertTeachersData.php?name=%22naren%22&department=%22nk%22&email=%22as%22&qualification=%22aasf%22&phone=%22fsfs%22&about=%22FsD%20ffsf%20s%20fs%22&experience=%22asfss%20fas%20%22&points=%22100%22
    $name=$_POST['name'];
    $department=$_POST['department'];
    $email=$_POST['email'];
    $picture=$_POST['picture'];
    // $picture = "http://nkkumawat.me/nk.png";

    $qualification=$_POST['qualification'];
    $phone=$_POST['phone'];
    $about=$_POST['about'];
    $experience=$_POST['experience'];
    $points=$_POST['points'];
    // $points = "12112";

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $sql = "INSERT INTO rateitteachersData (name, department, email , picture , qualification , phone , about , experience , points)
    VALUES ('$name', '$department', '$email' , '$picture',  '$qualification' , '$phone' , '$about' , '$experience' , '$points')";
    // use exec() because no results are returned
    $conn->exec($sql);
    echo "New record created successfully";
    }
catch(PDOException $e)
    {
    echo $sql . "<br>" . $e->getMessage();
    }

$conn = null;
?>