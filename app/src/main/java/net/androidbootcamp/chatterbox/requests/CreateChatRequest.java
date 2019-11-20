package net.androidbootcamp.chatterbox.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static net.androidbootcamp.chatterbox.urlStuff.BuildApiLink.getApiLink;

public class CreateChatRequest extends StringRequest {

    private static final String NEW_CHAT_REQUEST_URL = getApiLink("api/CreateChatroom.php");
    private Map<String, String> params;

    public CreateChatRequest(String userID,
                           String chatName,
                           Response.Listener<String> listener
    ) {
        super(Request.Method.POST, NEW_CHAT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("userID", String.valueOf(userID));
        params.put("chatName", String.valueOf(chatName));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
