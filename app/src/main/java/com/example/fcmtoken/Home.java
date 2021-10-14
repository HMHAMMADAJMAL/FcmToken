package com.example.fcmtoken;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Home extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        text=findViewById(R.id.showinfo);

                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("AuthProfile");
                            ValueEventListener eventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        text.setText("Welcome  " + dataSnapshot.getValue());
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            };
                            databaseReference2.addListenerForSingleValueEvent(eventListener);
        }
    }
