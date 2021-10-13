package com.example.fcmtoken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
//    RecyclerView recyclerView;
//    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            String loggedInUserName = "";

                loggedInUserName = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.d("Main", "user id: " + loggedInUserName);
            startActivity(new Intent(getApplicationContext(), ChatActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);





//        ArrayList<MessageModel> messagesList = new ArrayList<>();
//        CustomAdapter adapter = new CustomAdapter(this, messagesList);
//        recyclerView = findViewById(R.id.recycler_view);
//        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
//        recyclerView.setAdapter(adapter);
//        manager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(manager);
//        adapter.notifyDataSetChanged();
//        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
//        // recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
//        MessageModel messageModel3=new MessageModel("Party Jokes Startling, But Unnecessary",1);
//        messagesList.add(messageModel3);
//        MessageModel messageModel4=new MessageModel("Party Jokes Startling, But Unnecessary",2);
//        messagesList.add(messageModel4);
//        MessageModel messageModel5=new MessageModel("Party Jokes Startling, But Unnecessary",1);
//        messagesList.add(messageModel5);
//        MessageModel messageModel=new MessageModel("Computers have become ubiquitous in almost every However," + "Help is at hand in the facet of our lives ",1);
//        messagesList.add(messageModel);
//        MessageModel messageModel0=new MessageModel("Computers have become ubiquitous in almost every However," + "Help is at hand in the facet of our lives ",1);
//        messagesList.add(messageModel0);
    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                        }else{
                            //display some message here
                            Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            registerUser();
        }

        if(view == textViewSignin){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}

//public class MainActivity{
//}

//        extends AppCompatActivity {
//    private TextView geeksforgeeks;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        geeksforgeeks = findViewById(R.id.gfg);
//        geeksforgeeks.setText(
//                "GeeksForGeeks(Firebase Authentication)");

//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//           if(!task.isSuccessful())
//           {
//               Log.i(TAG,"Could not Fetch The Fcm Token"+task.getException());
//               return;
//           }
//           String token=task.getResult();
//           String msg =getString(R.string.msg_token_fmt,token);
//           Log.i(TAG,msg);
//            }
//        });
//
//    }

//        Button crashButton = new Button(this);
//        crashButton.setText("Test Crash");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                throw new RuntimeException("Test Crash"); // Force a crash
//            }
//        });
//        addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));



//    }
//}
