package net.androidbootcamp.chatterbox;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class chatroomActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView chatroomList;
    private ArrayAdapter adapter;

    private Response.Listener<String> responseListener;




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

        chatroomList = findViewById(R.id.chatroom_list);


        responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int success = jsonObject.getInt("success");

                    if (success == 1){
                        JSONArray chatroomResponse = jsonObject.getJSONArray("data");

                        if (!chatroomResponse.isNull(0)){

                            for (int index = 0; index < chatroomResponse.length(); index++){
                                JSONObject arrayItem = chatroomResponse.getJSONObject(index);

                                //todo
                            }
                        }
                    }else{
                        //displays error message from server in not success
                        AlertDialog.Builder builder = new AlertDialog.Builder(chatroomActivity.this);
                        builder.setMessage(jsonObject.getString("message"))
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


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
            //startActivity(new Intent(MenuActivity.this, chatroomActivity.class));
        } else if (id == R.id.nav_friends) {
            startActivity(new Intent(chatroomActivity.this, FriendsActivity.class));
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
