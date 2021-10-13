package com.example.fcmtoken;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.Iterator;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    ListView listViews;
    List<String> users = new ArrayList<String>();
    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    EditText email2;
    AdapterUser adapterUser;
    String uid;

     String uidofauth;
    DatabaseReference reference;

    List<ModelUsers> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.btnregister);
        progressbar = findViewById(R.id.progressbar);
        listViews = findViewById(R.id.listview);
        recyclerView = findViewById(R.id.users_recyceler_view);
        email2=findViewById(R.id.email);




        list = new ArrayList<>();

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
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        uid = getIntent().getStringExtra("uid");
                        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Log.d(TAG, "Uid of Cureent user  "+currentuser);
//                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//                        DatabaseReference usersdRef = rootRef.child("Chats");
//                        FirebaseDatabase.getInstance().getReference().child("AuthUsers").child(firebaseUser.getUid())
//                                .child("email").setValue(email);

//                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                        reference = FirebaseDatabase.getInstance().getReference("AuthUsers").child(firebaseUser.getUid());
                        String mail2 = email2.getText().toString().trim();
                        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
                        HashMap<String, Object> hashMap2 = new HashMap<>();
                        hashMap2.put("id", currentuser);
                        hashMap2.put("Email", mail2);
                        databaseReference2.child("AuthProfile").push().setValue(hashMap2);
                        databaseReference2.child("id").setValue(uid);

                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                list.clear();
                                String name1 = dataSnapshot.getKey();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    ModelUsers newPost = ds.getValue(ModelUsers.class);

                                    emailTextView.setText(newPost.getEmail());
                                    Log.d(TAG, "Email by email : "+newPost.email);

                                    Log.d(TAG, "Email  by get EMAIL: "+newPost.getEmail());
                                    list.add(newPost);

                                    adapterUser=new AdapterUser(getApplicationContext(),list);
                                    recyclerView.setAdapter(adapterUser);

                                    String name = ds.child("email").getValue(String.class);
//                                    String names=ds.child("email").getValue().toString();
//                                    ModelUsers name2 = ds.child("email").getValue(ModelUsers.class);
////                                    users.add(name);
//
//
//                                    Log.d(TAG, "List of Emails  : "+ nameh);
//
                                    Log.d(TAG, "Email of Person: "+FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                    Log.d(TAG, "Email List Name: "+name);
//                                    Log.i(TAG, "Check Names : "+names);
//                                    Log.d(TAG, "Email List Name: "+name2);
                                    ;   Log.d(TAG, "the key is : " + ds.getKey());
                                    ds.getKey().equals("Q4dyGnxmf3S1f6fQEhZxJho6OB72");

                                        Log.d(TAG, "the key is : " + ds.getValue());

                                    Log.d(TAG, "the key is : " + ds.getKey().equals("Q4dyGnxmf3S1f6fQEhZxJho6OB72"));
                                    Log.d(TAG, "the key is : " + ds.getRef());
                                    Log.d(TAG, "the key is : " + ds.getChildren());



                                    Log.d(TAG," Email List of PersonS ID  "+ ds.getKey().equals("0jvnr3AYnjO93SrzdAzE45tLqE92"));


//                                    Log.d(TAG, "List of Emails  : "+ nameh);
//                                    Log.d(TAG, "Key of Parent Child Values  : "+ dataSnapshot.child("AuthUsers").child("email").getValue());
//
//                                    ds.getKey().equals("email");
//                                        String orderNumber = ds.getValue().toString();
//                                        Log.d(TAG,"Specific Node Value"+ orderNumber);
//                                    ModelUsers modelUsers = ds.getValue(ModelUsers.class);
//                                    if (!modelUsers.getUid().equals(firebaseUser.getUid())) {
//                                        list.add(modelUsers);
//                                    }
//                                    recyclerView.setHasFixedSize(true);
//                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                                    adapterUser = new AdapterUser(getApplicationContext(), list);
//                                    recyclerView.setAdapter(adapterUser);
                                    ModelUsers user = dataSnapshot.getValue(ModelUsers.class);
                                    emailTextView.setText(user.getEmail());
                                    Log.d(TAG, "Email TEXT vIEW   : "+ user.email);



                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        };
                        databaseReference2.addListenerForSingleValueEvent(eventListener);
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "List of Available Users " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegistrationActivity.this, ChatActivity2.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}