package net.androidbootcamp.chatterbox;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.androidbootcamp.chatterbox.Requests.RegisterRequest;
import net.androidbootcamp.chatterbox.ui.login.LoginActivity;
import net.androidbootcamp.chatterbox.encryption.Encrypt256;

import org.json.JSONException;
import org.json.JSONObject;



public class    RegistrationActivity extends AppCompatActivity {

    EditText username,email, firstName, lastName, password, passwordTwo;
    Button registerBtn;
    CheckBox terms;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = (EditText)findViewById(R.id.regUsername);
        email = (EditText)findViewById(R.id.regEmail);
        firstName = (EditText)findViewById(R.id.regFirstName);
        lastName = (EditText)findViewById(R.id.regLastName);
        password = (EditText)findViewById(R.id.regPassword1);
        passwordTwo = (EditText)findViewById(R.id.regPassword2);
        registerBtn = (Button)findViewById(R.id.register);
        terms = (CheckBox)findViewById(R.id.termsAndConditions);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (!DataEmpty()){
                    //if the data is not empty, do all the following
                    Toast.makeText(RegistrationActivity.this, "You've completely filled out the form!", Toast.LENGTH_SHORT).show();



                    String uName = username.getText().toString();
                    String eMail = email.getText().toString();
                    String fName = firstName.getText().toString();
                    String lName = lastName.getText().toString();
                    String pass = Encrypt256.getSHA(password.getText().toString());         //Turn password into SHA-256
                    String pass2 = Encrypt256.getSHA(passwordTwo.getText().toString());     //Turn password into SHA-256


                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                //boolean success = false;
                                Log.e("JSONRESPONSE", response);
                                JSONObject jsonResponse = new JSONObject(response);
                                int success = jsonResponse.getInt("success");



                                if (success == 1){
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    RegistrationActivity.this.startActivity(intent);
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                    builder.setMessage("Register failed!").setNegativeButton("Ok", null).create().show();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(RegistrationActivity.this, "Didn't work!", Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest request = new RegisterRequest(eMail, uName, pass, fName, lName, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegistrationActivity.this);
                    queue.add(request);
                    Log.e("Queue","Register queue code ran!");

                }

            }
        });





    }//end onCreate

    //reference code from: https://www.codebrainer.com/blog/registration-form-in-android-check-email-is-valid-is-empty
    //todo Need data validation to make sure no blank fields
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    boolean DataEmpty() {

        if (isEmpty(username)) {
            username.setError("Username is required!");
            return true;
        }
        else if (isEmpty(email)) {
            lastName.setError("Email is required!");
            return true;
        }
        else if (isEmpty(firstName)) {
            firstName.setError("First name is required!");
            return true;
        }
        else if (isEmpty(lastName)) {
            lastName.setError("Last name is required!");
            return true;
        }

        else if (isEmpty(password)) {
            password.setError("Password is required!");
            return true;
        }
        else if (isEmpty(passwordTwo)) {
            passwordTwo.setError("Password is required!");
            return true;
        }
        else {
            return false;
        }
    }
    //end reference code



}//end class
