

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

public class Visuoperception_Intro extends AppCompatActivity {
Button nextfromvisuoperception;
ImageView visuoperceptionview;
String urlvisuoperception;

    ProgressBar progressBar10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visuoperception_intro);

        progressBar10 =findViewById(R.id.progressBar10);
        visuoperceptionview=findViewById(R.id.visuoperceptionview);
        urlvisuoperception="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/stage8.gif?alt=media&token=65ce6aad-29ac-4037-b484-762e62c6b82a";
        //Picasso.with(this).load(urlvisuoperception).into(visuoperceptionview);
        Glide.with(this)
                .load(urlvisuoperception).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar10.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar10.setVisibility(View.GONE);
                        return false;
                    }
                }).into(visuoperceptionview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        nextfromvisuoperception=findViewById(R.id.nextfromvisuoperception);
        nextfromvisuoperception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Visuoperception.class);
                startActivity(i);
            }
        });
    }
}