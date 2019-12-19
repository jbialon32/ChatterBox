<?php  
  function getConnectionPassword($db="PassGrabber") {

    $user = "PassBot";
    $pass = "b4Dp4S5";
    $database = $db;
    $server = "192.168.1.64";

    //Create a new mysqli object named $db,
    //and connect it to the database
    $db = new mysqli($server, $user, $pass, $database, 3306);

    //If the connection generates an error,
    //exit the program and display an error message
    if($db->connect_errno) {
      die('Connection To MySQL Failed');
    }

    $queryStatement = "
      SELECT pass FROM login WHERE id=1;
    ";

    $results = $db->query($queryStatement);
    $row = $results->fetch_assoc();
    $pass = $row[pass]; 

    $db->close();

    return $pass;

  }

  function getConnectionUser($db="PassGrabber") {

    $user = "PassBot";
    $pass = "b4Dp4S5";
    $database = $db;
    $server = "192.168.1.64";

    $db = new mysqli($server, $user, $pass, $database, 3306);

    if($db->connect_errno) {
      die('Connection To MySQL Failed');
    }

    $queryStatement = "
      SELECT user FROM login WHERE id=1;
    ";

    $results = $db->query($queryStatement);
    $row = $results->fetch_assoc();
    $user = $row[user]; 

    $db->close();

    return $user;

  }

?>