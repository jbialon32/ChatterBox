package net.androidbootcamp.chatterbox.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.androidbootcamp.chatterbox.R;
import net.androidbootcamp.chatterbox.adapters.ChatroomAdapter;
import net.androidbootcamp.chatterbox.inviteGen.InviteGenerator;
import net.androidbootcamp.chatterbox.objects.ChatroomObject;
import net.androidbootcamp.chatterbox.requests.ChangeChatRequest;
import net.androidbootcamp.chatterbox.requests.CreateChatInviteRequest;
import net.androidbootcamp.chatterbox.requests.CreateChatRequest;
import net.androidbootcamp.chatterbox.requests.GetChatIDRequest;
import net.androidbootcamp.chatterbox.requests.JoinChatInviteRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String tempChatName;
    private String loggedInUser;
    private int currentActiveChat;
    private int lastItemInList;
    private int tempChatID;

    private ChatroomAdapter chatAdapter;

    private ArrayList<ChatroomObject> chatRoomList = new ArrayList<>();

    //references
    private EditText newChat;
    private EditText newInvite;
    private Button joinInviteBtn;
    private Button createInviteBtn;
    private Button createChatBtn;
    private RecyclerView chatListView;

    private Response.Listener<String> chatListener;
    private Response.Listener<String> newChatListener;
    private Response.Listener<String> newInviteListener;
    private Response.Listener<String> joinInviteListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatrooms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //references
        chatListView = (RecyclerView) findViewById(R.id.chats_view);
        chatListView.setHasFixedSize(true);
        chatListView.setLayoutManager(new LinearLayoutManager(this));
        newChat = (EditText)findViewById(R.id.enterChatroomName);
        newInvite = (EditText)findViewById(R.id.enterChatroomPassport);
        joinInviteBtn = (Button)findViewById(R.id.joinChatButton);
        createInviteBtn = (Button)findViewById(R.id.createInviteButton);
        createChatBtn = (Button)findViewById(R.id.createChatButton);

        Intent getUserIntent = getIntent();
        loggedInUser = getUserIntent.getStringExtra("userID");
        currentActiveChat = getUserIntent.getIntExtra("active_chat", 0);

        // Adds functionality to createChatBtn
        createChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!newChat.getText().toString().isEmpty()){
                    CreateChatRequest chatChangeRequest = new CreateChatRequest(loggedInUser, newChat.getText().toString(), newChatListener);
                    RequestQueue queue = Volley.newRequestQueue(ChatRoomActivity.this);

                    queue.add(chatChangeRequest);
                } else {
                    System.out.println("Enter a server name!");
                }

            }
        });

        // Adds functionality to createChatBtn
        createInviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inviteCode = InviteGenerator.Generate();

                CreateChatInviteRequest createInviteRequest = new CreateChatInviteRequest(String.valueOf(currentActiveChat), inviteCode, newInviteListener);
                RequestQueue queue = Volley.newRequestQueue(ChatRoomActivity.this);

                queue.add(createInviteRequest);
            }
        });

        // Adds functionality to acceptInviteBtn
        joinInviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newInvite.getText().toString().length() > 0) {

                    String inviteCode = newInvite.getText().toString();

                    JoinChatInviteRequest joinInviteRequest = new JoinChatInviteRequest(loggedInUser, inviteCode, joinInviteListener);
                    RequestQueue queue = Volley.newRequestQueue(ChatRoomActivity.this);

                    queue.add(joinInviteRequest);
                }
            }
        });

        joinInviteListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");

                    if(success == 1) {

                        Intent intent = new Intent(ChatRoomActivity.this, MenuActivity.class);

                        //this will pass the userID sent from server to the MenuActivity
                        intent.putExtra("userID", loggedInUser); // passes the username received from userID to the MenuActivity

                        //start the MenuActivity
                        ChatRoomActivity.this.startActivity(intent);

                    } else {
                        Log.e("Response", jsonResponse.getString("message"));
                        newInvite.setText("Invalid Invite");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        newInviteListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    String inviteCode = jsonResponse.getString("message");

                    if(success == 1) {

                        newInvite.setText(inviteCode);

                    } else if(success == 2){

                        inviteCode = InviteGenerator.Generate();
                        CreateChatInviteRequest createInviteRequest = new CreateChatInviteRequest(String.valueOf(currentActiveChat), inviteCode, newInviteListener);

                        RequestQueue queue = Volley.newRequestQueue(ChatRoomActivity.this);

                        queue.add(createInviteRequest);

                    } else {
                        Log.e("Response", jsonResponse.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        // listens for new chat response and acts accordingly
        newChatListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");

                    if(success == 1) {
                        int newChatID = jsonResponse.getInt("data");
                        currentActiveChat = newChatID;
                        ChangeChatRequest chatChangeRequest = new ChangeChatRequest(Integer.parseInt(loggedInUser), currentActiveChat, newChatListener);
                        RequestQueue queue = Volley.newRequestQueue(ChatRoomActivity.this);

                        queue.add(chatChangeRequest);

                        Intent intent = new Intent(ChatRoomActivity.this, MenuActivity.class);

                        //this will pass the userID sent from server to the MenuActivity
                        intent.putExtra("userID", loggedInUser); // passes the username received from userID to the MenuActivity

                        //start the MenuActivity
                        ChatRoomActivity.this.startActivity(intent);
                    } else {
                        Log.e("Response", jsonResponse.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        // listens for server response then fills the recyclerview
        chatListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    Log.e("JsonResponse", response);

                    if(success == 1) {
                        JSONArray chatsInfo = jsonResponse.getJSONArray("data");
                        Log.e("responseListenerArray", chatsInfo.toString());

                        if (!chatsInfo.isNull(0)) {
                            for(int i=0; i < chatsInfo.length(); i++){
                                Log.d("Inside for loop", "made it inside for loop");

                                JSONObject messageInfo = chatsInfo.getJSONObject(i);
                                tempChatID = messageInfo.getInt("chat_id");
                                tempChatName = messageInfo.getString("chat_name");

                                ChatroomObject chatObject = new ChatroomObject(tempChatID, tempChatName);

                                chatRoomList.add(chatObject);

                                //stores initial index of last item in array
                                lastItemInList = chatRoomList.size()-1;

                                Log.e("messageList contents", chatRoomList.toString());

                            }//end for loop

                            //creates new adapter for recyclerview
                            chatAdapter = new ChatroomAdapter(getApplicationContext(), chatRoomList);

                            chatAdapter.setOnItemClickListener(new ChatroomAdapter.ClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    ChangeChatRequest chatChangeRequest = new ChangeChatRequest(Integer.parseInt(loggedInUser), position, chatListener);
                                    RequestQueue queue = Volley.newRequestQueue(ChatRoomActivity.this);

                                    queue.add(chatChangeRequest);

                                    Intent intent = new Intent(ChatRoomActivity.this, MenuActivity.class);

                                    //this will pass the userID sent from server to the MenuActivity
                                    intent.putExtra("userID", loggedInUser); // passes the username received from userID to the MenuActivity

                                    //start the MenuActivity
                                    ChatRoomActivity.this.startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(int position, View v) {
                                    Log.d("LONGCLICK", "onItemLongClick position: " + position);
                                }
                            });

                            //sets adapter to recyclerview
                            chatListView.setAdapter(chatAdapter);

                            //scrolls to last item in list in adapter
                            chatListView.scrollToPosition(chatAdapter.getItemCount()-1);

                        }//end if messageList not null

                    }else if (success == 0){
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);
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

        //requests the initial messages
        GetChatIDRequest chatGetRequest = new GetChatIDRequest(loggedInUser, chatListener);
        RequestQueue queue = Volley.newRequestQueue(ChatRoomActivity.this);

        queue.add(chatGetRequest);

    }

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



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_chatrooms) {
            //startActivity(new Intent(MenuActivity.this, ChatRoomActivity.class));
        } else if (id == R.id.nav_friends) {
            startActivity(new Intent(ChatRoomActivity.this, FriendsActivity.class));
        } /*else if (id == R.id.nav_filler) {

        } else if (id == R.id.nav_filler2) {

        } else if (id == R.id.nav_filler3) {

        } else if (id == R.id.nav_filler4) {

        }*/

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
