package net.androidbootcamp.chatterbox;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://192.168.1.90/api/CreateUser.php";
    private Map<String, String> params;

    public RegisterRequest(String email,
                           String username,
                           String password,
                           String fName,
                           String lName,
                           Response.Listener<String> listener
    ) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);
        params.put("fname", fName);
        params.put("lname", lName);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}