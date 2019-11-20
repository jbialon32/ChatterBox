<?php
  require '../req/SqlConnector.php';
  $response = array();
  
  //Check for mandatory parameters
  if(isset($_POST["userID"])&&isset($_POST["chatName"])){
    $db = connectSQL();
    $ownerID = $_POST["userID"];
    $chatName = $_POST["chatName"];
    $chatRole = 1;
    $chatID = 0;
    
    //Query to insert a chatroom
    $query = "INSERT INTO chat_rooms(owner_id, chat_name) VALUES (?,?)";
    //Prepare the query
    if($stmt = $db->prepare($query)){
      //Bind parameters
      $stmt->bind_param("is", $ownerID, $chatName);
      //Exceting MySQL statement
      $stmt->execute();
      //Check if data got inserted
      if($stmt->affected_rows == 1){
        $response["success"] = 1;
        $response["message"] = "Chatroom made successfully.";
        $response = getChatroomID($db, $chatName, $ownerID);
        if($response["success"] == 1){
          $chatID = $response["id"];
          $response = configureChatMember($db, $chatID, $ownerID, $chatRole);
        }else{
          //Some error while inserting
          $response["success"] = 0;
          $response["message"] = "Error while getting chat ID.";
        }	
      }else{
        //Some error while inserting
        $response["success"] = 0;
        $response["message"] = "Error while creating chatroom.";
      }					
    }else{
      //Some error while inserting
    $response["success"] = 0;
    $response["message"] = mysqli_error($db);
    }

    $db->close();

  }else{
    //Mandatory parameters are missing
    $response["success"] = 0;
    $response["message"] = "missing mandatory parameters";
  }

  //Displaying JSON response
  echo json_encode($response);

  function configureChatMember($db, $chatID, $userID, $role){
    //Query to insert user role 
    $query = "INSERT INTO room_members(user_id, chat_id, chat_role) VALUES (?,?,?)";
    //Prepare the query
    if($stmt = $db->prepare($query)){
      //Bind parameters
      $stmt->bind_param("iii", $userID, $chatID, $role);
      //Exceting MySQL statement
      $stmt->execute();
      //Check if data got inserted
      if($stmt->affected_rows == 1){
        $response["success"] = 1;			
        $response["message"] = "User Successfully Added To Chat";
        $response["data"] = $chatID;			
        
      }else{
        //Some error while inserting
        $response["success"] = 0;
        $response["message"] = "Error while adding user to chat.";
      }					
    }else{
      //Some error while inserting
      $response["success"] = 0;
      $response["message"] = mysqli_error($db);
    }
    
    return $response;

  }

  function getChatroomID($db, $chatName, $userID){
    //Query to fetch user id
    $chatID = 0;
    $query = "SELECT id FROM chat_rooms WHERE owner_id=? && chat_name=?";
    if($stmt = $db->prepare($query)){
      //Bind email parameter to the query
      $stmt->bind_param("is", $userID, $chatName);
      $stmt->execute();
      //Bind fetched result to variables $userID
      $stmt->bind_result($chatID);
      //Check for results
      if($stmt->fetch()){

        $response["success"] = 1;
        $response["message"] = "ID retrieved.";
        $response["id"] = $chatID;

        return $response;
            
      }else{
        //When login fails
        $response["success"] = 0;
        $response["message"] = "ID retrieval Failed.";
      }

      $db->close();

    } else{
      //When some error occurs
      $response["success"] = 0;
      $response["message"] = mysqli_error($db);   
    }

    return $response;

  }
  

?>