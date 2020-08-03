package com.example.alzheimers_detection;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VI_Calculation extends AppCompatActivity {
    Speak sp;
    ArrayList<String[]> answer = new ArrayList<>();
    String[] questions;
    String[] option_for_1;
    String[] option_for_2;
    String[] option_for_3;
    String[] que_no ={"one","two","three"};
    String[] correct_answer ={"left","right","down"};
    String[] Score_points={"zero","one","two","three"};

    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;

    int score;
    String Score;
    int question_number;
    Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vi_calculation);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        res = getResources();
        questions =res.getStringArray(R.array.Calculation_que);
        option_for_1=res.getStringArray(R.array.Calculation_Option1);
        option_for_2=res.getStringArray(R.array.Calculation_Option2);
        option_for_3=res.getStringArray(R.array.Calculation_Option3);



        sp = new Speak(this.getApplicationContext());
        Intent intent = getIntent();

        String que = intent.getStringExtra("Quetion");
        String ans = intent.getStringExtra("Answer");
        String point = intent.getStringExtra("Score");



        if(point==null)
            score=0;
        else
        {
            //String done=point.concat(decimal);
            // Toast.makeText(this, done, Toast.LENGTH_LONG).show();
        }

        answer.add(option_for_1);
        answer.add(option_for_2);
        answer.add(option_for_3);


        if(que==null)
            question_number =0;
        else{
            if(que.contains("one"))
                question_number =0;
            else if(que.contains("two"))
                question_number =1;
            else if(que.contains("three"))                      //GET QUE NUMBER
                question_number =2;
        }


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
                    score=score+1;

                Score= Score_points[(int) score];
                question_number++;
                if(question_number >=que_no.length) {
                    // next activity and total score!
                    speak(getString(R.string.after_Calculation),0,0,0,0,0,1);
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
        dbUsers.child("calculation").setValue(score);

        Intent myIntent=new Intent(getApplicationContext(),VI_Sentence_Repetition_intro.class);
        startActivity(myIntent);
    }

    void call_left()
    {
        View left = findViewById(R.id.left);
        left.setVisibility(View.VISIBLE);
        speak(getString(R.string.Swipeleft).concat(answer.get(question_number)[0]),0,1,0,0,0,0);
    }
    void call_right()
    {
        View left = findViewById(R.id.left);
        final View right = findViewById(R.id.right);
        left.setVisibility(View.GONE);
        right.setVisibility(View.VISIBLE);
        speak(getString(R.string.Swiperight).concat(answer.get(question_number)[1]),0,0,1,0,0,0);


    }
    void call_up()
    {
        final View up = findViewById(R.id.up);
        final View right = findViewById(R.id.right);
        right.setVisibility(View.GONE);
        up.setVisibility(View.VISIBLE);
        speak(getString(R.string.Swipeup),0,0,0,1,0,0);
    }

    void enter_response()
    {
        speak(getString(R.string.Enter_response),0,0,0,0,1,0);
    }
    private void call_question()
    {
        speak(questions[question_number],1,0,0,0,0,0);
    }
    private void get_answer()
    {
        final Intent myIntent = new Intent(this, VI_Calculation_ans.class);
        myIntent.putExtra("Que_no",que_no[question_number]);
        myIntent.putExtra("Score",Score);
        startActivity(myIntent);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void speak(String say, final int q, final int o1, final int o2, final int o3, final int na, final int finish){
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
                            call_right();
                        if(o2==1)
                            call_up();
                        if(o3==1)
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