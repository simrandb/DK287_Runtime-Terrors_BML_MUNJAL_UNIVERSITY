package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class OpeningScreen extends AppCompatActivity {
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_screen);

        //animateLogo();
        img = findViewById(R.id.imageView);

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        img.startAnimation(animFadeIn);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                change_activity();
            }
        }, 3100);
    }

    private void change_activity() {
        final UserSharedPreferences user=new UserSharedPreferences(getApplicationContext());
        if(user.getUsername()!="")
        {
            Intent i=new Intent(getApplicationContext(),HomeScreen.class);
            i.putExtra("username",user.getUsername());
            startActivity(i);
            finish();
        }

        else
        {
            Intent i=new Intent(getApplicationContext(),Login.class);
            startActivity(i);
            finish();
        }

    }
}
