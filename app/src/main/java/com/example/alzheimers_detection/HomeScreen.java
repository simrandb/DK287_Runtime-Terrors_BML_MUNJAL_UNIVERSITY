package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {
    Button playgame,instructions,settings,news,nearbydoctors,chatwithus,results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        playgame=findViewById(R.id.playgame);
        settings=findViewById(R.id.settings);
        news=findViewById(R.id.news);
        nearbydoctors=findViewById(R.id.nearbydoctors);
        instructions=findViewById(R.id.instructions);
        chatwithus=findViewById(R.id.chatwithus);
        results=findViewById(R.id.results);

        playgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Option_Screen.class);
                startActivity(i);
            }
        });

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Results.class);
                startActivity(i);
            }
        });

        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Put "instructions" activity in the below Intent and uncomment it
                Intent i=new Intent(getApplicationContext(),Instructions.class);
                startActivity(i);

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Settings.class);
                startActivity(i);

            }
        });

        chatwithus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Chatbot.class);
                startActivity(i);

            }
        });

        nearbydoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),NearbyDoctors.class);
                startActivity(i);

            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MyCommunities.class);
                startActivity(i);
            }
        });
    }
}