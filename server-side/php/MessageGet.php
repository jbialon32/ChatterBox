<?php

  require 'SqlConnector.php';

  $db = connectSQL();
  $result = array();
  $messageArray = array();
  $response = array();
  //Check for mandatory parameter chat_id
  if(isset($_GET["chat_id"])){
    $chatID = $_GET["chat_id"];
    //Query to fetch message details
    $query = "SELECT chat_id, user_name, message, time_stamp FROM message_full WHERE chat_id=?";
    if($stmt = $db->prepare($query)){
      //Bind chat_id parameter to the query
      $stmt->bind_param('s', $chatID);
      $stmt->execute();
      //Bind fetched result to variables $chatID, $user, $msg, and $time
      $stmt->bind_result($chatID, $user, $msg, $time);
      //Fetch a single row at a time
      while($stmt->fetch()){
        $messageArray["chat"] = $chatID;
        $messageArray["user"] = $user;
        $messageArray["message"] = $msg;
        $messageArray["timestamp"] = $time;
        $result[] = $messageArray;
      }
      $response["success"] = 1;
      $response["data"] = $result;
      $db->close();
    
    }else{
      //When some error occurs
      $response["success"] = 0;
      $response["message"] = mysqli_error($db);
      
    }
  
  }else{
    //When the mandatory parameter chat_id missing
    $response["success"] = 0;
    $response["message"] = "missing parameter chat_id";
  }
  //Display JSON response
  echo json_encode($response);

?>
