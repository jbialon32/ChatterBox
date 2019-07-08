<?php
  require '../req/SqlConnector.php';

  /*

    Many thanks to https://www.skysilk.com/blog/2018/how-to-connect-an-android-app-to-a-mysql-database/
    
  */

  $db=connectSQL();
  
  // Select email, username, and password from users
  $sql = "SELECT * FROM user_full;";
  
  // Check for results
  if ($result = mysqli_query($db, $sql))
  {
    // We have results, create an array to hold the results
    // and an array to hold the data
    $resultArray = array();
    $tempArray = array();
    
    // Loop through each result
    while($row = $result->fetch_object())
    {
      // Add each result into the results array
      $tempArray = $row;
          array_push($resultArray, $tempArray);
    }
    
    // Encode the array to JSON and output the results
    echo json_encode($resultArray);
  }
  
  // Close connections
  mysqli_close($db);

?>