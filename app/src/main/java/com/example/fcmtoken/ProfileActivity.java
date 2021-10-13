package com.example.fcmtoken;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button buttonLogout;
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
//        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
//        textViewUserEmail.setText("Welcome  "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);


        ArrayList<MessageModel> messagesList = new ArrayList<>();
        CustomAdapter adapter = new CustomAdapter(this, messagesList);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
        recyclerView.setAdapter(adapter);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
        // recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
        MessageModel messageModel3=new MessageModel("Party Jokes Startling, But Unnecessary",1);
        messagesList.add(messageModel3);
        MessageModel messageModel4=new MessageModel("Party Jokes Startling, But Unnecessary",2);
        messagesList.add(messageModel4);
        MessageModel messageModel5=new MessageModel("Party Jokes Startling, But Unnecessary",1);
        messagesList.add(messageModel5);
        MessageModel messageModel=new MessageModel("Computers have become ubiquitous in almost every However," + "Help is at hand in the facet of our lives ",1);
        messagesList.add(messageModel);
        MessageModel messageModel0=new MessageModel("Computers have become ubiquitous in almost every However," + "Help is at hand in the facet of our lives ",1);
        messagesList.add(messageModel0);


    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}