

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

public class ImmediateRecall_Intro extends AppCompatActivity {
Button nextfromimmediaterecall;
ImageView immediaterecallview;
String urlimmediaterecallview;
    ProgressBar progressBar7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.immediate_recall_intro);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        progressBar7 =findViewById(R.id.progressBar7);
        immediaterecallview=findViewById(R.id.immediaterecallview);
        urlimmediaterecallview="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/stage6.gif?alt=media&token=2342ac0a-7d1a-4a2f-842f-79ed3b9366f7";
        //Picasso.with(this).load(urlimmediaterecallview).into(immediaterecallview);
        Glide.with(this)
                .load(urlimmediaterecallview).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar7.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar7.setVisibility(View.GONE);
                return false;
            }
        }).into(immediaterecallview);
        nextfromimmediaterecall=findViewById(R.id.nextfromimmediaterecall);
        nextfromimmediaterecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Immediate_recallPlayVideo.class);
                startActivity(i);
            }
        });
    }
}