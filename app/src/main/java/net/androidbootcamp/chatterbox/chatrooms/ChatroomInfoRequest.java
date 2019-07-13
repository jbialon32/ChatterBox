package net.androidbootcamp.chatterbox.chatrooms;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ChatroomInfoRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://192.168.1.90/api/GetChatID.php";
    private Map<String, String> params;

    public ChatroomInfoRequest(int userID, Response.Listener<String> listener) {
        //todo Might need to change method
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("user_id", userID + "");


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
