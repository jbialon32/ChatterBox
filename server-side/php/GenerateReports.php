<!DOCTYPE php>
<HTML>

<link rel="stylesheet" type="text/css" href="../css/reports.css">

<?php

require '../req/SqlConnector.php';

?>
<head>
<title>Reports</title>
</head>

<body>
<div class="header">
    <h2>Generate Reports</h1>
    <form method="post">
        <select name="options">
            <option value="chat_members">Chat Members</option>
            <option value="users">Users</option>
            <option value="messages">Messages</option>
        </select>

        <input type="submit" name="generate" value="Generate">
    </form>
</div>

<?php
if(isset($_POST['generate'])){ //check if form was submitted
    ?>
    <div class="row">
        <div class="column side"></div>
        <div class="column middle" style="background-color:#bbb;">
        <?php
            generateReport();
        ?>
        </div>
        <div class="column side"></div>
    </div>

    <div class="footer">
        <p>Footer</p>
    </div>
    <?php

} 
?>

</body>
</html>

<?php

function generateReport() {

    $reportType = $_POST['options'];
    $data = getReportData($reportType);

    ?>

    <table style="width:100%">

    <?php

    if (strcmp($reportType, "chat_members") == 0) {

        ?>
        <tr>
            <th>Chat ID</th>
            <th>Chat Name</th>
            <th>Username</th>
            <th>Role</th>
        </tr>
        <?php

        while (!empty($data)) {

            $reportItem = array_pop($data);

            ?>

            <tr>
                <td><?php echo $reportItem["chatID"] ?></td>
                <td><?php echo $reportItem["chat_name"] ?></td>
                <td><?php echo $reportItem["username"] ?></td>
                <td><?php echo $reportItem["role"] ?></td>
            </tr>

        <?php

        }

    }

    if (strcmp($reportType, "users") == 0) {

        ?>
        <tr>
            <th>Username</th>
            <th>First name</th>
            <th>Last name</th>
            <th>E-mail</th>
            <th>Title</th>
            <th>Date Created</th>
        </tr>
        <?php

        while (!empty($data)) {

            $reportItem = array_pop($data);

            ?>

            <tr>
                <td><?php echo $reportItem["user"] ?></td>
                <td><?php echo $reportItem["first_name"] ?></td>
                <td><?php echo $reportItem["last_name"] ?></td>
                <td><?php echo $reportItem["email"] ?></td>
                <td><?php echo $reportItem["title"] ?></td>
                <td><?php echo $reportItem["date"] ?></td>
            </tr>

        <?php

        }
        
    }

    if (strcmp($reportType, "messages") == 0) {

        ?>
        <tr>
            <th>Chat ID</th>
            <th>Username</th>
            <th>Message</th>
            <th>Time Sent</th>
        </tr>

        <?php

        while (!empty($data)) {

            $reportItem = array_pop($data);

            ?>

            <tr>
                <td><?php echo $reportItem["chatID"] ?></td>
                <td><?php echo $reportItem["username"] ?></td>
                <td><?php echo $reportItem["message"] ?></td>
                <td><?php echo $reportItem["timestamp"] ?></td>
            </tr>

            <?php

        }
        
    }

    ?>

    </table>

    <?php

}


function getReportData($reportCode) {

    $db = connectSQL();
    $reportArray = array();
    $result = array();

    if (strcmp($reportCode, "users") == 0){

        $username;
        $firstName;
        $lastName;
        $eMail;
        $title;
        $dateCreated;

        $query = "CALL GetAllUserInfo()";

        if($stmt = $db->prepare($query)) {
            $stmt->execute();
            //Bind fetched result to variables $chatID, $user, $msg, and $time
            $stmt->bind_result($username, $firstName, $lastName, $eMail, $title, $dateCreated);
            //Fetch a single row at a time
            while($stmt->fetch()){
                $reportArray["user"] = $username;
                $reportArray["first_name"] = $firstName;
                $reportArray["last_name"] = $lastName;
                $reportArray["email"] = $eMail;
                $reportArray["title"] = $title;
                $reportArray["date"] = $dateCreated;
                $result[] = $reportArray;
            }

            $db->close();
            
        } else {
            //When some error occurs
            $response["success"] = 0;
            $response["message"] = mysqli_error($db);
        
        }

    }

    if (strcmp($reportCode, "messages") == 0){

        $chatID;
        $username;
        $message;
        $timestamp;

        $query = "CALL GetAllMessageInfo()";

        if($stmt = $db->prepare($query)) {
            $stmt->execute();
            //Bind fetched result to variables $chatID, $user, $msg, and $time
            $stmt->bind_result($chatID, $username, $message, $timestamp);
            //Fetch a single row at a time
            while($stmt->fetch()){
                $reportArray["chatID"] = $chatID;
                $reportArray["username"] = $username;
                $reportArray["message"] = $message;
                $reportArray["timestamp"] = $timestamp;
                $result[] = $reportArray;
            }

            $db->close();
            
        } else {
            //When some error occurs
            $response["success"] = 0;
            $response["message"] = mysqli_error($db);
        
        }

    }

    if (strcmp($reportCode, "chat_members") == 0){

        $chatID;
        $chatName;
        $username;
        $role;

        $query = "CALL GetAllChatMemberInfo()";

        if($stmt = $db->prepare($query)) {
            $stmt->execute();
            //Bind fetched result to variables $chatID, $user, $msg, and $time
            $stmt->bind_result($chatID, $chatName, $username, $role);
            //Fetch a single row at a time
            while($stmt->fetch()){
                $reportArray["chatID"] = $chatID;
                $reportArray["chat_name"] = $chatName;
                $reportArray["username"] = $username;
                $reportArray["role"] = $role;
                $result[] = $reportArray;
            }

            $db->close();
            
        } else {
            //When some error occurs
            $response["success"] = 0;
            $response["message"] = mysqli_error($db);
        
        }

    }

    $db->close();
    return $result;

}

?>