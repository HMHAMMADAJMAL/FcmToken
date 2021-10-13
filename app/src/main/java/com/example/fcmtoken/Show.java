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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Show extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView names;
    private Button buttonLogout;
    RecyclerView recyclerView;
    FirebaseUser user;
    Context context;

    List<Users> Using;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

//getAllUsersFromFirebase();
    }
//
//
//    public void getAllUsersFromFirebase() {
//        FirebaseDatabase.getInstance()
//                .getReference()
//                .child("Chats")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren()
//                                .iterator();
//                        List<ModelChat> users = new ArrayList<>();
//                        while (dataSnapshots.hasNext()) {
//                            DataSnapshot dataSnapshotChild = dataSnapshots.next();
//                            ModelChat user = dataSnapshotChild.getValue(ModelChat.class);
//                            if (!TextUtils.equals(user.id,
//                                    FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//
//                                Toast.makeText(getApplicationContext(), "hello" + user.message, Toast.LENGTH_SHORT).show();
//                                Log.d(TAG,"all user msgs "+user.message);
//                                users.add(user);
//
//
//                            }
//                        }
//                        // All users are retrieved except the one who is currently logged
//                        // in device.
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Unable to retrieve the users.
//                    }
//                });
//    }
}

