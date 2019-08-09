package net.androidbootcamp.chatterbox.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.androidbootcamp.chatterbox.R;
import net.androidbootcamp.chatterbox.requests.RegisterRequest;
import net.androidbootcamp.chatterbox.encryption.Encrypt256;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is for the Registration Activity. It provides EditText fields for the user to fill out and checks to make
 * sure none of the fields are blank before sending them to the server.
 */

public class    RegistrationActivity extends AppCompatActivity {

    EditText username,email, firstName, lastName, password, passwordTwo;
    Button registerBtn;
    CheckBox terms;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //references
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



                //data validation for all fields
                if (!DataEmpty()){
                    //if the data is not empty, do all the following
                    Toast.makeText(RegistrationActivity.this, "You've completely filled out the form!", Toast.LENGTH_SHORT).show();



                    String uName = username.getText().toString();
                    String eMail = email.getText().toString();
                    String fName = firstName.getText().toString();
                    String lName = lastName.getText().toString();
                    String pass = Encrypt256.getSHA(password.getText().toString());         //Turn password into SHA-256
                    String pass2 = Encrypt256.getSHA(passwordTwo.getText().toString());     //Turn password into SHA-256

                    //REFERENCE: https://www.youtube.com/playlist?list=PLe60o7ed8E-TztoF2K3y4VdDgT6APZ0ka
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {


                                //store response in JSONObject
                                JSONObject jsonResponse = new JSONObject(response);

                                //get the value of "success. 1 if success, 0 if not"
                                int success = jsonResponse.getInt("success");


                                //success
                                if (success == 1){
                                    //go back to login
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    RegistrationActivity.this.startActivity(intent);
                                }else{

                                    //display errors
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                    builder.setMessage("Register failed!").setNegativeButton("Ok", null).create().show();
                                }

                            } catch (JSONException e) {
                                //Exceptions
                                e.printStackTrace();
                            }
                        }
                    };

                    //REFERENCE: https://www.youtube.com/playlist?list=PLe60o7ed8E-TztoF2K3y4VdDgT6APZ0ka
                    //send request to queue then to server
                    RegisterRequest request = new RegisterRequest(eMail, uName, pass, fName, lName, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegistrationActivity.this);
                    queue.add(request);

                }

            }
        });





    }//end onCreate

    //reference code from: https://www.codebrainer.com/blog/registration-form-in-android-check-email-is-valid-is-empty

    //checks if string from a field is empty returns boolean
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    //checks each field if data is empty if it is displays error message and returns true
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
