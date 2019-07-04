<?php
require 'SqlConnector.php';
$response = array();
 
//Check for mandatory parameters
if(isset($_POST["email"])&&isset($_POST["username"])&&isset($_POST["password"])&&isset($_POST["fname"])&&isset($_POST["lname"])){
  $db = connectSQL();
	$email = strToUpper($_POST["email"]);
	$username = strToUpper($_POST["username"]);
	$pass = $_POST["password"];
  $fname = $_POST["fname"];
	$lname = $_POST["lname"];
	$response = uniqueValidation($db, $username, $email);

	if($response["success"] == 1){
		//Query to insert a user
		$query = "INSERT INTO users(email, user_name, pass) VALUES (?,?,?)";
		//Prepare the query
		if($stmt = $db->prepare($query)){
			//Bind parameters
			$stmt->bind_param("sss",$email,$username,$pass);
			//Exceting MySQL statement
			$stmt->execute();
			//Check if data got inserted
			if($stmt->affected_rows == 1){
				$response = getUserByEmail($db, $email);
				if($response["success"] = 1){
					$userID = $response["id"];
					$response = addUserInfo($db, $userID, $fname, $lname);
					if($response["success"] = 1){
						$response = addUserRole($db, $userID);
						if($response["success"] = 1){
							$response = addUserToWelcome($db, $userID);
						}
					}
				}
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
	}
}else{
	//Mandatory parameters are missing
	$response["success"] = 0;
	$response["message"] = "missing mandatory parameters";
}
//Displaying JSON response
echo json_encode($response);

function getUserByEmail($db, $email){
  //Query to fetch user id
  $userID = 0;
  $query = "SELECT id FROM users WHERE email=?";
  if($stmt = $db->prepare($query)){
    //Bind email parameter to the query
    $stmt->bind_param("s", $email);
    $stmt->execute();
    //Bind fetched result to variables $userID
    $stmt->bind_result($userID);
    //Check for results
    if($stmt->fetch()){

      $response["success"] = 1;
      $response["message"] = "ID retrieved.";
      $response["id"] = $userID;

      return $response;
          
    }else{
      //When login fails
      $response["success"] = 0;
      $response["message"] = "ID retrieval Failed.";
    }

    $db->close();

  }else{
    //When some error occurs
    $response["success"] = 0;
    $response["message"] = mysqli_error($db);   
  }

  return $response;

}

function addUserInfo($db, $userID, $fname, $lname){
	//Query to insert user info
	$query = "INSERT INTO user_info(user_id, fName, lName) VALUES (?,?,?)";
	//Prepare the query
	if($stmt = $db->prepare($query)){
		//Bind parameters
		$stmt->bind_param("iss",$userID,$fname,$lname);
		//Exceting MySQL statement
		$stmt->execute();
		//Check if data got inserted
		if($stmt->affected_rows == 1){
			$response["success"] = 1;			
			$response["message"] = "User Info Successfully Added";			
			
		}else{
			//Some error while inserting
			$response["success"] = 0;
			$response["message"] = "Error while adding user info";
		}					
	}else{
		//Some error while inserting
		$response["success"] = 0;
		$response["message"] = mysqli_error($db);
  }
  
  return $response;

}

function addUserRole($db, $userID){
  //Query to insert user role
  $role = 2;  
	$query = "INSERT INTO user_roles(user_id, role_id) VALUES (?,?)";
	//Prepare the query
	if($stmt = $db->prepare($query)){
		//Bind parameters
		$stmt->bind_param("ii",$userID,$role);
		//Exceting MySQL statement
		$stmt->execute();
		//Check if data got inserted
		if($stmt->affected_rows == 1){
			$response["success"] = 1;			
			$response["message"] = "User Role Successfully Added";			
			
		}else{
			//Some error while inserting
			$response["success"] = 0;
			$response["message"] = "Error while adding user role";
		}					
	}else{
		//Some error while inserting
		$response["success"] = 0;
		$response["message"] = mysqli_error($db);
  }
  
  return $response;

}

function addUserToWelcome($db, $userID){
  //Query to insert user into the welcome chatroom
  $chat = 1;
  $role = 2;
	$query = "INSERT INTO room_members(user_id, chat_id, chat_role) VALUES (?,?,?)";
	//Prepare the query
	if($stmt = $db->prepare($query)){
		//Bind parameters
		$stmt->bind_param("iii",$userID,$chat,$role);
		//Exceting MySQL statement
		$stmt->execute();
		//Check if data got inserted
		if($stmt->affected_rows == 1){
			$response["success"] = 1;			
			$response["message"] = "User Successfully Added To Chat";			
			
		}else{
			//Some error while inserting
			$response["success"] = 0;
			$response["message"] = "Error while adding user info";
		}					
	}else{
		//Some error while inserting
		$response["success"] = 0;
		$response["message"] = mysqli_error($db);
  }
  
  return $response;

}

function uniqueValidation($db, $username, $email){
	$user = "";
	$mail = "";
	$query = "SELECT user_name, email FROM users WHERE user_name=? OR email=?";
	if($stmt = $db->prepare($query)){
		//Bind user_name parameter to the query
		$stmt->bind_param("ss", $username, $email);
		$stmt->execute();
		//Bind fetched result to variables $userID, $pass
		$stmt->bind_result($user, $mail);
		$user = strToUpper($user);
		$mail = strToUpper($mail);
		//Check for results		
		if($stmt->fetch()){
			//Check user entry with stored password
			if(strcmp($username, $user) == 0){
				$response["success"] = 0;
				$response["message"] = "Username Already Taken.";
			}

			if(strcmp($email, $mail) == 0){
				$response["success"] = 0;
				$response["message"] = "Username Already Taken.";
			}
					
		}else{
			//if no matching records
			$response["success"] = 1;
			$response["message"] = "Email and Username are Unique";
			return $response;
		}

	}else{
		//When some error occurs
		$response["success"] = 0;
		$response["message"] = mysqli_error($db);
		
	}

}

?>