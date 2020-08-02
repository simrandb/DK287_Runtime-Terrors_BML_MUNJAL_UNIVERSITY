package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class Immediate_recallPlayVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.immediaterecall_playvideo);




        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    public void playvideo(View view) {

        buttonEffect(view);
        Intent intent = new Intent(this, ImmediateRecallVideo.class);
        startActivity(intent);
    }

    public static void buttonEffect(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(1251271480, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                v.getBackground().clearColorFilter();
                                v.invalidate();
                            }
                        }, 100L);
                        break;
                    }
                }
                return false;
            }
        });
    }
}