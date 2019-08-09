# ChatterBox

Campus based chat system

# To Do

  + Add create and join functionality to chat room activity
  + Add chatroom password functionality
  + Start friends stuff
  + Documentation

# Change Log

  +Version 1.0.1

   +UI/XML
     + New placeholder chat room info stuff
     + Added recycler view instead of list too chatroom stuff

   +Java
     + Reorganized File Structure
     + Now listens for active chat in MenuActivity onCreate
     + Uses the active chat variable to get and send messages
     + Changed recycler adapter so multiple classes can be made
       w/ more ease
     + Added Chat room list recycler adapter

   +PHP
     + Tweaked GetChatID
     + Created GetActiveChat
     
   +SQL
     + Created active_chat table
