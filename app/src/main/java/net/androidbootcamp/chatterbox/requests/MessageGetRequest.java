package net.androidbootcamp.chatterbox.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static net.androidbootcamp.chatterbox.urlStuff.BuildApiLink.getApiLink;

public class MessageGetRequest extends StringRequest {
    private static final String MESSAGE_REQUEST_URL = getApiLink("api/MessageGet.php");
    private static final String MESSAGE_REFRESH_REQUEST_URL = getApiLink("api/MessageRefresh.php");
    private Map<String, String> params;

    public MessageGetRequest(int chatID, Response.Listener<String> listener) {
        super(Method.POST, MESSAGE_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("chat_id", String.valueOf(chatID));

    }

    //this constructor accepts additional timestampIndex and uses different URL than other constructor
    public MessageGetRequest(int chatID,String timeStampIndex,Response.Listener<String> listener) {


        super(Method.POST, MESSAGE_REFRESH_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("chat_id", String.valueOf(chatID));
        params.put("timestampindex", timeStampIndex);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
