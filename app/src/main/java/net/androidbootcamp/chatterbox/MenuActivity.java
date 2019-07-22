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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.androidbootcamp.chatterbox.Requests.SendMessageRequest;
import net.androidbootcamp.chatterbox.messages.MessageFactory;
import net.androidbootcamp.chatterbox.messages.MessageGetRequest;
import net.androidbootcamp.chatterbox.messages.MessageObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_USERID = "user";
    private static final String KEY_CHATID = "chat";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_TIMESTAMP = "timestamp";

    //holds username from LoginActivity
    private String loggedInUser;

    private ListView messageListView;

    private ArrayList<HashMap<String, String>> messageList = new ArrayList<>();

    private EditText newMessage;
    private ImageButton sendMessageBtn;

    private Handler mHandler = new Handler();
    private int chatID;

    private Response.Listener<String> responseListener;
    private Response.Listener<String> sendListener;

    private ArrayList<HashMap<String, String>> availableMessages = new ArrayList<>();

    private SimpleAdapter adapter;

    private boolean initialMessageRequest;

    private String timeStampIndex = "";

    //private RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);;


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



        //reference for listview
        messageListView = (ListView)findViewById(R.id.messages_view);
        //messageListView.setTranscriptMode(messageListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        //messageListView.setStackFromBottom(true);

        //gets the username we passed from LoginActivity
        Intent intent = getIntent();
        loggedInUser = intent.getStringExtra("userID");

        //references to new message edittext and send message button
        newMessage = (EditText)findViewById(R.id.typingBox);
        sendMessageBtn = (ImageButton)findViewById(R.id.typingSendButton);










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

                        Toast.makeText(MenuActivity.this, message, Toast.LENGTH_SHORT).show();

                    }else{
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
                        Log.e("responseListener", messagesInfo.toString());


                        if (!messagesInfo.isNull(0)) {
                            for(int i=0; i < messagesInfo.length(); i++){
                                Log.d("Inside for loop", "made it inside for loop");




                                JSONObject messageInfo = messagesInfo.getJSONObject(i);
                                //chatID = messageInfo.getInt("chat_id");
                                String username = messageInfo.getString("user");
                                String message = messageInfo.getString("message");
                                String timestamp = messageInfo.getString("timestamp");

                                //this will keep the timestamp of the last item in the map
                                timeStampIndex = timestamp;

                                HashMap<String,String> map = new HashMap<>();
                                //map.put("chat_id", String.valueOf(chatID));
                                map.put("user", username);
                                map.put("message", message);
                                map.put("timestamp", timestamp);


                                messageList.add(map);








                                Log.e("messageList contents", messageList.toString());




                            }




                        }


                        if (messageList != null) {
                            // adapter to display arraylist using message.xml as formating

                            adapter = new SimpleAdapter(MenuActivity.this,
                                    messageList,
                                    R.layout.message,
                                    new String[]{KEY_USERID, KEY_MESSAGE},
                                    new int[]{R.id.name, R.id.message_body});


                            //sets adapter for the listview
                            messageListView.setAdapter(adapter);



                            //adjusts listview height based on messages (needed because we used scrollview)
                            Utils.setListViewHeightBasedOnChildren(messageListView);

                            scrollMyListViewToBottom();
                            //messageListView.smoothScrollToPosition(adapter.getCount() - 1);


                            Log.e("list count", String.valueOf(messageListView.getCount()));




                        }//end if


                        Log.e("timestampindex", timeStampIndex);





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


            // adapter to display arraylist using message.xml as formating

        /*if (messageList.size() > 0) {
            adapter = new SimpleAdapter(MenuActivity.this,
                    messageList,
                    R.layout.message,
                    new String[]{KEY_USERID, KEY_MESSAGE},
                    new int[]{R.id.name, R.id.message_body});


            //sets adapter for the listview
            messageListView.setAdapter(adapter);


            //adjusts listview height based on messages (needed because we used scrollview)
            Utils.setListViewHeightBasedOnChildren(messageListView);

            //scrollMyListViewToBottom();
            messageListView.smoothScrollToPosition(adapter.getCount() - 1);

            messageList.clear();
            Log.e("list count", String.valueOf(messageListView.getCount()));
        }*/


        //start delayed Runnable to get messages every 1 second
        getMessageRunnable.run();

        if (initialMessageRequest) {
            //requests the initial messages
            MessageGetRequest messageGetRequest = new MessageGetRequest(1, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);
            //queue.getCache().clear();
            queue.add(messageGetRequest);
            initialMessageRequest = false;
            //queue.getCache().clear();
        }





        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newUserMessage = newMessage.getText().toString();
                Log.e("Sending text contents", newUserMessage);

                if (!newUserMessage.equals("")){

                    SendMessageRequest sendMessageRequest = new SendMessageRequest(1, loggedInUser, newUserMessage,sendListener);
                    RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);
                    queue.add(sendMessageRequest);
                    Log.e("After sentRequest", "sent request");
                    newMessage.setText("");
                    closeKeyboard();
                    adapter.notifyDataSetChanged();

                }else{
                    //newMessage.setError("Type in Message!");
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


    //Got this from https://stackoverflow.com/questions/22680596/how-to-make-the-listview-scroll-vertically-up-automatically-when-items-are-added
    private void scrollMyListViewToBottom() {
        messageListView.post(new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    // Select the last row so it will scroll into view...
                    messageListView.setSelection(adapter.getCount() - 1);
                    messageListView.smoothScrollToPosition(adapter.getCount() - 1);
                }
            }
        });
    }




    //  this is the code to stop the Runnable:   mHandler.removeCallbacks(getMessageRunnable);
    Runnable getMessageRunnable = new Runnable() {
        @Override
        public void run() {

            //this calls a different constructor for MessageGetRequest which is supposed to return only new messages
            if (initialMessageRequest == false) {
                MessageGetRequest refreshRequest = new MessageGetRequest(1, timeStampIndex, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);
                //queue.getCache().clear();
                queue.add(refreshRequest);



                Log.d("messageList size", String.valueOf(messageList.size()));
            }


            mHandler.postDelayed(this, 5000);
        }
    };





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
