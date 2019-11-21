<?php
require '../req/SqlConnector.php';
$response = array();
 
//Check for mandatory parameters
if(isset($_POST["userID"])&&isset($_POST["chatID"])){
  $db = connectSQL();
	$chat = $_POST["chatID"];
	$user = $_POST["userID"];
  
  //Query to insert a user
  $query = "UPDATE chat_active SET active_chat=? WHERE user_id=?;";
  //Prepare the query
  if($stmt = $db->prepare($query)){
    //Bind parameters
    $stmt->bind_param("ii", $chat, $user);
    //Executing MySQL statement
    $stmt->execute();
    //Check if data got inserted
    if($stmt->affected_rows == 1){
      $response["success"] = 1;
      $response["message"] = "Chat successfully changed.";
    }else{
      //Some error while inserting
      $response["success"] = 0;
      $response["message"] = "Error while changing chat";
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
  

?>