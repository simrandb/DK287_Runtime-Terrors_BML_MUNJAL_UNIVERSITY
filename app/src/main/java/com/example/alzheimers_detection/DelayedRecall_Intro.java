

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

public class DelayedRecall_Intro extends AppCompatActivity {
Button nextfromdelayedrecall;
ImageView unclegif,caterpillargif;
String urluncle,urlcaterpillar;
ProgressBar progressBar5;
    ProgressBar progressBar6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delayed_recall_intro);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        progressBar5 =findViewById(R.id.progressBar5);
        progressBar6 =findViewById(R.id.progressBar6);
        unclegif=findViewById(R.id.unclegif);
        caterpillargif=findViewById(R.id.caterpillargif);

        urlcaterpillar="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/caterpillar_min.png?alt=media&token=89ea34fa-322f-404f-b4c0-e402621ae643";
        urluncle="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/uncle.gif?alt=media&token=d71b0ea5-619a-4159-8efc-89b0bfd16ab1";

        //Picasso.with(this).load(urlcaterpillar).into(caterpillargif);
        //Picasso.with(this).load(urluncle).into(unclegif);
        Glide.with(this)
                .load(urlcaterpillar).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar6.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar6.setVisibility(View.GONE);
                return false;
            }
        }).into(caterpillargif);

        Glide.with(this)
                .load(urluncle).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar5.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar5.setVisibility(View.GONE);
                return false;
            }
        }).into(unclegif);
        nextfromdelayedrecall=findViewById(R.id.nextfromdelayedrecall);

        nextfromdelayedrecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),DelayedRecall.class);
                startActivity(i);
            }
        });
    }
}