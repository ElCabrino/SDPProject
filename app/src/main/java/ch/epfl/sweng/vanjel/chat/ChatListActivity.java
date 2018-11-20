package ch.epfl.sweng.vanjel.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.vanjel.Doctor;
import ch.epfl.sweng.vanjel.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.Patient;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.User;

/**
 * Class used to chat with a other user
 *
 * @author Etienne CAQUOT
 */
public class ChatListActivity extends AppCompatActivity {

    private static final String TAG = "ChatListActivity";

    private RecyclerView chatList;
    private ChatListAdapter chatListAdapter;

    private List<Chat> chats;

    FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();
    FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();

    private String userUid;
    private HashMap<String,String> UidToName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        userUid = auth.getCurrentUser().getUid();
        chats = new ArrayList<>();
        UidToName = new HashMap<>();
        chatList = findViewById(R.id.chatList);
        getAllUsers();
        getChats();
        chatList.setLayoutManager(new LinearLayoutManager(this));
        chatListAdapter = new ChatListAdapter(this,chats);
        chatList.setAdapter(chatListAdapter);
    }

    /**
     * get all chats that the used is involved in (his UID is in the chat UID) and put them in the ChatListAdapter
     */
    private void getChats() {
        database.getReference("Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey().contains(userUid)){
                        String contactUid = snapshot.getKey().replace(userUid,"");
                        String contactName =  UidToName.get(contactUid);
                        String message = (String) snapshot.child("text").getValue();
                        String time = (String) snapshot.child("time").getValue();
                        chats.add(new Chat(time,message,contactName,contactUid));
                    }
                }
                updateAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * get all user of the application and put them in a map uid->names
     */
    private void getAllUsers() {
        getClassUsers(Patient.class, "Patient");
        getClassUsers(Doctor.class, "Doctor");
    }

    private void getClassUsers(final Class<? extends User> c, String type) {
        database.getReference(type).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(c);
                    c.cast(user);
                    UidToName.put(snapshot.getKey(),user.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * update the adapter
     */
    private void updateAdapter(){
        chatListAdapter = new ChatListAdapter(this,chats);
        chatList.setAdapter(chatListAdapter);
        chatListAdapter.notifyDataSetChanged();
    }
}
