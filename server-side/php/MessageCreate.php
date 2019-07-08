<?php
require 'SqlConnector.php';
$response = array();
 
//Check for mandatory parameters
if(isset($_POST["chatID"])&&isset($_POST["userID"])&&isset($_POST["message"])){
  $db = connectSQL();
	$chat = $_POST["chatID"];
	$user = $_POST["userID"];
  $msg = $_POST["message"];
  
  //Query to insert a user
  $query = "INSERT INTO messages(chat_id, user_id, message) VALUES (?,?,?)";
  //Prepare the query
  if($stmt = $db->prepare($query)){
    //Bind parameters
    $stmt->bind_param("iis", $chat, $user, $msg);
    //Exceting MySQL statement
    $stmt->execute();
    //Check if data got inserted
    if($stmt->affected_rows == 1){
      $response["success"] = 1;
      $response["message"] = "Message sent successfully.";
    }else{
      //Some error while inserting
      $response["success"] = 0;
      $response["message"] = "Error while adding user";
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