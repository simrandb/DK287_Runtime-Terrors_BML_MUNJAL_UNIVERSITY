

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

public class Calculation_Intro extends AppCompatActivity {
Button nextfromcalculation;
ImageView calculationview;
String urlcalculationview;
ProgressBar progressBar4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation_intro);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        progressBar4 =findViewById(R.id.progressBar4);
        calculationview=findViewById(R.id.calculationview);
        urlcalculationview="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/stage4.gif?alt=media&token=43156ab1-a515-4dab-9c18-b2f3d8e73f14";
        //Picasso.with(this).load(urlcalculationview).into(calculationview);
        Glide.with(this)
                .load(urlcalculationview).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar4.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model,
                                           Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar4.setVisibility(View.GONE);
                return false;
            }
        }).into(calculationview);
        nextfromcalculation=findViewById(R.id.nextfromcalculation);
        nextfromcalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Calculation.class);
                startActivity(i);
            }
        });
    }
}