package net.androidbootcamp.chatterbox.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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

public class FindFriendsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    !!                                  S T A R T   H E R E                                       !!
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chatrooms) {
            Intent chatRoomIntent = new Intent(FindFriendsActivity.this, ChatRoomActivity.class);
            chatRoomIntent.putExtra("userID", loggedInUser);
            chatRoomIntent.putExtra("active_chat", activeChat);
            startActivity(chatRoomIntent);
        } else if (id == R.id.nav_friends) {
            Intent friendsIntent = new Intent(FindFriendsActivity.this, FriendsActivity.class);
            friendsIntent.putExtra("userID", loggedInUser);
            startActivity(friendsIntent);
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
