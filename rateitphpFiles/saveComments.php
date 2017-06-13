<?php
    require 'createCommentsDataBase.php';
//http://nkkumawat.me/rateit/insertTeachersData.php?name=%22naren%22&department=%22nk%22&email=%22as%22&qualification=%22aasf%22&phone=%22fsfs%22&about=%22FsD%20ffsf%20s%20fs%22&experience=%22asfss%20fas%20%22&points=%22100%22&picture=%22fdf%22
    $name=urldecode($_GET['name']);
    $comments=urldecode($_GET['comments']);
    $teacherid=urldecode($_GET['teacherid']);
    try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $sql = "INSERT INTO commentsrateit (teacherid, comments, name )VALUES ('$teacherid', '$comments', '$name' )";
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