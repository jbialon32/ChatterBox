package net.androidbootcamp.chatterbox.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static net.androidbootcamp.chatterbox.urlStuff.BuildApiLink.getApiLink;

public class JoinChatInviteRequest extends StringRequest {

    private static final String JOIN_INVITE_URL = getApiLink("api/MessageGet.php");
    private Map<String, String> params;

    public JoinChatInviteRequest(int chatID, Response.Listener<String> listener) {
        super(Method.POST, JOIN_INVITE_URL, listener, null);
        params = new HashMap<>();

        params.put("chat_id", String.valueOf(chatID));

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
