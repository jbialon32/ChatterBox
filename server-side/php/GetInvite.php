<?php

require '../req/SqlConnector.php';
$result = array();
$inviteArray = array();
$response = array();
 
//Check for mandatory parameters
if(isset($_POST["inviteCode"])){
  $db = connectSQL();
  $chat;
  $invite = $_POST["inviteCode"];
  $perm;
  
//Query to insert a user
$query = "SELECT * FROM chat_invites WHERE invite_code=?";
//Prepare the query
if($stmt = $db->prepare($query)){
    //Bind parameters
    $stmt->bind_param("s", $invite);
    //Exceting MySQL statement
    $stmt->execute();
    //Bind fetched result to variables
    $stmt->bind_result($invite, $chat, $perm);
    //Retrieve ad prepare for JSON
    while($stmt->fetch()){
        $messageArray["invite_code"] = $chatID;
        $messageArray["chat"] = $user;
        $messageArray["perm"] = $msg;
        $result[] = $messageArray;
      }
      $response["success"] = 1;
      $response["data"] = $result;
      $db->close();			
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

?>
