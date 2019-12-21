<?php

require '../req/SqlConnector.php';

    if(isset($_POST['user_id']) && isset($_POST['invite_code'])){

        $db = connectSQL();
        $reportArray = array();
        $result = array();

        $userID = $_POST['user_id'];
        $inviteCode = $_POST['invite_code'];

        $query = "CALL JoinInvite(?,?)";

        if($stmt = $db->prepare($query)){
            //Bind parameters
            $stmt->bind_param("is", $userID, $inviteCode);
            //Exceting MySQL statement
            $stmt->execute();
            $stmt->bind_result($success);
            //Check if data was modified correctly
            while($stmt->fetch()){
                if($success == 1){
                    $response["success"] = 1;
                    $response["message"] = "Success!";
                }
                if($success == 2){
                    $response["success"] = 2;
                    $response["message"] = "Success, but already a member!";
                }
                else{
                    //Some error while inserting
                    $response["success"] = 0;
                    $response["message"] = "Error while accepting invite";
                }
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
    
    echo json_encode($response);


?>