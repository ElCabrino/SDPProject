package ch.epfl.sweng.vanjel.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.models.Doctor;
import ch.epfl.sweng.vanjel.models.Patient;
import ch.epfl.sweng.vanjel.models.User;

/**
 * Class used to chat with a other user
 *
 * @author Etienne CAQUOT
 */
public class ChatListActivity extends AppCompatActivity {

    private RecyclerView chatList;

    private Map<String,Chat> chats;

    private final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();
    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();

    private String userUid;
    private Map<String,String> UidToName;

    private int getChats; //flag to be sure we fetched Patients and Doctors

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        if (auth.getCurrentUser() != null) {
            userUid = auth.getCurrentUser().getUid();
            chats = new HashMap<>();
            UidToName = new HashMap<>();
            chatList = findViewById(R.id.chatList);
            chatList.setLayoutManager(new LinearLayoutManager(this));
            getAllUsers();
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Get all chats that the used is involved in (his UID is in the chat UID) and put them in the ChatListAdapter
     */
    private void getChats() {
        database.getReference("Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String chatUid = snapshot.getKey();
                    if(chatUid != null && chatUid.contains(userUid)){
                        String contactUid = snapshot.getKey().replace(userUid,"");
                        String contactName =  UidToName.get(contactUid);
                        String message = (String) snapshot.child("text").getValue();
                        String time = (String) snapshot.child("time").getValue();
                        chats.put(chatUid, new Chat(time,message,contactName,contactUid));
                    }
                }
                updateAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        });
    }

    /**
     * Get all user of the application and put them in a map uid->names
     */
    private void getAllUsers() {
        getChats = 0;
        getClassUsers(Patient.class, "Patient");
        getClassUsers(Doctor.class, "Doctor");
    }

    /**
     * Used to get Users of subclass c (either Doctor or Patient in our case)
     * @param c subclass of User
     * @param type String to represent type of users
     */
    private void getClassUsers(final Class<? extends User> c, String type) {
        database.getReference(type).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(c);
                    c.cast(user);
                    if ((snapshot.getKey() != null)&&(user != null)) {
                        UidToName.put(snapshot.getKey(), user.toString());
                    }
                }
                getChats++;
                if(getChats > 0){
                    // got all users and get chats
                    getChats();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        });
    }

    /**
     * Update the adapter
     */
    private void updateAdapter(){
        ChatListAdapter chatListAdapter = new ChatListAdapter(this, chats);
        chatList.setAdapter(chatListAdapter);
        chatListAdapter.notifyDataSetChanged();
    }
}
