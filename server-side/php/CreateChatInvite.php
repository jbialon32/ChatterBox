<?php
require '../req/SqlConnector.php';
$response = array();
 
//Check for mandatory parameters
if(isset($_POST["chat_id"])&&isset($_POST["invite_code"])&&isset($_POST["permanent"])){
  $db = connectSQL();
  $chat = $_POST["chat_id"];
  $invite = $_POST["invite_code"];
  $perm = $_POST["permanent"];
  
  $response = uniqueValidation($db, $invite);

  if($response["success"] == 1){

    //Query to insert a user
    $query = "INSERT INTO chat_invites(invite_code, chat_id, permanent) VALUES (?,?,?)";
    //Prepare the query
    if($stmt = $db->prepare($query)){
        //Bind parameters
        $stmt->bind_param("sis", $invite, $chat, $perm);
        //Exceting MySQL statement
        $stmt->execute();
        //Check if data got inserted
        if($stmt->affected_rows == 1){
            $response["success"] = 1;
            $response["message"] = $invite;
        }else{
            //Some error while inserting
            $response["success"] = 0;
            $response["message"] = "Error while inserting invite";
        }					
    }else{
      //Some error while inserting
      $response["success"] = 0;
      $response["message"] = mysqli_error($db);
    }

    $db->close();

  }else{

    //Some error while inserting
    $response["success"] = 2;
    $response["message"] = "Invite code already Exists";
    $db->close();

  }

}else{
        //Mandatory parameters are missing
        $response["success"] = 0;
        $response["message"] = "missing mandatory parameters";
    
}

//Displaying JSON response
echo json_encode($response);

function uniqueValidation($db, $inviteCode){
	$query = "SELECT invite_code FROM chat_invites WHERE invite_code=?";
	if($stmt = $db->prepare($query)){
		//Bind user_name parameter to the query
		$stmt->bind_param("s", $inviteCode);
		$stmt->execute();
		//Bind fetched result to variables $userID, $pass
		$stmt->bind_result($existingInvite);
		//Check for results		
		if($stmt->affected_rows == 1){
			//Check user entry with stored password
			if(strcmp($inviteCode, $user) == 0){
				$response["success"] = 0;
				$response["message"] = "Invite already exists";
			}
					
		}else{
			//if no matching records
			$response["success"] = 1;
			$response["message"] = "Invite is unique";
			return $response;
		}

	}else{
		//When some error occurs
		$response["success"] = 0;
		$response["message"] = mysqli_error($db);
		
	}

}  

?>