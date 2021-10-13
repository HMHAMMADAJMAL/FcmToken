package com.example.fcmtoken;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.chromium.base.Log;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment {
 RecyclerView recyclerView;

 AdapterUser adapterUser;

 List<ModelUsers> list;
    public BlankFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
recyclerView=view.findViewById(R.id.users_recyceler_view);
recyclerView.setHasFixedSize(true);
recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() ));


list=new ArrayList<>();

getAllUser();

   return  view;
    }

    private void getAllUser() {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Child");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           list.clear();


           for(DataSnapshot ds: snapshot.getChildren())
           {
            ModelUsers modelUsers=ds.getValue(ModelUsers.class);
            if(!modelUsers.getUid().equals(firebaseUser.getUid()))
               {
                   list.add(modelUsers);
               }
            adapterUser=new AdapterUser(getActivity(),list);
            recyclerView.setAdapter(adapterUser);
           }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}