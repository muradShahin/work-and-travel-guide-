package com.workandtravel.ui.myFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workandtravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class about extends Fragment {
    TextView prPolicy;


    public about() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_about, container, false);
    prPolicy=v.findViewById(R.id.policy);

    prPolicy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://app.termly.io/document/privacy-policy/29b2868d-47d0-4d76-89c2-7a04ad360131"));
            startActivity(browserIntent);
        }
    });


    return v;
    }

}
