package net.androidbootcamp.chatterbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.androidbootcamp.chatterbox.Adapters.RecyclerViewAdapter;
import net.androidbootcamp.chatterbox.Requests.SendMessageRequest;
import net.androidbootcamp.chatterbox.messages.MessageGetRequest;
import net.androidbootcamp.chatterbox.messages.MessageObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class is for the Menu Activity. It is the main chat UI that displays the messages from the chatroom
 * and lets the user enter and send new messages.
 */


public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    //holds userID from LoginActivity
    private String loggedInUser;

    //adapter
    private RecyclerView.Adapter recyclerAdapter;

    //this will hold message objects
    private ArrayList<MessageObject> messageList = new ArrayList<>();


    //references
    private EditText newMessage;
    private ImageButton sendMessageBtn;
    private RecyclerView messageListView;

    //repeats a runnable that gets new messages
    private Handler mHandler = new Handler();

    private int chatID;
    private int lastItemInList;

    private Response.Listener<String> responseListener;
    private Response.Listener<String> sendListener;
    private Response.Listener<String> refreshListener;




    //boolean to check if is the initalrequest to run different code if not
    private boolean initialMessageRequest;

    //stores message timestamp to use when getting new messages
    private String timeStampIndex = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        initialMessageRequest = true;



        //references
        messageListView = (RecyclerView) findViewById(R.id.messages_view);
        messageListView.setHasFixedSize(true);
        messageListView.setLayoutManager(new LinearLayoutManager(this));
        newMessage = (EditText)findViewById(R.id.typingBox);
        sendMessageBtn = (ImageButton)findViewById(R.id.typingSendButton);




        //gets the userID we passed from LoginActivity
        Intent intent = getIntent();
        loggedInUser = intent.getStringExtra("userID");






        //listens for response from sending a message
        sendListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    Log.e("Sent jsonresponse", String.valueOf(jsonResponse));

                    if (success == 1){
                        String message = jsonResponse.getString("message");

                        //gets the success message from server and displays it
                        Toast.makeText(MenuActivity.this, message, Toast.LENGTH_SHORT).show();

                    }else{
                        //displays error
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setMessage(jsonResponse.getString("message"))
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        //this is for listening for response from the server for new messages
        refreshListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    Log.e("JsonResponse", response);

                    if(success == 1) {
                        JSONArray messagesInfo = jsonResponse.getJSONArray("data");
                        Log.e("responseListenerArray", messagesInfo.toString());

                        //if the response array is not empty
                        if (!messagesInfo.isNull(0)) {

                            //loops through response array
                            for(int i=0; i < messagesInfo.length(); i++){
                                Log.d("Inside for loop", "made it inside for loop");

                                //stors the JSONObjects from the array one at a time
                                JSONObject messageInfo = messagesInfo.getJSONObject(i);

                                //these store the values from the name/value pairs
                                chatID = messageInfo.getInt("chat");
                                String username = messageInfo.getString("user");
                                String message = messageInfo.getString("message");
                                String timestamp = messageInfo.getString("timestamp");

                                //creates new MessageObject with the values from the JSONObjects
                                MessageObject msgObject = new MessageObject(chatID, username, message, timestamp);

                                //this will keep the timestamp of the last item in the map
                                timeStampIndex = timestamp;



                                //adds the MessageObject to an arraylist
                                messageList.add(msgObject);





                            }//end for loop


                            //lets adapter know the data is updated from a certain index in array
                            recyclerAdapter.notifyItemRangeChanged(lastItemInList, messageList.size()-1);

                            //notify adapter data has changed
                            recyclerAdapter.notifyDataSetChanged();

                            //automatically scrolls to last item in recyclerview
                            messageListView.scrollToPosition(recyclerAdapter.getItemCount()-1);

                            //stores new last item index from array
                            lastItemInList = messageList.size()-1;


                        }//end if messageList not null







                    }else if (success == 0){
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                        builder.setMessage(jsonResponse.getString("message"))
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                    }else{
                        Log.e("Response", jsonResponse.getString("message"));
                    }







                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };



        // listens for server response then fills the listview
        responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    Log.e("JsonResponse", response);

                    if(success == 1) {
                        JSONArray messagesInfo = jsonResponse.getJSONArray("data");
                        Log.e("responseListenerArray", messagesInfo.toString());


                        if (!messagesInfo.isNull(0)) {
                            for(int i=0; i < messagesInfo.length(); i++){
                                Log.d("Inside for loop", "made it inside for loop");

                                JSONObject messageInfo = messagesInfo.getJSONObject(i);
                                chatID = messageInfo.getInt("chat");
                                String username = messageInfo.getString("user");
                                String message = messageInfo.getString("message");
                                String timestamp = messageInfo.getString("timestamp");
                                MessageObject msgObject = new MessageObject(chatID, username, message, timestamp);

                                //this will keep the timestamp of the last item in the map
                                timeStampIndex = timestamp;


                                messageList.add(msgObject);


                                //stores initial index of last item in array
                                lastItemInList = messageList.size()-1;

                                Log.e("messageList contents", messageList.toString());




                            }//end for loop

                            //creates new adapter for recyclerview
                            recyclerAdapter = new RecyclerViewAdapter(messageList, getApplicationContext());



                            //sets adapter to recyclerview
                            messageListView.setAdapter(recyclerAdapter);

                            //scrolls to last item in list in adapter
                            messageListView.scrollToPosition(recyclerAdapter.getItemCount()-1);




                        }//end if messageList not null









                    }else if (success == 0){
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                        builder.setMessage(jsonResponse.getString("message"))
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                    }else{
                        Log.e("Response", jsonResponse.getString("message"));
                    }







                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }//end onResponse




        };//end response listener




        //start delayed Runnable to get messages every 5 seconds
        getMessageRunnable.run();

        if (initialMessageRequest) {
            //requests the initial messages
            MessageGetRequest messageGetRequest = new MessageGetRequest(1, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);

            queue.add(messageGetRequest);
            initialMessageRequest = false;

        }




        //listener for sent button
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newUserMessage = newMessage.getText().toString();
                Log.e("Sending text contents", newUserMessage);

                //if not blank do this
                if (!newUserMessage.equals("")){

                    //makes a send request to the server
                    SendMessageRequest sendMessageRequest = new SendMessageRequest(1, loggedInUser, newUserMessage,sendListener);
                    RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);
                    queue.add(sendMessageRequest);

                    //resets the new message input field to blank
                    newMessage.setText("");

                    //makes the keyboard go away
                    closeKeyboard();


                }else{

                    Toast.makeText(MenuActivity.this, "Type In Text!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }//end onCreate


    //Got this from https://codinginflow.com/tutorials/android/hide-soft-keyboard-programmatically
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }







    //  this is the code to stop the Runnable:   mHandler.removeCallbacks(getMessageRunnable);
    Runnable getMessageRunnable = new Runnable() {
        @Override
        public void run() {

            //this calls a different constructor for MessageGetRequest which is supposed to return only new messages
            if (initialMessageRequest == false) {
                MessageGetRequest refreshRequest = new MessageGetRequest(1, timeStampIndex, refreshListener);
                RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);

                queue.add(refreshRequest);

            }

            //repeats this runnable every 5 seconds
            mHandler.postDelayed(this, 5000);
        }
    };




    //menu stuff
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chatrooms) {
            startActivity(new Intent(MenuActivity.this, chatroomActivity.class));
        } else if (id == R.id.nav_friends) {
            startActivity(new Intent(MenuActivity.this, FriendsActivity.class));
        }

       /* if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
