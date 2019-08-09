package net.androidbootcamp.chatterbox.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.androidbootcamp.chatterbox.R;
import net.androidbootcamp.chatterbox.objects.MessageObject;

import java.util.List;

/**
 * Edited from https://medium.com/android-grid/how-to-fetch-json-data-using-volley-and-put-it-to-recyclerview-android-studio-383059a12d1e
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private List<MessageObject> messageObjectList;

    public MessageAdapter(Context context, List<MessageObject> messageObjectList) {
        this.context = context;
        this.messageObjectList = messageObjectList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //sets the message animation
        holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_up_animation));

        MessageObject message = messageObjectList.get(position);

        holder.message.setText(message.getMessage());
        holder.time.setText(String.valueOf(message.getTimestamp()));
        holder.author.setText(String.valueOf(message.getUsername()));

    }

    @Override
    public int getItemCount() {
        return messageObjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message, time, author;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.message_layout);
            message = itemView.findViewById(R.id.message_body);
            time = itemView.findViewById(R.id.time);
            author = itemView.findViewById(R.id.name);
        }
    }

}