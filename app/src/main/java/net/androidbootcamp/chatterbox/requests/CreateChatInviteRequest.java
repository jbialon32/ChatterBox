package net.androidbootcamp.chatterbox.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static net.androidbootcamp.chatterbox.urlStuff.BuildApiLink.getApiLink;

public class CreateChatInviteRequest extends StringRequest {

    private static final String NEW_CHAT_REQUEST_URL = getApiLink("api/CreateChatInvite.php");
    private Map<String, String> params;

    public CreateChatInviteRequest(String chatID,
                                   String inviteCode,
                                   Response.Listener<String> listener
    ) {
        super(Request.Method.POST, NEW_CHAT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("chat_id", String.valueOf(chatID));
        params.put("invite_code", String.valueOf(inviteCode));
        params.put("permanent", "True");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
