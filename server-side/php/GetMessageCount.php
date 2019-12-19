<?php

  require '../req/SqlConnector.php';

  $db = connectSQL();
  $result = array();
  $response = array();
  //Check for mandatory parameter chat_id
  if(isset($_GET["chat_id"])){
    $chatID = $_GET["chat_id"];
    //Query to fetch message count by chat room id
    $query = "SELECT COUNT(message) AS count FROM message_full WHERE chat_id=?";
    if($stmt = $db->prepare($query)){
      //Bind chat_id parameter to the query
      $stmt->bind_param('i', $chatID);
      $stmt->execute();
      //Bind fetched result to the variable count
      $stmt->bind_result($count);
      //Fetch a single row at a time
      if($stmt->fetch()){
        $response["success"] = 1;
        $response["count"] = $count;
      }else{
        $response["success"] = 0;
        $response["message"] = "Message retrieval failed.";
        $db->close();
      }
    
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
