package ch.epfl.sweng.vanjel.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.epfl.sweng.vanjel.R;

/**
 * Class used to populate the messages in the RecyclerView ChatActivity
 *
 * @author Etienne Caquot
 */
public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private List<Message> mMessageList;
    private String mUserUid;

    /**
     * Constructor of MessageListAdapter
     * @param messageList the list of all message to put in the adapter
     * @param userUid the user's id
     */
    MessageListAdapter(List<Message> messageList, String userUid) {
        mMessageList = messageList;
        mUserUid = userUid;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessageList.get(position);
        if (message.getSenderUid().equals(mUserUid)) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_send_message, parent, false);
            return new MessageHolder(view, true);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_received_message, parent, false);
            return new MessageHolder(view,false);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(position);
        if(holder.getItemViewType() == VIEW_TYPE_MESSAGE_SENT) {
            ((MessageHolder) holder).bind(message);
        } else {
            ((MessageHolder) holder).bind(message);
        }
    }


    /**
     * A ViewHolder for messages
     */
    private class MessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        /**
         * Create ViewHolder
         * @param itemView the View
         */
        MessageHolder(View itemView, boolean send) {
            super(itemView);
            if (send) {
                messageText = itemView.findViewById(R.id.sendMessage);
                timeText = itemView.findViewById(R.id.sendDate);
            } else {
                messageText = itemView.findViewById(R.id.receiveMessage);
                timeText = itemView.findViewById(R.id.receivedDate);
            }
        }

        /**
         * Populate the ViewHolder
         * @param message the Message to display
         */
        void bind(Message message) {
            messageText.setText(message.getMessage());
            timeText.setText(message.getTime());
        }
    }
}