package net.androidbootcamp.chatterbox.messages;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MessageGetRequest extends StringRequest {
    //private static final String LOGIN_REQUEST_URL = "http://192.168.0.37/api/MessageGet.php";
    private static final String LOGIN_REQUEST_URL = "http://teamblues.x10host.com/MessageGet.php";
    private Map<String, String> params;

    public MessageGetRequest(int chatID, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("chat_id", String.valueOf(chatID));


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
