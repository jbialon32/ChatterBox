package net.androidbootcamp.chatterbox.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static net.androidbootcamp.chatterbox.urlStuff.BuildApiLink.getApiLink;

public class ActiveChatRequest extends StringRequest {

    private static final String GetActiveChat_REQUEST_URL = getApiLink("api/GetActiveChat.php");
    private Map<String, String> params;

    public ActiveChatRequest(String user_id, Response.Listener<String> listener) {
        super(Method.POST, GetActiveChat_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("user_id", user_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
