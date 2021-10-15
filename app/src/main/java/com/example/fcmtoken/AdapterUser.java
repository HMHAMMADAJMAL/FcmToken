package com.example.fcmtoken;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterUser  extends  RecyclerView.Adapter<AdapterUser.MyHolder>  {
Context context;
List<ModelUsers> userLists;

public AdapterUser(Context context ,List<ModelUsers> userLists)
{
    this.context=context;
    this.userLists=userLists;
}
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


    View view = LayoutInflater.from(context).inflate(R.layout.row_users,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
//String userEmail=userLists.get(position).getEmail();
//holder.mEmailTv.setText(userEmail);
//holder.itemView.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        Toast.makeText(context, "my email "+userEmail ,Toast.LENGTH_SHORT).show();
//    }
//});
        ModelUsers message = userLists.get(position);
        holder.mEmailTv.setText(message.getEmail());
        holder.mIdTv.setText(message.getid());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Recycle Click" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ChatActivity2.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

TextView mEmailTv;
TextView mIdTv;
    public MyHolder(@NonNull View itemView) {
        super(itemView);

        mEmailTv=itemView.findViewById(R.id.emailTv);

        mIdTv=itemView.findViewById(R.id.IdTv);


    }
}

}
