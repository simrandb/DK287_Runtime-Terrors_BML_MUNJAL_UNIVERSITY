package com.example.alzheimers_detection;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VI_Delayed_Recall extends AppCompatActivity {
    Speak sp;
    ArrayList<String[]> answer = new ArrayList<>();
    String[] questions;
    String[] option_for_1;
    String[] option_for_2;
    String[] option_for_3;
    String[] option_for_4;
    String[] option_for_5;
    String[] option_for_6;

    int seconds=1;
    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;
    int num;
    String[] que_no ={"one","two","three","four","five","six"};
    String[] correct_answer ={"left","right","down","left","down","down"};
    String[] Score_points={"zero","one","two","three"};

    double score;
    String Score;
    String Decimal;
    int question_number;
    Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vi_delayed_recall);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        res = getResources();
        questions =res.getStringArray(R.array.DRQuetion);
        option_for_1=res.getStringArray(R.array.DR_Option1);
        option_for_2=res.getStringArray(R.array.DR_Option2);
        option_for_3=res.getStringArray(R.array.DR_Option3);
        option_for_4=res.getStringArray(R.array.DR_Option4);
        option_for_5=res.getStringArray(R.array.DR_Option5);
        option_for_6=res.getStringArray(R.array.DR_Option6);


        sp = new Speak(this.getApplicationContext());
        Intent intent = getIntent();

        String que = intent.getStringExtra("Quetion");
        String ans = intent.getStringExtra("Answer");
        String point = intent.getStringExtra("Score");
        String decimal = intent.getStringExtra("Decimal");



        if(decimal==null&&point==null)
            score=0.0;
        else
        {
            //String done=point.concat(decimal);
            // Toast.makeText(this, done, Toast.LENGTH_LONG).show();
        }

        answer.add(option_for_1);
        answer.add(option_for_2);
        answer.add(option_for_3);
        answer.add(option_for_4);
        answer.add(option_for_5);
        answer.add(option_for_6);

        if(que==null)
            question_number =0;
        else{
            if(que.contains("one"))
                question_number =0;
            else if(que.contains("two"))
                question_number =1;
            else if(que.contains("three"))                      //GET QUE NUMBER
                question_number =2;
            else if(que.contains("four"))
                question_number =3;
            else if(que.contains("five"))
                question_number =4;
            else if(que.contains("six"))
                question_number =5;}


        if(point==null)
            score=0;
        else
        {
            if(point.contains("zero"))
                score=0;
            else if(point.contains("one"))
                score=1;
            else if(point.contains("two"))               //GET tens value of score
                score=2;
            else if(point.contains("three"))
                score=3;
        }


        if(decimal==null)
            score=score+0;
        else
        {
            if(decimal.contains("zero"))                     //GET decimal value of score
                score=score+0;
            else if(decimal.contains("five"))
                score=score+0.5;

        }


        if(que==null&&ans==null)
        {
            call_question();              //for first time

        }

        if(ans==null)
        {
            //Toast.makeText(this, "NULL ans", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(ans.contains("up"))
            {
                call_question();
            }

            if(!ans.contains("up"))
            {

                if(ans.contains(correct_answer[question_number]))
                    score=score+0.5;

                int d= (int) ((score*10)%10);
                if(d==0)
                    Decimal="zero";
                else
                    Decimal="five";

                Score= Score_points[(int) score];
                question_number++;
                if(question_number >=que_no.length) {
                    // next activity and total score!
                    speak(getString(R.string.after_Delayed_Recall),0,0,0,0,0,0,1);

                }
                if(question_number<que_no.length)
                    call_question();
            }

        }
    }

    private void change_activity() {
        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();
        dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
        dbUsers.child("delayedRecall").setValue(score);

        DatabaseReference userDBRef = FirebaseDatabase.getInstance().getReference("Users");
        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();
        userDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(uid).getValue(User.class);
                num=user.getNumOfScores();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occured");
            }


        });


        seconds=2;
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if(seconds>0)
                {
                    seconds=seconds-1;
                    handler.postDelayed(this,1000);
                }
                else
                {

                    num++;
                    dbUsers.child("numOfScores").setValue(num);

                    Intent myIntent=new Intent(getApplicationContext(), VI_Orientation_intro.class);
                    startActivity(myIntent);
                }
            }
        });

    }

    void call_left()
    {
        View left = findViewById(R.id.left);
        left.setVisibility(View.VISIBLE);
        speak(getString(R.string.Swipeleft).concat(answer.get(question_number)[0]),0,1,0,0,0,0,0);
    }
    void call_right()
    {
        final View down = findViewById(R.id.down);
        final View right = findViewById(R.id.right);
        down.setVisibility(View.GONE);
        right.setVisibility(View.VISIBLE);
        speak(getString(R.string.Swiperight).concat(answer.get(question_number)[2]),0,0,0,1,0,0,0);


    }
    void call_up()
    {
        final View up = findViewById(R.id.up);
        final View right = findViewById(R.id.right);
        right.setVisibility(View.GONE);
        up.setVisibility(View.VISIBLE);
        speak(getString(R.string.Swipeup),0,0,0,0,1,0,0);
    }
    void call_down()
    {
        final View down = findViewById(R.id.down);
        final View left = findViewById(R.id.left);
        left.setVisibility(View.GONE);
        down.setVisibility(View.VISIBLE);
        speak(getString(R.string.Swipedown).concat(answer.get(question_number)[1]),0,0,1,0,0,0,0);
    }

    void enter_response()
    {
        speak(getString(R.string.Enter_response),0,0,0,0,0,1,0);
    }
    private void call_question()
    {
        speak(questions[question_number],1,0,0,0,0,0,0);
    }
    private void get_answer()
    {
        final Intent myIntent = new Intent(this, VI_Delayed_Recall_ans.class);
        myIntent.putExtra("Que_no",que_no[question_number]);
        myIntent.putExtra("Score",Score);
        myIntent.putExtra("Decimal",Decimal);
        startActivity(myIntent);
    }
    void speak(String say, final int q,final int o1,final int o2,final int o3,final int o4,final int na ,final int finish){
        //call speakOut function
        sp.speakOut(say); //need minimum api level 21

        //to slow down the speed
        sp.changeSpeed(0.9f);

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
                        if(q==1)
                            call_left();
                        if(o1==1)
                            call_down();
                        if(o2==1)
                            call_right();
                        if(o3==1)
                            call_up();
                        if(o4==1)
                            enter_response();
                        if(na==1)
                            get_answer();
                        else if(finish==1) change_activity();
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