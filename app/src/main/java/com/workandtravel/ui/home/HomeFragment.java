package com.workandtravel.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.workandtravel.ui.activites.arabicExplain;
import com.workandtravel.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView goArabic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        goArabic=root.findViewById(R.id.arabicEx);
        goArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), arabicExplain.class);
                startActivity(i);
            }
        });
        return root;
    }
}