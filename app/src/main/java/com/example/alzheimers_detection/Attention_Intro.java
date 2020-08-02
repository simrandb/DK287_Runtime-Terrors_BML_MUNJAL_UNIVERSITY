

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

public class Attention_Intro extends AppCompatActivity {
Button nextfromattention;
ImageView attentionview;
String urlattentionview;
ProgressBar  progressBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention_intro);
        progressBar2 =findViewById(R.id.progressBar2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        /*attentionview=findViewById(R.id.attentionview);
        urlattentionview="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/stage7.gif?alt=media&token=62df8530-60c8-4583-b2e5-d881b66b7db5";
        //Picasso.with(this).load(urlattentionview).into(attentionview);
        Glide.with(this)
                .load(urlattentionview).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar2.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar2.setVisibility(View.GONE);
                return false;
            }
        }).into(attentionview);
*/
        nextfromattention=findViewById(R.id.nextfromattention);
        nextfromattention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Attention.class);
                startActivity(i);
            }
        });
    }
}