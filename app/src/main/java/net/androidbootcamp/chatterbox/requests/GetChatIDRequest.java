package net.androidbootcamp.chatterbox.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static net.androidbootcamp.chatterbox.urlStuff.BuildApiLink.getApiLink;

public class GetChatIDRequest extends StringRequest {

    private static final String GetChatIDRequest_REQUEST_URL = getApiLink("api/GetChatID.php");
    private Map<String, String> params;

    public GetChatIDRequest(String user_id, Response.Listener<String> listener) {
        super(Request.Method.POST, GetChatIDRequest_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("user_id", user_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}


