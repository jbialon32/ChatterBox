package net.androidbootcamp.chatterbox.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is for building a request to sent to the server for sending a new message.
 */

//REFERENCE: https://www.youtube.com/playlist?list=PLe60o7ed8E-TztoF2K3y4VdDgT6APZ0ka
public class SendMessageRequest extends StringRequest {
    //private static final String REGISTER_REQUEST_URL = "http://192.168.1.90/api/CreateUser.php";
    //private static final String REGISTER_REQUEST_URL = "http://192.168.0.37/api/CreateUser.php";
    private static final String REGISTER_REQUEST_URL = "http://teamblues.x10host.com/MessageCreate.php";
    private Map<String, String> params;

    //constructor
    public SendMessageRequest(Integer chatID, String userID, String message, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();

        params.put("chatID", String.valueOf(chatID));
        params.put("userID", userID);
        params.put("message", message);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
