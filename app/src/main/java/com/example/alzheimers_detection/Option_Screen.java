package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class Option_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    public void Start_Vp(View view)
    {
        Intent i=new Intent(getApplicationContext(),Roadmap.class);
        startActivity(i);
    }

    public void Start_Vi(View view)
    {
        Intent i=new Intent(getApplicationContext(),VI_Start.class);
        startActivity(i);
    }
}