package net.androidbootcamp.chatterbox.messages;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MessageGetRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://192.168.1.90/api/MessageGet.php";
    private Map<String, String> params;

    public MessageGetRequest(int chatID, Response.Listener<String> listener) {
        //todo Might need to change method
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("chatID", chatID + "");


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}