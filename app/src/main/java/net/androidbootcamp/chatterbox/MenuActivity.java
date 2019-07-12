package net.androidbootcamp.chatterbox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.androidbootcamp.chatterbox.ui.login.LoginActivity;
import net.androidbootcamp.chatterbox.ui.login.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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

    private ArrayList<HashMap<String, String>> messageList;

    private EditText newMessage;
    private ImageButton sendMessageBtn;

    private Handler mHandler = new Handler();
    private String chatID;

    Response.Listener<String> responseListener;


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


        //start delayed Runnable to get messages every 1 second
        getMessageRunnable.run();

        //reference for listview
        messageListView = (ListView)findViewById(R.id.messages_view);

        //gets the username we passed from LoginActivity
        Intent intent = getIntent();
        loggedInUser = intent.getStringExtra("username");

        //references to new message edittext and send message button
        newMessage = (EditText)findViewById(R.id.typingBox);
        sendMessageBtn = (ImageButton)findViewById(R.id.typingSendButton);

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo OnClickListener for send message button.
            }
        });







        // listens for server response then fills the listview
        responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt(KEY_SUCCESS);

                    if (success == 1){
                        // references to responses and a hashmap to contain them
                        String username = jsonResponse.getString(KEY_USERID);
                        String message = jsonResponse.getString(KEY_MESSAGE);
                        String timestamp = jsonResponse.getString(KEY_TIMESTAMP);
                        HashMap<String, String> map = new HashMap<>();

                        // adds the responses to a HashMap
                        map.put(KEY_USERID, username);
                        map.put(KEY_MESSAGE, message);
                        map.put(KEY_TIMESTAMP, timestamp);

                        //adds the HashMap to an ArrayList to use in a adapter
                        messageList.add(map);

                        //adapter to insert arraylist with server response to listview
                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        //first arg is the context
                        //second arg is the arraylist being used
                        //third arg is the layout file being used to display the listview items in a certain way, in this case message.xml
                        //fourth arg is a String array which holds the keys to arraylist to display their values
                        //fifth arg is a int array which holds the references to the view in message.xml or what goes where
                        ListAdapter adapter = new SimpleAdapter(MenuActivity.this,
                                                                messageList,
                                                                R.layout.message,
                                                                new String[]{KEY_USERID + "-" + KEY_TIMESTAMP, KEY_MESSAGE},
                                                                new int[]{R.id.name, R.id.message_body});

                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        //sets adapter for the listview
                        messageListView.setAdapter(adapter);





                    //something went wrong with the database, display the error message from response
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                        builder.setMessage(jsonResponse.getString("message"))
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };//end response listener








    }//end onCreate

    //todo Need to fix this when i figure how, also not exactly sure where to put this
    //  this is the code to stop the Runnable:   mHandler.removeCallbacks(getMessageRunnable);
    Runnable getMessageRunnable = new Runnable() {
        @Override
        public void run() {

            MessageGetRequest messageGetRequest = new MessageGetRequest(chatID, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);
            queue.add(messageGetRequest);
            mHandler.postDelayed(this, 1000);
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
