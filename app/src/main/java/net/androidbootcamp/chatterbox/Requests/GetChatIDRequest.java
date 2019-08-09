package net.androidbootcamp.chatterbox.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetChatIDRequest extends StringRequest {

    private static final String GetChatIDRequest_REQUEST_URL = "http://teamblues.x10host.com/GetChatID.php";

    //private static final String GetChatIDRequest_REQUEST_URL = "http://192.168.1.90/api/GetChatID.php";
    private Map<String, String> params;

    public GetChatIDRequest(String user_ID,Response.Listener<String> listener) {
        super(Request.Method.POST, GetChatIDRequest_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("user_ID", user_ID);


    }





}


