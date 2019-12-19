<?php

  require 'GetSQL.php';

    function connectSQL() {

      $user = getConnectionUser();
      $pass = getConnectionPassword();
      $database = "TeamBlues";
      $server = "192.168.1.64";

      $db = new mysqli($server, $user, $pass, $database, 3306);

      if($db->connect_errno) {
        die('Connection To MySQL Failed');
      }

      return $db;

    }

?>