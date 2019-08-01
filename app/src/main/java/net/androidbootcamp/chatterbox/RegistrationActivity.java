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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.androidbootcamp.chatterbox.Requests.RegisterRequest;
import net.androidbootcamp.chatterbox.ui.login.LoginActivity;
import net.androidbootcamp.chatterbox.encryption.Encrypt256;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * This class is for the Registration Activity. It provides EditText fields for the user to fill out and checks to make
 * sure none of the fields are blank as well as the email is valid and passwords match before sending them to the server.
 */

public class    RegistrationActivity extends AppCompatActivity {

    EditText username,email, firstName, lastName, password, passwordTwo;
    Button registerBtn;
    CheckBox terms;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //references
        username = findViewById(R.id.regUsername);
        email = findViewById(R.id.regEmail);
        firstName = findViewById(R.id.regFirstName);
        lastName = findViewById(R.id.regLastName);
        password = findViewById(R.id.regPassword1);
        passwordTwo = findViewById(R.id.regPassword2);
        registerBtn = findViewById(R.id.register);
        terms = findViewById(R.id.termsAndConditions);
        progressBar = findViewById(R.id.progressBarRegister);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                if (!terms.isChecked()){
                    terms.setError("");
                    Toast.makeText(getApplicationContext(), "You must agree to Terms and Conditions!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }



                //data validation for all fields
                if (!DataEmpty() && isValidEmail() && terms.isChecked() && passwordsMatch()){
                    //if the data is not empty, do all the following
                    Toast.makeText(RegistrationActivity.this, "You've completely filled out the form!", Toast.LENGTH_SHORT).show();



                    String uName = username.getText().toString();
                    String eMail = email.getText().toString();
                    String fName = firstName.getText().toString();
                    String lName = lastName.getText().toString();
                    String pass = Encrypt256.getSHA(password.getText().toString());         //Turn password into SHA-256


                    //REFERENCE: https://www.youtube.com/playlist?list=PLe60o7ed8E-TztoF2K3y4VdDgT6APZ0ka
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                Log.e("response", response);

                                //store response in JSONObject
                                JSONObject jsonResponse = new JSONObject(response);

                                //get the value of "success. 1 if success, 0 if not"
                                int success = jsonResponse.getInt("success");
                                String message = jsonResponse.getString("message");

                                Log.e("responseRegister", String.valueOf(success));
                                //success
                                if (success == 1){
                                    //go back to login
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    RegistrationActivity.this.startActivity(intent);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }else{

                                    //display errors
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                    builder.setMessage(message).setNegativeButton("Ok", null).create().show();
                                    progressBar.setVisibility(View.INVISIBLE);
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
                    Log.e("registerRequest", "request made");

                }//end if



            }//end onClick
        });//end onClickListener





    }//end onCreate

    //reference code from: https://www.codebrainer.com/blog/registration-form-in-android-check-email-is-valid-is-empty

    //checks if string from a field is empty returns boolean
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    //checks email if it is valid by comparing it to a pattern
    boolean isValidEmail (){

        //this is from https://www.geeksforgeeks.org/check-email-address-valid-not-java/
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        String emailStr = email.getText().toString();

        Pattern pat = Pattern.compile(emailRegex);

        if (pat.matcher(emailStr).matches()){
            return true;

        }else{
            email.setError("Invalid Email!");
            return false;

        }

    }//end isValidEmail

    boolean passwordsMatch(){
        String passOne = password.getText().toString();
        String passTwo = passwordTwo.getText().toString();
        if (passOne.equals(passTwo)){
            return true;
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            passwordTwo.setError("Passwords don't match!");
            return false;
        }
    }

    //checks each field if data is empty if it is displays error message and returns true
    boolean DataEmpty() {

        if (isEmpty(username)) {
            username.setError("Username is required!");
            return true;
        }
        else if (isEmpty(email)) {
            email.setError("Email is required!");
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


        else {
            return false;
        }
    }
    //end reference code



}//end class
