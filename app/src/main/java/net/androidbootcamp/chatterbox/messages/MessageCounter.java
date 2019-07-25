package net.androidbootcamp.chatterbox.messages;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageCounter {
    int currCount = 0;
    int compCount = 0;

    public int getMessageCountByChat(int activeChatID) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    if(success == 1) {
                       currCount = jsonResponse.getInt("count");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

        MessageCountRequest messageCountRequest = new MessageCountRequest(activeChatID, responseListener);
        return currCount;
    }

    public boolean newMessageCheck(int activeChatID, int previousCount){
        compCount = previousCount;
        currCount = getMessageCountByChat(activeChatID);
        if(compCount < currCount){
            return true;
        }
        return false;
    }

}
