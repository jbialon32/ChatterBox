<?php

  require '../req/SqlConnector.php';

  $activeCreateResult;
  $db = connectSQL();
  $response = array();
  //Check for mandatory parameter user_id
  if(isset($_POST["user_id"])){
    $userID = $_POST["user_id"];
    //Query to fetch message details
    $query = "SELECT active_chat FROM chat_active WHERE user_id=?";
    if($stmt = $db->prepare($query)){
      //Bind chat_id parameter to the query
      $stmt->bind_param('i', $userID);
      $stmt->execute();
      //Bind fetched result to variables $chatID
      $stmt->bind_result($chatID);
      //Fetch the active chat if it exists
      if($stmt->fetch() != null){
        $response["success"] = 1;
        $response["activechat"] = $chatID;
        $db->close();
      }else{
        $activeCreateResult = activeCreate($db, $userID);
        if($activeCreateResult == 1){
          $response["success"] = 1;
          $response["activechat"] = $activeCreateResult;          
        }else{
          $response["success"] = 0;
          $response["message"] = "Could not create active chat."; 
        }
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
    $response["message"] = "missing parameter user_id";
  }
  //Display JSON response
  echo json_encode($response);

  function activeCreate($db, $userID){

    //Query to fetch message details
    $query = "INSERT INTO chat_active(user_id, active_chat) VALUES (?,1)";
    if($stmt = $db->prepare($query)){
      //Bind chat_id parameter to the query
      $stmt->bind_param('i', $userID);
      $successExec = $stmt->execute();

      if($successExec){
        $activeChat = 1;
      } else{
        $activeChat = 0;
      }

      return $activeChat;

  }
}
?>