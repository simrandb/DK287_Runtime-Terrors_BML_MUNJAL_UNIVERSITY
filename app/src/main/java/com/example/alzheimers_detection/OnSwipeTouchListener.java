package com.example.alzheimers_detection;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class OnSwipeTouchListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;
    Context context;
    String Stage_name;
    OnSwipeTouchListener(Context ctx, View mainView,String sn) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        mainView.setOnTouchListener(this);
        context = ctx;
        Stage_name=sn;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
    public class GestureListener extends
            GestureDetector.SimpleOnGestureListener {
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
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY < 0) {
                        Toast.makeText(context, "Up", Toast.LENGTH_LONG).show();
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

    void onSwipeTop() {
        Toast.makeText(context, "Swiped Up", Toast.LENGTH_SHORT).show();
        HiddenPanel panel = new HiddenPanel();
        panel.showPopUp(context, Stage_name);
        this.onSwipe.swipeTop();
    }        interface onSwipeListener {
        void swipeTop();
    }
    onSwipeListener onSwipe;

}