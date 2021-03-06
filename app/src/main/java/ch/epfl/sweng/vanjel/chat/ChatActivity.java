package ch.epfl.sweng.vanjel.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.R;

/**
 * Class used to display the list of all chats a user participates
 *
 * @author Etienne CAQUOT
 */
public class ChatActivity extends AppCompatActivity {

    private MessageListAdapter messageAdapter;
    private RecyclerView messageRecycler;
    private List<Message> messageList;

    private EditText message;

    private final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();
    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();

    private String senderUid;
    private String contactUid;
    private String chatUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        try {
            bind();
            getLastMessage();
        } catch (FirebaseAuthInvalidUserException e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occurred when initializing the chat", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method bind all the necessary components for the activity
     */
    private void bind() throws FirebaseAuthInvalidUserException {
        TextView contactName = findViewById(R.id.contactName);
        if (getIntent().getExtras()!=null) {
            contactName.setText(getIntent().getExtras().getString("contactName"));
        }
        message = findViewById(R.id.messageToSend);
        messageRecycler = findViewById(R.id.RecyclerViewChat);
        messageList = new ArrayList<>();
        if (auth.getCurrentUser()!=null) {
            senderUid = auth.getCurrentUser().getUid();
            messageRecycler.setLayoutManager(new LinearLayoutManager(this));
            contactUid = getIntent().getExtras().getString("contactUID");
            setChat();
        } else {
            throw new FirebaseAuthInvalidUserException("chat", "No user logged in");
        }
    }

    private void setChat(){
        assert contactUid != null;
        if (senderUid.compareTo(contactUid) > 0) {
            chatUid = contactUid+senderUid;
        } else {
            chatUid = senderUid+contactUid;
        }
    }

    /**
     * Sends the message written called when click on send button
     */
    public void sendMessage(View v){
        if (message.getText().length() != 0){
            DateFormat dateFormat = new SimpleDateFormat("H:mm");
            String dateString = dateFormat.format(new Date());
            database.getReference("Chat").child(chatUid).updateChildren(createMessage(message.getText().toString(), dateString)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ChatActivity.this, "Message successfully sent.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ChatActivity.this, "Failed to send message.", Toast.LENGTH_SHORT).show();
                }
            });
            message.setText("");
        }
    }

    /**
     * Method to create a Map<String,Object> to represent a message data in Firebase, need time, text, sender and receiver.
     * @param text the message text
     * @param date the message date
     * @return Message to store in Firebase
     */
    private Map<String, Object> createMessage(String text, String date) {
        Map<String, Object> message = new HashMap<>();
        message.put("sender", senderUid);
        message.put("receiver", contactUid);
        message.put("text", text);
        message.put("time", date);
        return message;
    }

    /**
     * Method get fetch from Firebase the last message posted to the channel
     */
    private void getLastMessage(){
        database.getReference("Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getKey() != null && snapshot.getKey().equals(chatUid)) {
                            String message = (String) snapshot.child("text").getValue();
                            String time = (String) snapshot.child("time").getValue();
                            String sender = (String) snapshot.child("sender").getValue();
                            messageList.add(new Message(time, message, sender));
                        }
                }
                messageAdapter = new MessageListAdapter(messageList, senderUid);
                messageRecycler.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        });
    }
}

