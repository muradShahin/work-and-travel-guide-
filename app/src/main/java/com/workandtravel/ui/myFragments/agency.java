package com.workandtravel.ui.myFragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.workandtravel.Classes.CurrentAgency;
import com.workandtravel.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.workandtravel.ui.activites.MapsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class agency extends Fragment {
    private static final int REQUEST_CALL = 1;
    ImageView imageView;
    TextView name,overview,address;
    Button call,face;
    FloatingActionButton loc;


    public agency() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_agency, container, false);
        imageView=v.findViewById(R.id.img);
        name=v.findViewById(R.id.nameAgen);
        overview=v.findViewById(R.id.overview);
        call=v.findViewById(R.id.call);
        face=v.findViewById(R.id.face);
        loc=v.findViewById(R.id.location);
        address=v.findViewById(R.id.addres);
        init();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = CurrentAgency.facebookPage;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), MapsActivity.class);
                i.putExtra("loc1", CurrentAgency.loc1);
                i.putExtra("loc2",CurrentAgency.loc2);
                i.putExtra("name",CurrentAgency.name);
                startActivity(i);
            }
        });


        return v;

    }
    private void init() {
        Glide.with(getActivity()).load(CurrentAgency.img).into(imageView);
        name.setText(CurrentAgency.name);
        overview.setText(CurrentAgency.overview);
        address.setText(CurrentAgency.address);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                call();

            }else{
                Toast.makeText(getActivity(), "permission not grantedm", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void call() {

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }else{
            String dia="tel:"+CurrentAgency.phone;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dia)));

        }

    }


}
