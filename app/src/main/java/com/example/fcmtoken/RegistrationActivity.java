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
    String id;
    String uidofauth;

    DatabaseReference reference;
    List<ModelUsers> list;
    TextView showinfo;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.btnregister);
        progressbar = findViewById(R.id.progressbar);

        email2=findViewById(R.id.email);
        list=new ArrayList<>();
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

                        String mail2 = email2.getText().toString().trim();
                        user =FirebaseAuth.getInstance().getCurrentUser();
                        id=user.getUid();
                        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
                        HashMap<String, Object> hashMap2 = new HashMap<>();
                        hashMap2.put("email", mail2);
                        hashMap2.put("id", id);
                        databaseReference2.child("AuthProfile").push().setValue(hashMap2);
                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String name1 = dataSnapshot.getKey();
                                Log.d(TAG,"DATASNAPSHOT KEY    "+name1);
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Log.d(TAG, "GeT ALL THE Record of Users  : " + dataSnapshot.getValue());
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        };
                        databaseReference2.addListenerForSingleValueEvent(eventListener);
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegistrationActivity.this, Home.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void btnlogin(View view) {
        Intent i =new Intent(RegistrationActivity.this,LoginsActivity.class);
        startActivity(i);
    }
}