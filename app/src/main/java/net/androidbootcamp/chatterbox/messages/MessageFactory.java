package net.androidbootcamp.chatterbox.messages;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageFactory {
    private Integer chatID;
    private String username;
    private String message;
    private String timestamp;
    private ArrayList<MessageObject> availableMessages = new ArrayList<MessageObject>();

    public ArrayList<MessageObject> generateMessages(int activeChatID) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    if(success == 1) {
                        JSONArray messagesInfo = jsonResponse.getJSONArray("data");
                        for(int i=0; i < messagesInfo.length(); i++){
                            JSONObject messageInfo = messagesInfo.getJSONObject(i);
                            chatID = messageInfo.getInt("chat_id");
                            username = messageInfo.getString("user");
                            message = messageInfo.getString("message");
                            timestamp = messageInfo.getString("timestamp");
                            MessageObject msgObject = new MessageObject(chatID, username, message, timestamp);
                            availableMessages.add(msgObject);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

        MessageGetRequest messageGetRequest = new MessageGetRequest(chatID, responseListener);
        return availableMessages;
    }
}
