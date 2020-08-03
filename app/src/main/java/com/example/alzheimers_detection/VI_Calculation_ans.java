package com.example.alzheimers_detection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class VI_Calculation_ans extends AppCompatActivity {

    OnSwipeTouchListener onSwipeTouchListener;
    private static Context mContext;
    static boolean got;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vi_calculation_ans);

        got=false;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        mContext = this;

        Intent intent = getIntent();

        final String que = intent.getStringExtra("Que_no");
        final String score = intent.getStringExtra("Score");
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.swipe_cal),que,score);
       /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(got==false)
                            change_activity("up",que,score);

                    }
                }, 60000);

            }
        }, 60000);*/
    }
    static void change_activity(String out, String que,String score)
    {
        got=true;
        Intent myIntent = new Intent( mContext, VI_Calculation.class);
        myIntent.putExtra("Quetion", que);
        myIntent.putExtra("Answer", out);
        myIntent.putExtra("Score", score);
        mContext.startActivity(myIntent);
    }

    public static class OnSwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;
        Context context;
        String que="";
        String score="";

        OnSwipeTouchListener(Context ctx, View mainView, String ques, String scores) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
            mainView.setOnTouchListener(this);
            context = ctx;
            que=ques;
            score=scores;

        }
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            return gestureDetector.onTouchEvent(event);
        }

        public class GestureListener extends  GestureDetector.SimpleOnGestureListener {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                change_activity("right", que,score);
                                onSwipeRight();

                            } else {
                                change_activity("left", que,score);
                                onSwipeLeft();

                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            //change_activity("down", que,score);
                            //onSwipeBottom();
                        } else {
                            change_activity("up", que,score);
                            onSwipeTop();
                        }
                        result = true;
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }

        }

        void onSwipeRight() {
            this.onSwipe.swipeRight();
        }
        void onSwipeLeft() {
            this.onSwipe.swipeLeft();
        }
        void onSwipeTop() {
            this.onSwipe.swipeTop();
        }
        interface onSwipeListener {
            void swipeRight();
            void swipeTop();
            void swipeLeft();
        }
        VI_Abstraction_ans.OnSwipeTouchListener.onSwipeListener onSwipe;
    }

}