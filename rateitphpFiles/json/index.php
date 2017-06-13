
<?php
  require 'createDataBase.php';
	//http://nkkumawat.me/rateit/insertTeachersData.php?name=%22naren%22&department=%22nk%22&email=%22as%22&qualification==.$qualification.%22aasf%22&phone=%22fsfs%22&about=%22FsD%20ffsf%20s%20fs%22&experience=%.$experience.""22asfss%20fas%20%22&points=%22100%22&picture=%22fdf%22

  $string = file_get_contents("computer.json");
  $json = json_decode($string, true);
  $i = 0;
  // for( $i = 0 ; $i < sizeof($json) ; $i++) 
  {

  	$name = $json[$i]['name'];
  	$department = $json[$i]['department'];
  	$qualification = $json[$i]['qualification'];
  	$phone = $json[$i]['phone'];
  	$email = $json[$i]['email'];
  	$picture = $json[$i]['picture'];
  	$experience = $json[$i]['experience'];
  	$about = $json[$i]['about'];
    $points = 0;

  	// $url = "http://nkkumawat.me/rateit/insertTeachersData.php?name=".$name."&department=".$department."&email=".$email."&qualification=".$qualification."&phone=".$phone."&about=".$about."&experience=".$experience."&points=0&picture=".$picture;

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




  	// $xml = file_get_contents($url);
  	// echo $xml;
  }
 
?>
