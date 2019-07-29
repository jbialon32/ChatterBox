package net.androidbootcamp.chatterbox.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.androidbootcamp.chatterbox.R;
import net.androidbootcamp.chatterbox.messages.MessageObject;

import java.util.List;


/*
Got this class from https://medium.com/@peterekeneeze/parsing-remote-json-to-recyclerview-android-1ad927e96d58
and modified it for this apps needs.
 */

/**
 * Custom adapter for a recycler view.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<MessageObject> messageObjectsList;
    private Context context;


    /**
     * Constructor for RecyclerViewAdapter class
     * @param messageObjectsList Parameter that accepts a list with messageObjects
     * @param context Parameter that accepts a context
     */
    public RecyclerViewAdapter(List<MessageObject> messageObjectsList, Context context) {
        this.messageObjectsList = messageObjectsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //this inflates the view using message.xml layout file
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message,viewGroup,false);

        return new ViewHolder(v);
    }

    /**
     * This method binds the data to the views in the ViewHolder
     * @param viewHolder A passed in ViewHolder
     * @param i Index
     *
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MessageObject messageObject = messageObjectsList.get(i);

        //sets the message animation
        viewHolder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_up_animation));


        viewHolder.message.setText(messageObject.getMessage());

        viewHolder.name.setText(messageObject.getUsername());
        viewHolder.time.setText(messageObject.getTimestamp());


    }

    @Override
    public int getItemCount() {
        return messageObjectsList.size();
    }

    /**
     * This class holds the views from message.xml
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView message;
        public TextView name;
        public RelativeLayout relativeLayout;
        public TextView time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message_body);
            name = itemView.findViewById(R.id.name);
            relativeLayout = itemView.findViewById(R.id.message_layout);
            time = itemView.findViewById(R.id.time);
        }




    }//end ViewHolder class
}//end RecyclerViewAdapter class



