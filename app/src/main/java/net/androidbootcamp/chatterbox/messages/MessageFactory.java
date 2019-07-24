package net.androidbootcamp.chatterbox.messages;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.androidbootcamp.chatterbox.MenuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageFactory {
    private Integer chatID;
    private String username;
    private String message;
    private String timestamp;
    private ArrayList<MessageObject> availableMessages = new ArrayList<>();
    Context context;

    public MessageFactory(Context context){
        this.context = context;
    }

    public ArrayList<MessageObject> generateMessages() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    Log.e("MessageFactory", response);

                    if(success == 1) {
                        JSONArray messagesInfo = jsonResponse.getJSONArray("data");
                        //Log.e("MessageFactory", messagesInfo.toString());

                        for(int i=0; i < messagesInfo.length(); i++){
                            JSONObject messageInfo = messagesInfo.getJSONObject(i);
                            chatID = messageInfo.getInt("chat_id");
                            username = messageInfo.getString("user");
                            message = messageInfo.getString("message");
                            timestamp = messageInfo.getString("timestamp");
                            MessageObject msgObject = new MessageObject(chatID, username, message, timestamp);
                            availableMessages.add(msgObject);

                        }
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(jsonResponse.getString("message"))
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

        MessageGetRequest messageGetRequest = new MessageGetRequest(1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(messageGetRequest);
        return availableMessages;
    }
}
