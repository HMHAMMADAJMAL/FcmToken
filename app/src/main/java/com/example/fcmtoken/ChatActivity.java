package com.example.fcmtoken;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "Tag one ";
    Toolbar toolbar;
    RecyclerView recyclerView;

    TextView name, userstatus;
    EditText msg;
    ImageButton send, attach;
    FirebaseAuth firebaseAuth;
    String uid, myuid;
    ValueEventListener valueEventListener;
    List<ModelChat> chatList;
    AdapterChat adapterChat;
    String msgs;
    Uri imageuri = null;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;
    boolean notify = false;
    private Button buttonLogouts;
    private Object Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseAuth = FirebaseAuth.getInstance();


        msg = findViewById(R.id.messaget);
        send = findViewById(R.id.sendmessage);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.chatrecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        uid = getIntent().getStringExtra("uid");

        Query userquery = users.child("uid").equalTo(uid);
        userquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // retrieve user data
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String nameh = "" + dataSnapshot1.child("name").getValue();
                    String typingto = "" + dataSnapshot1.child("typingTo").getValue();
                    if (typingto.equals(myuid)) {// if user is typing to my chat
                    }
                    name.setText(nameh);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                String message = msg.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {//if empty
                    Toast.makeText(ChatActivity.this, "Please Write Something Here", Toast.LENGTH_LONG).show();
                } else {
                    sendmessage(message);
                }
                msg.setText("");
            }
        });
        readMessages();
       checkCurrentUser();
//       getProviderData();
    }
    public void checkCurrentUser() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.i(TAG,"Current user is "+user.getUid());

            // User is signed in
        } else {
            Log.i(TAG, "No user is signed in   " + user);
            // No user is signed in
        }

    }
    private void readMessages() {

        chatList = new ArrayList<>();

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Chats");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
                chatList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                  ModelChat modelChat = dataSnapshot1.getValue(ModelChat.class);

//                    Log.i("Locations updated", "location: " + location);
//                    if (modelChat.getSender().equals(myuid) && modelChat.getReceiver().equals(uid))
//chatList.add(modelChat);
//                   chatList.add(modelChat);


//
//                    {
                   //     chatList.add(modelChat);                                             //              }

//                    else if (modelChat.getReceiver().equals(myuid) && modelChat.getSender().equals(uid))   {
//                        chatList.add(modelChat);
//
//
//
//                                                                                                           }


adapterChat = new AdapterChat(ChatActivity.this, chatList);
                    adapterChat.notifyDataSetChanged();
                    recyclerView.setAdapter(adapterChat);
           }
}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendmessage(final String message) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myuid);
        hashMap.put("receiver", uid);
        hashMap.put("message", message);
        databaseReference.child("Chats").push().setValue(hashMap);


        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Chats");

        adapterChat = new AdapterChat(ChatActivity.this, chatList);
        adapterChat.notifyDataSetChanged();
        recyclerView.setAdapter(adapterChat);

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    ref1.child("id").setValue(myuid);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Chats");

        adapterChat = new AdapterChat(ChatActivity.this, chatList);
        adapterChat.notifyDataSetChanged();
        recyclerView.setAdapter(adapterChat);


        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    ref2.child("id").setValue(uid);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


//    public void btnlogout(View view) {
//
//        Intent i =new Intent(getApplicationContext(),LoginsActivity.class);
//        startActivity(i);
//    }
}
