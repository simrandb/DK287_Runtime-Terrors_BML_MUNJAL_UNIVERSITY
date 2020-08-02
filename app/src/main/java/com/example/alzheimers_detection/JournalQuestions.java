package com.example.alzheimers_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JournalQuestions extends AppCompatActivity {
    ImageView mood1,mood2,mood3,mood4,mood5;
    String patientsmood,dailyactivitiesfeel,work,sleepcycleis,bodypainornot,relationshipbehavioural;
    Button submit;
    RadioGroup working,sleepcycle,dailyactivities,bodypain;
    int score=0,set=0;
    RatingBar ratingBar;
    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;
    RadioButton sedentary,lethargic,moderatelyactive,highlyactive,focusedwork,distractedwork,regularsleep,irregularsleep,cantsay,bodypainnever,bodypainsometimes,bodypainmostly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal_questions);
        mAuth = FirebaseAuth.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        submit=findViewById(R.id.submit);
        ratingBar=findViewById(R.id.ratingBar);
        mood1=findViewById(R.id.mood1);
        mood2=findViewById(R.id.mood2);
        mood3=findViewById(R.id.mood3);
        mood4=findViewById(R.id.mood4);
        mood5=findViewById(R.id.mood5);
        cantsay=findViewById(R.id.cantsay);
        bodypainsometimes=findViewById(R.id.bodypainsometimes);
        bodypainnever=findViewById(R.id.bodypainnever);
        bodypainmostly=findViewById(R.id.bodypainmostly);

        regularsleep=findViewById(R.id.regularsleep);
        irregularsleep=findViewById(R.id.irregularsleep);


        sedentary=findViewById(R.id.sedentary);
        distractedwork=findViewById(R.id.distractedwork);

        focusedwork=findViewById(R.id.focusedwork);
        lethargic=findViewById(R.id.lethargic);
        moderatelyactive=findViewById(R.id.moderatelyactive);
        highlyactive=findViewById(R.id.highlyactive);

        bodypain=findViewById(R.id.bodypain);
        working=findViewById(R.id.working);
        sleepcycle=findViewById(R.id.sleepcycle);
        dailyactivities=findViewById(R.id.dailyactivities);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (set==0 || bodypain.getCheckedRadioButtonId()==-1 || dailyactivities.getCheckedRadioButtonId()==-1 || sleepcycle.getCheckedRadioButtonId()==-1 || working.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(),"All the questions are compulsory!",Toast.LENGTH_LONG).show();
                }
                else {
                    fuser = mAuth.getCurrentUser();
                    uid=fuser.getUid();
                    dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
                    if (set == 1 || set == 2 || set == 3) {
                        score++;

                    }
                    dbUsers.child("mood").setValue(set);

                    if(highlyactive.isChecked())
                    {
                        score++;
                        dbUsers.child("carryingOutMyDailyActivities").setValue(4);

                    }
                    else if(moderatelyactive.isChecked())
                    {
                        score++;
                        dbUsers.child("carryingOutMyDailyActivities").setValue(3);

                    }
                    else if(sedentary.isChecked())
                    {
                        dbUsers.child("carryingOutMyDailyActivities").setValue(1);

                    }
                    else
                    {
                        dbUsers.child("carryingOutMyDailyActivities").setValue(1);

                    }

                    if(focusedwork.isChecked())
                    {
                        score++;
                        dbUsers.child("imWorkingIfeel").setValue(1);
                    }
                    else
                    {
                        dbUsers.child("imWorkingIfeel").setValue(1);
                    }

                    if(regularsleep.isChecked())
                    {
                        score++;
                        dbUsers.child("sleepCycle").setValue(1);

                    }
                    else
                    {
                        dbUsers.child("sleepCycle").setValue(0);
                    }



                    if (bodypainnever.isChecked())
                    {
                        score++;
                        dbUsers.child("experienceBodyPain").setValue(1);

                    }
                    else
                    {
                        dbUsers.child("experienceBodyPain").setValue(0);
                    }

                    float getrating = ratingBar.getRating();

                    if(getrating>=2.5)
                    {
                        score++;
                        dbUsers.child("starRating").setValue(getrating);

                    }
                    dbUsers.child("starRating").setValue(getrating);

                    dbUsers.child("totalBehaviouralScore").setValue(score);

                    Intent i =new Intent(getApplicationContext(),HomeScreen.class);
                    startActivity(i);
                }


            }
        });
        mood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set=1;

                mood1.setBackgroundColor(Color.GRAY);
                mood2.setBackgroundColor(Color.WHITE);
                mood3.setBackgroundColor(Color.WHITE);
                mood4.setBackgroundColor(Color.WHITE);
                mood5.setBackgroundColor(Color.WHITE);

            }
        });
        mood2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set=2;
                mood2.setBackgroundColor(Color.GRAY);
                mood1.setBackgroundColor(Color.WHITE);
                mood3.setBackgroundColor(Color.WHITE);
                mood4.setBackgroundColor(Color.WHITE);
                mood5.setBackgroundColor(Color.WHITE);


            }
        }); mood3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set=3;

                mood3.setBackgroundColor(Color.GRAY);
                mood1.setBackgroundColor(Color.WHITE);
                mood2.setBackgroundColor(Color.WHITE);
                mood4.setBackgroundColor(Color.WHITE);
                mood5.setBackgroundColor(Color.WHITE);

            }
        }); mood4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set=4;

                mood4.setBackgroundColor(Color.GRAY);
                mood1.setBackgroundColor(Color.WHITE);
                mood2.setBackgroundColor(Color.WHITE);
                mood3.setBackgroundColor(Color.WHITE);
                mood5.setBackgroundColor(Color.WHITE);

            }
        }); mood5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set=5;

                mood5.setBackgroundColor(Color.GRAY);
                mood1.setBackgroundColor(Color.WHITE);
                mood2.setBackgroundColor(Color.WHITE);
                mood3.setBackgroundColor(Color.WHITE);
                mood4.setBackgroundColor(Color.WHITE);

            }
        });

        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();
        final DatabaseReference userDBRef = FirebaseDatabase.getInstance().getReference("Users");
        userDBRef.child(uid).child("behaviouralResultOrNot").setValue(1);

        userDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(uid).getValue(User.class);
                if(user.getMood()==1)
                {
                    patientsmood="Happy";
                }
                else if(user.getMood()==2)
                {
                    patientsmood="Smiley";
                }
                else if(user.getMood()==3)
                {
                    patientsmood="Neutral";
                }
                else if(user.getMood()==4)
                {
                    patientsmood="Irritated";
                }
                else
                {
                    patientsmood="Angry";
                }

                if(user.getImWorkingIfeel()==1)
                {
                    work="Focused";
                }
                else
                {
                    work="Distracted";
                }


                if(user.getCarryingOutMyDailyActivities()==1)
                {
                    dailyactivitiesfeel="Sedentary";
                }
                else if(user.getCarryingOutMyDailyActivities()==2)
                {
                    dailyactivitiesfeel="Lethargic";
                }
                else if(user.getCarryingOutMyDailyActivities()==3)
                {
                    dailyactivitiesfeel="Moderately Active";
                }
                else
                {
                    dailyactivitiesfeel="Highly Active";
                }

                if(user.getSleepCycle()==1)
                {
                    sleepcycleis="Regular";
                }
                else
                {
                    sleepcycleis="Mostly Irregular";
                }

                if(user.getExperienceBodyPain()==1)
                {
                    bodypainornot="Never";
                }
                else
                {
                    bodypainornot="Sometimes";
                }

                relationshipbehavioural= String.valueOf(user.getStarRating());
                String behaviouralInfo="\nPatient's Journal:\nMood on Average:"+patientsmood+"\nFeel when doing daily activities:"+dailyactivitiesfeel+"\nFeel when working:"+work+"\nSleep Cycle:"+sleepcycleis+"Body Pain:"+bodypainornot+"\nRating of relationship with people:"+relationshipbehavioural  ;
                userDBRef.child(uid).child("behavioural_info").setValue(behaviouralInfo);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occured");
            }


        });





    }
}