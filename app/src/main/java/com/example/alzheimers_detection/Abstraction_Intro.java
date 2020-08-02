


//added images to firebase storage
package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class Abstraction_Intro extends AppCompatActivity {
Button nextfromabstraction;
ImageView abstractionview;
String urlabstractionview;
    ProgressBar progressBar3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abstraction_intro);
        progressBar3 =findViewById(R.id.progressBar3);
        abstractionview=findViewById(R.id.abstractionview);
        urlabstractionview="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/stage3.gif?alt=media&token=e4b58baf-c5c9-434a-b483-1667fa9d0555";
        Glide.with(this)
                .load(urlabstractionview).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar3.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar3.setVisibility(View.GONE);
                return false;
            }
        }).into(abstractionview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        nextfromabstraction=findViewById(R.id.nextfromabstraction);
        nextfromabstraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Abstraction.class);
                startActivity(i);
            }
        });

    }
}