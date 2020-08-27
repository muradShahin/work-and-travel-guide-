package com.workandtravel.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workandtravel.Classes.Posts;
import com.workandtravel.R;
import com.workandtravel.Recyclers.RecyclerAdapterPosts;
import com.workandtravel.ui.activites.addPost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Posts> ar=new ArrayList<>();
    FloatingActionButton add;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("posts");
        add=root.findViewById(R.id.location);
        recyclerView=root.findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            getPosts();

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(), addPost.class);
                    startActivity(i);

                }
            });

        return root;
    }

    public void getPosts(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ar.clear();
                for (DataSnapshot keyNode:dataSnapshot.getChildren()){
                    Posts p=new Posts();
                    p.setName(keyNode.child("name").getValue().toString());
                    p.setImage(keyNode.child("image").getValue().toString());
                    p.setContent(keyNode.child("content").getValue().toString());
                    p.setState(keyNode.child("state").getValue().toString());
                    p.setCountry("From :\t"+keyNode.child("country").getValue().toString());
                    ar.add(p);

                }
                RecyclerAdapterPosts recyclerAdapterPosts=new RecyclerAdapterPosts(getActivity(),ar);
                recyclerView.setAdapter(recyclerAdapterPosts);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}