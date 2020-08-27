package com.workandtravel.ui.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.workandtravel.R;

public class welcome extends AppCompatActivity {
    ImageView loading;
Animation a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        loading=findViewById(R.id.load);
        a= AnimationUtils.loadAnimation(this,R.anim.roate_center);
        loading.setAnimation(a);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(welcome.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}
