package net.androidbootcamp.chatterbox.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static net.androidbootcamp.chatterbox.urlStuff.BuildApiLink.getApiLink;

public class ChangeChatRequest extends StringRequest {
    private static final String CHAT_JOIN_REQUEST_URL = getApiLink("api/ChangeChat.php");
    private Map<String, String> params;

    public ChangeChatRequest(int userID, int chatID, Response.Listener<String> listener) {
        super(Request.Method.POST, CHAT_JOIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("userID", String.valueOf(userID));
        params.put("chatID", String.valueOf(chatID));

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
