package ch.epfl.sweng.vanjel.chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.epfl.sweng.vanjel.R;

/**
 * A class to populate the chatList RecyclerView in ChatListActivity
 *
 * @author Etienne Caquot
 */
public class ChatListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Chat> chatList;

    /**
     *
     * @param context the context of Adapter
     * @param chatList the list of Chats the user participates in
     */
    public ChatListAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChatListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_chat,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Chat chat = chatList.get(position);
        ((ChatListAdapter.ViewHolder) holder).bind(chat);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChatActivity.class);
                intent.putExtra("contactUID",chat.getContactUid());
                intent.putExtra("contactName",chat.getContactName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    /**
     * A ViewHolder for the chats
     */
    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView contact, time, lastMessage;

        /**
         * Creates the ViewHolder
         * @param itemView the View
         */
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            contact = itemView.findViewById(R.id.contactName);
            time = itemView.findViewById(R.id.time);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }

        /**
         * Populate the ViewHolder with a Chat
         * @param chat the chat to add to the ViewHolder
         */
        void bind(Chat chat) {
            contact.setText(chat.getContactName());
            time.setText(chat.getTime());
            lastMessage.setText(chat.getLastMessage());
        }
    }
}