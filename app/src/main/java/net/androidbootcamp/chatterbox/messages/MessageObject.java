package net.androidbootcamp.chatterbox.messages;

public class MessageObject {
    private int chatID;
    private String username;
    private String message;
    private String timestamp;

    public MessageObject(int chat_ID, String user, String messageText, String timeSent){
        chatID = chat_ID;
        username = user;
        message = messageText;
        timestamp = timeSent;
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
