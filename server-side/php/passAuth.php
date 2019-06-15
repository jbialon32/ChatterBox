<?php

  require 'SqlConnector.php';

  $db = connectSQL();
  $response = array();
  //Check for mandatory parameters username and the password to check
  if(isset($_GET['username']) && isset($_GET['user_pass'])){
    $user = $_GET['username'];
    $passAttempt = $_GET['user_pass'];
    //Query to fetch user id and password details
    $query = "SELECT id, pass FROM users WHERE user_name=?";
    if($stmt = $db->prepare($query)){
      //Bind user_name parameter to the query
      $stmt->bind_param("s",$user);
      $stmt->execute();
      //Bind fetched result to variables $userID, $pass
      $stmt->bind_result($userID, $pass);
      //Check for results		
      if($stmt->fetch()){
        //Check user entry with stored password
        if(strcmp($passAttempt, $pass) == 0){}
          $response["success"] = 1;
          $response["userID"] = $userID;
            
      }else{
        //When login fails
        $response["success"] = 0;
        $response["message"] = "Login Failed.";
      }
      $db->close();
  
  
    }else{
      //When some error occurs
      $response["success"] = 0;
      $response["message"] = mysqli_error($db);
      
    }
  
  }else{
    //When the mandatory parameters username missing
    $response["success"] = 0;
    $response["message"] = "missing parameter username";
  }
  //Display JSON response
  echo json_encode($response);

?>