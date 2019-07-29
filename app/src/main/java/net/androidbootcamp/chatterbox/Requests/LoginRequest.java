package net.androidbootcamp.chatterbox.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is for building a request to a server for login.
 */





//REFERENCE: https://www.youtube.com/playlist?list=PLe60o7ed8E-TztoF2K3y4VdDgT6APZ0ka
public class LoginRequest extends StringRequest {
    //private static final String LOGIN_REQUEST_URL = "http://192.168.1.90/api/passAuth.php";
    //private static final String LOGIN_REQUEST_URL = "http://192.168.0.37/api/passAuth.php";


    //URL of webhost
    private static final String LOGIN_REQUEST_URL = "http://teamblues.x10host.com/passAuth.php";
    private Map<String, String> params;

    //this constructor adds the user provided username and password to a hashmap for sending to server and sets a response listener
    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        //puts the values for username and user_pass in a map
        params.put("username", username);
        params.put("user_pass", password);

    }

    //gets map
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
