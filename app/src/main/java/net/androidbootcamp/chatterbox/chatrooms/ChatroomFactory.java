package net.androidbootcamp.chatterbox.chatrooms;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatroomFactory {

    private Integer chatID;
    private String chatName;
    private boolean active;
    private String chatPass;
    private ArrayList<ChatroomObject> availableChatrooms = new ArrayList<ChatroomObject>();

    public ArrayList<ChatroomObject> generateChatrooms(int userID) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    if(success == 1) {
                        JSONArray chatrooms = jsonResponse.getJSONArray("data");
                        for(int i=0; i < chatrooms.length(); i++){
                            JSONObject chatroomInfo = chatrooms.getJSONObject(i);
                            chatID = chatroomInfo.getInt("chat_id");
                            chatName = chatroomInfo.getString("chat_name");
                            active = false;
                            if(i==0) {
                                active = true;
                            }
                            ChatroomObject chatroom = new ChatroomObject(chatID, chatName);
                            availableChatrooms.add(chatroom);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

        ChatroomInfoRequest chatroomInfoRequest = new ChatroomInfoRequest(userID, responseListener);
        return availableChatrooms;
    }

}
