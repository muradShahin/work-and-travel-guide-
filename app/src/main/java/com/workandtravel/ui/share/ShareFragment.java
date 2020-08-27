package com.workandtravel.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workandtravel.Classes.Embassy;
import com.workandtravel.R;
import com.workandtravel.Recyclers.RecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShareFragment extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ShareViewModel shareViewModel;
    ArrayList<Embassy> embassies;
    ArrayList<String>keys;
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        recyclerView=root.findViewById(R.id.rec);

        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                firebaseDatabase=FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference("embassies");
                embassies=new ArrayList<>();
                keys=new ArrayList<>();
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
               getEmbassy();


            }
        });
        return root;
    }
    public void getEmbassy(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot key:dataSnapshot.getChildren()){
                    keys.add(key.getKey());
                    Embassy embassy=new Embassy();
                    embassy.setName(key.child("name").getValue().toString());
                    embassy.setImg(key.child("image").getValue().toString());
                    embassy.setAddress(key.child("address").getValue().toString());
                    embassy.setEmail(key.child("email").getValue().toString());
                    embassy.setPhone(key.child("phone").getValue().toString());
                    embassy.setFax(key.child("fax").getValue().toString());
                    embassy.setWebsite(key.child("website").getValue().toString());
                    embassy.setLoc1(key.child("loc1").getValue().toString());
                    embassy.setLoc2(key.child("loc2").getValue().toString());
                    embassies.add(embassy);


                }
               RecyclerAdapter recyclerAdapter=new RecyclerAdapter(getActivity(),embassies);
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}