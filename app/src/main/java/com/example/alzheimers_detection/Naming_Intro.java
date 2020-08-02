


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

public class Naming_Intro extends AppCompatActivity {
Button nextfromnaming;
ImageView namingview;
String urlnamingview;
    ProgressBar progressBar8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naming_intro);
        progressBar8 =findViewById(R.id.progressBar8);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        namingview=findViewById(R.id.namingview);
        urlnamingview="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/stage2.gif?alt=media&token=af9f32f6-0a27-47bd-8911-2d4785297f95";
        //Picasso.with(this).load(urlnamingview).into(namingview);
        Glide.with(this)
                .load(urlnamingview).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar8.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar8.setVisibility(View.GONE);
                return false;
            }
        }).into(namingview);
        nextfromnaming=findViewById(R.id.nextfromnaming);

        nextfromnaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Naming4.class);
                startActivity(i);
            }
        });
    }
}