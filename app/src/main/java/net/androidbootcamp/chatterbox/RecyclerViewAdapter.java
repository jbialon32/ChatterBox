package net.androidbootcamp.chatterbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.androidbootcamp.chatterbox.messages.MessageObject;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<MessageObject> messageObjectsList;
    private Context context;

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
        public LinearLayout linearLayout;
        public TextView time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message_body);
            name = itemView.findViewById(R.id.name);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            time = itemView.findViewById(R.id.time);
        }




    }//end ViewHolder class
}//end RecyclerViewAdapter class



