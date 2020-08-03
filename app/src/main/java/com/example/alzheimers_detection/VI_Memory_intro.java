package com.example.alzheimers_detection;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VI_Memory_intro extends AppCompatActivity {
    Speak sp;
    private static Boolean done3 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vi_memory_intro);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        Button button= findViewById(R.id.button);

        sp = new Speak(this.getApplicationContext());
        speak(getString(R.string.VI_Memory_intro),1);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(done3)
                {
                    Intent myIntent=new Intent(getApplicationContext(),VI_Memory.class);
                    startActivity(myIntent);
                    VI_Memory_intro.this.finish();
                }

            }

        });
    }

    // private void speak(String[] text) {}

    void play(){
        MediaPlayer mediaPlayer = MediaPlayer.create(VI_Memory_intro.this, R.raw.story);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                speak(getString(R.string.tap_procede),2);
            }
        }, 42000);    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

    void speak(String say, final int flag){
        //call speakOut function
        sp.speakOut(say); //need minimum api level 21

        //to slow down the speed
        sp.changeSpeed(1.0f);

        //set Progresslistener to keep track of tts
        sp.getListener().setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "Started" + keyword, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onDone(String s) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "Done ", Toast.LENGTH_SHORT).show();
                        if(flag==1) {
                            play();
                        }
                        if(flag==2)
                        {
                            done3 = true; //speaker has finished speaking
                        }


                    }
                });
            }

            @Override
            public void onError(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}