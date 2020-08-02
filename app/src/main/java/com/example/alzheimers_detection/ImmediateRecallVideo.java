package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

public class ImmediateRecallVideo extends AppCompatActivity {
    VideoView vid;
    int seconds;
    OnSwipeTouchListener onSwipeTouchListener;
    Uri vidUri;
String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.immediate_recall_video);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        final String stage_name="ImmediateRecall";
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.ImmediateRecallVideo),stage_name);
        /*new CountDownTimer(5000,5000){

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                HiddenPanel panel = new HiddenPanel();
                panel.showPopUp(ImmediateRecallVideo.this, stage_name);
            }
        }.start();*/

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        vid = (VideoView) findViewById(R.id.video_view);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video_final;
        //path="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/video_final.mp4?alt=media&token=11374488-f55a-48ec-a56f-fa9f1c8842b1";
        vidUri = Uri.parse(path);

        seconds=1;
        final Handler handler2=new Handler();
        handler2.post(new Runnable() {
            @Override
            public void run() {
                if(seconds>0)
                {
                    seconds=seconds-1;
                    handler2.postDelayed(this,1000);
                }
                else
                {
                    vid.setVideoURI(vidUri);
                    vid.requestFocus();
                    vid.start();

                }
            }
        });


        vid.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp){
                // invoke your activity here

                Intent i = new Intent(getApplicationContext(), ImmediateRecall.class);
                startActivity(i);

            }
        });    }
}