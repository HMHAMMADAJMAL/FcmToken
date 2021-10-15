package com.example.fcmtoken;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

class AdapterChat extends RecyclerView.Adapter<AdapterChat.Myholder> {
    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPR_RIGHT = 1;

    Context context;
    List<ModelChat> list;
    FirebaseUser firebaseUser;

    public AdapterChat(Context context, List<ModelChat> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_LEFT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return new Myholder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return new Myholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, @SuppressLint("RecyclerView") final int position) {
        ModelChat message = list.get(position);
        holder.message.setText(message.getMessage());


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (list.get(position).getSender().equals(firebaseUser.getUid())) {
//            Log.d(TAG,"the get uid value is "+firebaseUser.getUid());
//            return MSG_TYPR_RIGHT;
//        } else {
            return MSG_TYPE_LEFT;
//        }

//        return MSG_TYPE_LEFT;

    }

    class Myholder extends RecyclerView.ViewHolder {

        ImageView mimage;
        TextView message;
        LinearLayout msglayput;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.msgc);
            msglayput = itemView.findViewById(R.id.msglayout);

        }
    }
}
