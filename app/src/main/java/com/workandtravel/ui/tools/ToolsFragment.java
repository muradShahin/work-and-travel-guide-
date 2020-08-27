package com.workandtravel.ui.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workandtravel.Classes.Agencies;
import com.workandtravel.Classes.agencyIndex;
import com.workandtravel.R;
import com.workandtravel.Recyclers.RecyclerAdapterAgencies;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<Agencies> agencies=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("agencies");
        recyclerView=root.findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true));
        getAgenices();



        return root;
    }
    public void getAgenices(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                agencies.clear();
                for (DataSnapshot key:dataSnapshot.getChildren()){
                    Agencies agen=new Agencies();
                    agen.setName(key.child("name").getValue().toString());
                    agen.setImg(key.child("img").getValue().toString());
                    agen.setAddress(key.child("adress").getValue().toString());
                    agen.setFacebookPage(key.child("facebookPage").getValue().toString());
                    agen.setPhone(key.child("phone").getValue().toString());
                    agen.setOverview(key.child("overview").getValue().toString());
                    agen.setLoc1(key.child("loc1").getValue().toString());
                    agen.setLoc2(key.child("loc2").getValue().toString());

                    agencies.add(agen);


                }
                RecyclerAdapterAgencies recyclerAdapterAgencies=new RecyclerAdapterAgencies(getActivity(),agencies);
                recyclerView.setAdapter(recyclerAdapterAgencies);
                recyclerView.scrollToPosition(3);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


}