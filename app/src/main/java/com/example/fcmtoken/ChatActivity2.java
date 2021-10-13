package com.example.fcmtoken;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.io.IOException;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class ChatActivity2 extends AppCompatActivity {
    private static final String TAG = "name";
    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView profile, block;
    TextView name, userstatus;
    EditText msg;
    EditText email;
    ImageButton send, attach;
    FirebaseAuth firebaseAuth;
    String uid, myuid, image;
    ValueEventListener valueEventListener;
    List<ModelChat> chatList;
    List<Model_User>  usersList;
    AdapterChat adapterChat;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users,users2;
    FirebaseUser firebaseUser;
    boolean notify = false;
    RecyclerView recyclerView2;

    AdapterUser adapterUser;

    List<ModelUsers> listuer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        name = findViewById(R.id.name);
        userstatus = findViewById(R.id.onlinetv);
        msg = findViewById(R.id.messaget);
        email = findViewById(R.id.email);
        send = findViewById(R.id.sendmessage);
        attach = findViewById(R.id.attachbtn);
        block = findViewById(R.id.block);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.chatrecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        uid = getIntent().getStringExtra("uid");
        Log.i(TAG,"Current user is "+uid);
        firebaseDatabase = FirebaseDatabase.getInstance();
//        String mail = email.getText().toString().trim();
//        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
//        HashMap<String, Object> hashMap2 = new HashMap<>();
//        hashMap2.put("id", "3723292829");
//        hashMap2.put("Email", mail);
//        databaseReference2.child("Users2").push().setValue(hashMap2);
        checkUserStatus();
        users = firebaseDatabase.getReference("Chats");

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                String message = msg.getText().toString().trim();

                if (TextUtils.isEmpty(message)) {//if empty
                    Toast.makeText(ChatActivity2.this, "Please Write Something Here", Toast.LENGTH_LONG).show();
                } else {
                    sendmessage(message);
                }
                msg.setText("");
            }
        });
        Query userquery = users.orderByChild("uid").equalTo(uid);
        userquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // retrieve user data
                name.setText("Welcome  " + user);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    String nameh = "" + dataSnapshot1.child("name").getValue();
                    String msg = "" + dataSnapshot1.child("message").getValue();
                    String email = "" + dataSnapshot1.child("email").getValue();
                    image = "" + dataSnapshot1.child("image").getValue();
                    String onlinestatus = "" + dataSnapshot1.child("onlineStatus").getValue();
                    String typingto = "" + dataSnapshot1.child("typingTo").getValue();
                    if (typingto.equals(myuid)) {// if user is typing to my chat
                        userstatus.setText("Typing....");// type status as typing
                    } else {
                        if (onlinestatus.equals("online")) {
                            userstatus.setText(onlinestatus);
                        } else {
                            Calendar calendar = Calendar.getInstance();
//                            calendar.setTimeInMillis(Long.parseLong(onlinestatus));
                            String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
                            userstatus.setText("Last Seen:" + timedate);
                        }
                    }
                    name.setText(nameh);
                    msg = "" + dataSnapshot1.child("message").getValue();
                    email = "" + dataSnapshot1.child("email").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        readMessages();

//        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
//        HashMap<String, Object> hashMap2 = new HashMap<>();
//        hashMap2.put("email","emails ");
//        databaseReference2.child("AuthUsers").push().setValue(email.getText().toString());

    }

    private void readMessages() {
        // show message after retrieving data
        chatList = new ArrayList<>();
        usersList = new ArrayList<>();
        listuer=new ArrayList<>();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Chats");
        DatabaseReference dbref2 = FirebaseDatabase.getInstance().getReference().child("AuthUsers");

        String userId = dbref.push().getKey();
        Log.d(TAG,"Chat user Id "+userId);
        Toast.makeText(getApplicationContext(), "Get key value of Chat User  "+userId, Toast.LENGTH_SHORT).show();
        String userId2= dbref.push().getKey();
        Log.d(TAG,"Auth user Id "+userId2);
        Toast.makeText(getApplicationContext(), "Get key value of Auth User  "+userId, Toast.LENGTH_SHORT).show();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chatList.clear();
                listuer.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users modelChat = dataSnapshot1.getValue(Users.class);
                    ModelUsers modelChat2 = dataSnapshot1.getValue(ModelUsers.class);
//                    Log.d(TAG, "User name: " + modelChat.getReceiver() + ", email " + modelChat.getSender() +" Message "+modelChat.message);
                    Log.d(TAG, "All User Ids : " + modelChat.email);
//                    Log.d(TAG, "All User Ids : " + modelChat2.email);
                 //   if (modelChat.getSender().equalsIgnoreCase(myuid) && modelChat.getReceiver().equalsIgnoreCase(uid) ||modelChat.getReceiver().equalsIgnoreCase(myuid)&& modelChat.getSender().equalsIgnoreCase(uid)) {
//                    chatList.add(modelChat); // add the chat in chatlist
                       // }
//                    adapterChat = new AdapterChat(ChatActivity2.this, chatList);
//                    adapterChat.notifyDataSetChanged();
//                    recyclerView.setAdapter(adapterChat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                usersList.clear();
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                    Model_User users = dataSnapshot2.getValue(Model_User.class);
//                    Log.d(TAG, "All User Ids : " + users.getEmail());
//                    usersList.add(users);
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
        hashMap.put("timestamp", "173");
        hashMap.put("dilihat", false);
        hashMap.put("type", "text");
        databaseReference.child("Chats").push().setValue(hashMap);







        Log.d(TAG,"All the MSGS   "+message);


        Log.i(TAG,"Hashmap Values  "+hashMap);
        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("ChatList");

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    ref1.child("id").setValue(myuid);
                    Log.d(TAG,"MyuID VALUE IS  "+myuid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("ChatList");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    ref2.child("id").setValue(uid);
                    Log.d(TAG,"MyuID VALUE IS  "+uid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            myuid = user.getUid();
            name.setText("Welcome  "+user.getEmail());
        }

    }
    public void btnlogout(View view) {

        Intent i =new Intent(getApplicationContext(),LoginsActivity.class);
        startActivity(i);
    }
    public void displayname(View view) {

        getAllUsersFromFirebase();

    }
    public void getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("Chats")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren()
                                .iterator();
                        List<ModelChat> users = new ArrayList<>();
                        while (dataSnapshots.hasNext()) {
                            DataSnapshot dataSnapshotChild = dataSnapshots.next();
                            ModelChat user = dataSnapshotChild.getValue(ModelChat.class);
                            if (!TextUtils.equals(user.id,
                                    FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                adapterChat = new AdapterChat(ChatActivity2.this, chatList);
                                adapterChat.notifyDataSetChanged();
                                recyclerView.setAdapter(adapterChat);
                                Intent i =new Intent(getApplicationContext(),Show.class);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(), "hello"+user.message, Toast.LENGTH_SHORT).show();
                                users.add(user);

                            }
                        }
                        // All users are retrieved except the one who is currently logged
                        // in device.
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Unable to retrieve the users.
                    }
                });
    }

//    private void getAllUser() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AuthUsers");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                listuer.clear();
//
//
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    ModelUsers modelUsers = ds.getValue(ModelUsers.class);
//                    if (!modelUsers.getUid().equals(firebaseUser.getUid())) {
//                        listuer.add(modelUsers);
//                    }
//                    adapterUser = new AdapterUser(getApplicationContext(), listuer);
//                    recyclerView2.setAdapter(adapterUser);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
