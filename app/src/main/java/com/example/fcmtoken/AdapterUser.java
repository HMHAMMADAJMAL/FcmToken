package com.example.fcmtoken;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
String userEmail=userLists.get(position).getEmail();
holder.mEmailTv.setText(userEmail);
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(context, "my email "+userEmail ,Toast.LENGTH_SHORT).show();
    }
});


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder{

TextView mEmailTv;
    public MyHolder(@NonNull View itemView) {
        super(itemView);

        mEmailTv=itemView.findViewById(R.id.emailTv);
    }
}

}
