package net.androidbootcamp.chatterbox.messages;

import java.util.HashMap;

public class MessageObject {
    private int chatID;
    private String username;
    private String message;
    private String timestamp;
    private HashMap<String, String> map;

    public MessageObject(int chat_ID, String user, String messageText, String timeSent){
        chatID = chat_ID;
        username = user;
        message = messageText;
        timestamp = timeSent;

        map.put("username", user);
        map.put("message", messageText);
        map.put("timestamp", timeSent);
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
