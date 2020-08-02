

//added images to firebase storage
package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ImmediateRecall extends AppCompatActivity {
    private QuestionLibrary myQuestionLibrary = new QuestionLibrary();
    ImageView next;
    ImageView caterpillar;
    int flag=1;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    int counter=1;
    private String mAnswer,urlcaterpillar,urlnext;
    private int mQuestion_number = 0;
    private float mScore = 0;

    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;
    String description;
    OnSwipeTouchListener onSwipeTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.immediate_recall);
        description = "\nAnswer the next two questions based on the previous video."+
                "\n\n\nNote that you cannot go back to any of the questions that you have already answered.";
        final String stage_name="ImmediateRecall";
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.ImmediateRecall),stage_name);
        Intent intent = getIntent();
        final String Play = intent.getStringExtra("Play");


        mAuth = FirebaseAuth.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        next=findViewById(R.id.next);
        mQuestionView = findViewById(R.id.question);
        mButtonChoice1 = findViewById(R.id.option1);
        mButtonChoice2 = findViewById(R.id.option2);
        mButtonChoice3 = findViewById(R.id.option3);
        caterpillar=findViewById(R.id.caterpillar);
        urlcaterpillar="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/caterpillar.jpg?alt=media&token=faffd502-6614-4125-93b8-50de67fe0546";
        urlnext="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/nextgreenbutton.png?alt=media&token=60064096-a57e-4aa0-8cb7-511e933d97ca";
        Picasso.with(this).load(urlnext).into(next);
        Picasso.with(this).load(urlcaterpillar).into(caterpillar);
        new CountDownTimer(1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                PopUp_PlayGame p = new PopUp_PlayGame();
                p.showPopUp(ImmediateRecall.this,description);
            }
        }.start();

        mQuestionView.setText(myQuestionLibrary.mQuestions[counter]);
        mButtonChoice1.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);
        mButtonChoice2.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);
        mButtonChoice3.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);
        mButtonChoice1.setText(myQuestionLibrary.mChoice[counter][0]);
        mButtonChoice2.setText(myQuestionLibrary.mChoice[counter][1]);
        mButtonChoice3.setText(myQuestionLibrary.mChoice[counter][2]);

        mButtonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonChoice1.setBackgroundResource(R.drawable.roundbuttonimmediaterecall2);
                mButtonChoice2.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);
                mButtonChoice3.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);

            }
        });

        mButtonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonChoice2.setBackgroundResource(R.drawable.roundbuttonimmediaterecall2);
                mButtonChoice1.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);
                mButtonChoice3.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);

            }
        });
        mButtonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonChoice3.setBackgroundResource(R.drawable.roundbuttonimmediaterecall2);
                mButtonChoice1.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);
                mButtonChoice2.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag++;
                if(mButtonChoice1.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.roundbuttonimmediaterecall2).getConstantState()))
                {
                    if(mButtonChoice1.getText().toString().equals(myQuestionLibrary.mCorrect_answer[counter]))
                    {
                        mScore++;
                    }
                    updateQuestion();
                }
                else if(mButtonChoice2.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.roundbuttonimmediaterecall2).getConstantState()))
                {

                    if(mButtonChoice2.getText().toString().equals(myQuestionLibrary.mCorrect_answer[counter]))
                    {
                        mScore++;
                    }
                    updateQuestion();
                }
                else if(mButtonChoice3.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.roundbuttonimmediaterecall2).getConstantState()))
                {

                    if(mButtonChoice3.getText().toString().equals(myQuestionLibrary.mCorrect_answer[counter]))
                    {
                        mScore++;
                    }
                    updateQuestion();
                }

                if(counter==5 && flag==3)
                {
                    fuser = mAuth.getCurrentUser();
                    uid=fuser.getUid();
                    dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
                    dbUsers.child("immediateRecall").setValue(mScore);

                   Popup_aftergame panel = new Popup_aftergame();
                    panel.showPopUp(ImmediateRecall.this, stage_name);

                   /* Intent i=new Intent(getApplicationContext(), Attention_Intro.class);
                    startActivity(i);*/
                }
            }
        });
    }
public  void  updateQuestion()
{

    counter=5;
    mQuestionView.setText(myQuestionLibrary.mQuestions[counter]);
    mButtonChoice1.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);
    mButtonChoice2.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);
    mButtonChoice3.setBackgroundResource(R.drawable.roundbuttonimmediaterecall);
    mButtonChoice1.setText(myQuestionLibrary.mChoice[counter][0]);
    mButtonChoice2.setText(myQuestionLibrary.mChoice[counter][1]);
    mButtonChoice3.setText(myQuestionLibrary.mChoice[counter][2]);
}
}