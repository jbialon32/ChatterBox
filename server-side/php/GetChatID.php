<?php

  require '../req/SqlConnector.php';

  $db = connectSQL();
  $result = array();
  $messageArray = array();
  $response = array();
  //Check for mandatory parameter chat_id
  if(isset($_POST["user_id"])){
    $userID = $_POST["user_id"];
    //Query to fetch message details
    $query = "SELECT room_members.chat_id, chat_rooms.chat_name, chat_rooms.password FROM room_members, chat_rooms WHERE room_members.user_id=? && chat_rooms.id=room_members.chat_id";
    if($stmt = $db->prepare($query)){
      //Bind chat_id parameter to the query
      $stmt->bind_param('i', $userID);
      $stmt->execute();
      //Bind fetched result to variables $chatID
      $stmt->bind_result($chatID, $chatName, $chatPass);
      //Fetch a single row at a time
      while($stmt->fetch()){
        $chatroomArray["chat_id"] = $chatID;
        $chatroomArray["chat_name"] = $chatName;
        $chatroomArray["password"] = $chatPass;
        $result[] = $chatroomArray;
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
    $response["message"] = "missing parameter user_id";
  }
  //Display JSON response
  echo json_encode($response);

?>
