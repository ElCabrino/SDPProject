package ch.epfl.sweng.vanjel.chat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.vanjel.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.Profile;
import ch.epfl.sweng.vanjel.R;

/**
 * @author Etienne CAQUOT
 */
public class ChatActivity extends AppCompatActivity {

    private MessageListAdapter messageAdapter;
    private RecyclerView messageRecycler;

    private TextView contactName;
    private EditText message;
    private Button sendMessageButton;

    private List<Message> messageList;

    FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();
    FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    DatabaseReference ref;

    private String senderUid;
    private String contactUid;
    private String chatUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bind();
    }

    /**
     * This method bind all the necessary components for the activity
     */
    private void bind() {
        contactName = findViewById(R.id.contactName);
        contactName.setText(getIntent().getExtras().getString("doctorName"));
        message = findViewById(R.id.messageToSend);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        messageRecycler = findViewById(R.id.RecyclerViewChat);
        messageList = new ArrayList<>();
        senderUid = auth.getUid();
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        contactUid = getIntent().getExtras().getString("doctorUID");
        if (senderUid.length() > contactUid.length()) {
            chatUid = contactUid+senderUid;
        } else {
            chatUid = senderUid+contactUid;
        }
    }

    /**
     * Sends the message written
     */
    // TODO Store in firebase maybe under a combinaison of both Uid
    // see https://medium.com/@ngengesenior/database-structure-of-one-to-one-chat-app-with-firebase-93f813184dfe
    public void sendMessage(View v){
        if (message.getText().length() != 0){
            DateFormat dateFormat = new SimpleDateFormat("hh.mm");
            String dateString = dateFormat.format(new Date());
            messageList.add(new Message(dateString,message.getText().toString(),senderUid));
            messageAdapter = new MessageListAdapter(this, messageList, senderUid);
            messageRecycler.setAdapter(messageAdapter);
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

    private Map<String, Object> createMessage(String text, String date) {
        Map<String, Object> message = new HashMap<>();
        message.put("sender", senderUid);
        message.put("receiver", contactUid);
        message.put("text", text);
        message.put("time", date);
        return message;
    }

    //TODO retrive message from DB, and update the Recycler/adapter.
}
