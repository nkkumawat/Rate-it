<?php
    require 'createDataBase.php';
//http://nkkumawat.me/rateit/insertTeachersData.php?name=%22naren%22&department=%22nk%22&email=%22as%22&qualification=%22aasf%22&phone=%22fsfs%22&about=%22FsD%20ffsf%20s%20fs%22&experience=%22asfss%20fas%20%22&points=%22100%22&picture=%22fdf%22
    $id=urldecode($_GET['id']);
    $rating=urldecode($_GET['rating']);

    $sql = "SELECT points FROM rateitteachersData WHERE id=$id";
	$result = $conn->query($sql);

	if ($result->num_rows > 0) {
			$ans = mysqli_fetch_assoc($result);
	        $rating= $rating + $ans['points'];
	        echo $ans['points'];
	} else {
	    echo "No result found";
	}
	$conn->close();

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $sql = "UPDATE rateitteachersData SET points='$rating' WHERE id='$id'";
    // use exec() because no results are returned
    $conn->exec($sql);
     echo $rating;
    }
catch(PDOException $e)
    {
     echo $sql . "<br>" . $e->getMessage();
    }

$conn = null;
?>