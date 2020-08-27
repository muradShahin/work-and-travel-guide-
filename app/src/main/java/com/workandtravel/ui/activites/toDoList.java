package com.workandtravel.ui.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.workandtravel.Classes.DoList;
import com.workandtravel.Recyclers.RecyclerList;
import com.workandtravel.R;

import java.util.ArrayList;

import io.paperdb.Paper;

public class toDoList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<DoList> lists=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        recyclerView=findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        prepareArray();
    }

    public void prepareArray(){
        Paper.init(this);
        DoList list=new DoList();
        list.setTitle("Passport");
        list.setText1("Copy to carry yourself:\t3");
        list.setText2("Copy your parents must have:\t1");

        lists.add(list);

        list=new DoList();
        list.setTitle("J1-Visa");
        list.setText1("Copy to carry yourself:\t3");
        list.setText2("Copy your parents must have:\t1");


        lists.add(list);

        list=new DoList();
        list.setTitle("DS");
        list.setText1("Copy to carry yourself:\t3");
        list.setText2("Copy your parents must have:\t1");

        lists.add(list);

        list=new DoList();
        list.setTitle("Job Offer");
        list.setText1("Copy to carry yourself:\t2");
        list.setText2("Copy your parents must have:\t1");
        lists.add(list);

        list=new DoList();
        list.setTitle("Airplane Ticket");
        list.setText1("Copy to carry yourself:\t2");
        list.setText2("Copy your parents must have:\t1");
        lists.add(list);

        list=new DoList();
        list.setTitle("Insurance policy");
        list.setText1("Copy to carry yourself:\t2");
        list.setText2("Copy your parents must have:\t1");
        lists.add(list);

        list=new DoList();
        list.setTitle("Driver'sd license / if you have one");
        list.setText1("Copy to carry yourself:\t2");
        list.setText2("Copy your parents must have:\t1");
        lists.add(list);


        RecyclerList recyclerList=new RecyclerList(this,lists);
        recyclerView.setAdapter(recyclerList);

    }
}
