package ch.epfl.sweng.vanjel.chat;

import android.content.Context;
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

    private Context mContext;
    private List<Message> mMessageList;
    private String mUserUid;

    /**
     * Constructor of MessageListAdapter
     * @param context the context of Adapter
     * @param messageList the list of all message to put in the adapter
     */
    public MessageListAdapter(Context context, List<Message> messageList, String userUid) {
        mContext = context;
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT || viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_send_message, parent, false);
            return new SentMessageHolder(view, true);
        }/* else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_received_message, parent, false);
            return new ReceivedMessageHolder(view);
        }*/
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((SentMessageHolder) holder).bind(message);
//                ((ReceivedMessageHolder) holder).bind(message);
        }
    }


    /**
     * A ViewHolder for sent messages
     */
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        /**
         * Create ViewHolder
         * @param itemView the View
         */
        SentMessageHolder(View itemView, boolean send) {
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


    /**
     * A ViewHolder for received messages
     */
/*    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        /**
         * Create ViewHolder
         * @param itemView the View
         */
/*        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.receiveMessage);
            timeText = itemView.findViewById(R.id.receivedDate);
        }

        /**
         * Populate the ViewHolder
         * @param message the Message to display
         */
/*        void bind(Message message) {
            messageText.setText(message.getMessage());
            timeText.setText(message.getTime());
        }
    }*/
}