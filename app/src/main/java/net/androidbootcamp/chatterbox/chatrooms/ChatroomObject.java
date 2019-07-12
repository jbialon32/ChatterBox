package net.androidbootcamp.chatterbox.chatrooms;

public class ChatroomObject {

    private int chatID;
    private String chatName;
    private boolean active = false;
    private String chatPass = null;

    public ChatroomObject(int id, String name) {
        chatID = id;
        chatName = name;
    }

    public ChatroomObject(int id, String name, String password) {
        chatID = id;
        chatName = name;
        chatPass = password;
    }

    public int getChatID() {
        return chatID;
    }

    public String getChatName() {
        return chatName;
    }

    public String getChatPass() {
        return chatPass;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setChatPass(String chatPass) {
        this.chatPass = chatPass;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }
}
