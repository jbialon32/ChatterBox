# ChatterBox

Campus based chat system

# To Do

  + Add join functionality to chat room activity
  + Start friends stuff
  + Documentation

# Change Log

  Version 1.0.2
  
    UI/XML
  - Turned chat room recyclerview to set of buttons
  -chat room menu create button has functionality

  Java
  - Added requests for create and change chat rooms
  - Updated ChatRommActivity

  PHP
  -Added ChangedCgat.php
  -Heavy modificatiosn to CreateChatroom.php

  Version 1.0.1

  UI/XML
  - New placeholder chat room info stuff
  - Added recycler view instead of list too chatroom stuff

  Java
  - Reorganized File Structure
  - Now listens for active chat in MenuActivity onCreate
  - Uses the active chat variable to get and send messages
  - Changed recycler adapter so multiple classes can be made
    w/ more ease
  - Added Chat room list recycler adapter

  PHP
  - Tweaked GetChatID
  - Created GetActiveChat
     
  SQL
  - Created active_chat table
