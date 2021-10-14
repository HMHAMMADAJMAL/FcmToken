package com.example.fcmtoken;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    RecyclerView recyclerViewmodaluser;
    FirebaseUser firebaseUser;
    EditText email2;
    AdapterUser adapterUser;
    String uid;
    String uidofauth;
    DatabaseReference reference;
    List<ModelUsers> list;
    TextView showinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.btnregister);
        progressbar = findViewById(R.id.progressbar);



//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setStackFromEnd(true);
//        recyclerViewmodaluser = findViewById(R.id.recycler_view_modal_users);
//        recyclerViewmodaluser.setHasFixedSize(true);
//        recyclerViewmodaluser.setLayoutManager(linearLayoutManager);
        email2=findViewById(R.id.email);
        list=new ArrayList<>();
        readMessagess();




        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();

            }
        });

    }

    private void registerNewUser() {
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        uid = getIntent().getStringExtra("uid");
                        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Log.d(TAG, "Uid of Curent user  "+currentuser);
                        String mail2 = email2.getText().toString().trim();
                        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
                        HashMap<String, Object> hashMap2 = new HashMap<>();
                        hashMap2.put("id", currentuser);
                        hashMap2.put("email", mail2);
                        Log.d(TAG,"All the MSGS   "+mail2);

                        databaseReference2.child("AuthProfile").push().setValue(hashMap2);
                        Log.d(TAG,"All the MSGS   "+mail2);
                        databaseReference2.child("id").setValue(uid);
                        Log.d(TAG,"All the MSGS   "+mail2);
                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String name1 = dataSnapshot.getKey();
                                Log.d(TAG,"DATASNAPSHOT KEY    "+name1);
//                                list.clear();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    ModelUsers newPost = ds.getValue(ModelUsers.class);
//                                    list.add(newPost);
//                                    adapterUser = new AdapterUser(RegistrationActivity.this, list);
//                                    adapterUser.notifyDataSetChanged();
//                                    recyclerViewmodaluser.setAdapter(adapterUser);
//                                    Log.d(TAG, "Get Email Values  "+newPost.getEmail());
//                                    Log.d(TAG, "Email  by get EMAIL: "+newPost.getEmail());
//                                    list.add(newPost);

                                    Log.d(TAG, "GeT ALL THE Record of Users  : " + dataSnapshot.getValue());



                                }
//                                adapterUser=new AdapterUser(getApplicationContext(),list);
//                                recyclerViewmodaluser.setAdapter(adapterUser);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        };
                        databaseReference2.addListenerForSingleValueEvent(eventListener);
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "List of Available Users " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegistrationActivity.this, Home.class);

                            startActivity(intent);
//                            Intent myIntent = new Intent(RegistrationActivity.this, Show.class);
//                            myIntent.putExtra("firstName", );
//                            myIntent.putExtra("lastName", "Your Last Name Here");
//                            startActivity(myIntent);


                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    private void readMessagess() {
        list=new ArrayList<>();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("AuthProfile");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    ModelUsers modelChat = dataSnapshot1.getValue(ModelUsers.class);
//                    list.add(modelChat);
//                    adapterUser = new AdapterUser(RegistrationActivity.this, list);
//                    adapterUser.notifyDataSetChanged();
//                    recyclerViewmodaluser.setAdapter(adapterUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbref.addValueEventListener(new ValueEventListener() {
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
}