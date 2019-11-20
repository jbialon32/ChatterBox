package net.androidbootcamp.chatterbox.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.androidbootcamp.chatterbox.R;
import net.androidbootcamp.chatterbox.objects.ChatroomObject;

import java.util.List;

/**
 * Edited from https://medium.com/android-grid/how-to-fetch-json-data-using-volley-and-put-it-to-recyclerview-android-studio-383059a12d1e
 */

public class ChatroomAdapter
        extends RecyclerView.Adapter<ChatroomAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private Context context;
    private List<ChatroomObject> chatroomObjectList;

    public ChatroomAdapter(Context context, List<ChatroomObject> chatroomObjectList) {
        this.context = context;
        this.chatroomObjectList = chatroomObjectList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.chatroom_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //sets the message animation
        holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_up_animation));

        ChatroomObject chatRoom = chatroomObjectList.get(position);

        System.out.println("AHAH GOT IT: " + chatRoom.getChatID());

        holder.serverID.setText(Integer.toString(chatRoom.getChatID()));
        holder.chatName.setText(chatRoom.getChatName());

    }

    // Gets number of items in chatroomObjectList
    @Override
    public int getItemCount() {
        return chatroomObjectList.size();
    }

    // Connects variables
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView chatName;
        public TextView serverID;
        public Button chatButton;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.chat_layout);
            chatButton = itemView.findViewById(R.id.chooseChat);
            chatName = itemView.findViewById(R.id.chat_name);
            serverID = itemView.findViewById(R.id.server_id);

            chatButton.setOnClickListener(this);
            chatButton.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(Integer.parseInt(serverID.getText().toString()), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ChatroomAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

}