package com.example.fcmtoken;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Home extends AppCompatActivity {
    TextView text;
    RecyclerView myrecyclerview;
    List<ModelUsers> modeluserlist;
    AdapterUser modaladapteruser;
    FirebaseAuth firebaseAuth;
    String id, myuid, image;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;
    DatabaseReference databaseReference;
    EditText msgemail;
    ImageButton sendmsgbtn;
    boolean notify = false;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        msgemail = findViewById(R.id.emailmsg);
        sendmsgbtn = findViewById(R.id.sendemailmessagebtn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        myrecyclerview = findViewById(R.id.recycler_view);
        myrecyclerview.setHasFixedSize(true);
        myrecyclerview.setLayoutManager(linearLayoutManager);
        readMessages();
    }
    private void readMessages() {
        modeluserlist = new ArrayList<>();
        user =FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("AuthProfile");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modeluserlist.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelUsers modelChat = dataSnapshot1.getValue(ModelUsers.class);
                    if (!modelChat.getid().equals(user.getUid()))
                      {
                        modeluserlist.add(modelChat);
                      }
                }

                Log.d(TAG, "Current user email No  is " + modeluserlist.size());
                modaladapteruser = new AdapterUser(Home.this, modeluserlist);
                myrecyclerview.setLayoutManager(new LinearLayoutManager(Home.this));
                myrecyclerview.setAdapter(modaladapteruser);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

