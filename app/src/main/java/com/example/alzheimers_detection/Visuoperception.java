

//added images to firebase storage
package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.CheckBox;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Visuoperception extends AppCompatActivity {
    Button go;
    ImageView picture,eyesgif;
    String urlpicture,urleyesgif;
    float score=0,cnt=0;







    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;

    OnSwipeTouchListener onSwipeTouchListener;
    String description;
    CheckBox readbox,bookbox,orbox,novelbox,pagesbox,wordsbox,notbox,nobox,treebox,manbox,dogbox,catbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visuoperception);
        final String stage_name="Visuoperception";
        description = "\nUse your visuoperceptual skills to identify six hidden" +
                " words inside the living room. \n\nTick the \ncheckboxes with these hidden words from the list of words given below.";

        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.Visuoperception),stage_name);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        go=findViewById(R.id.go);
        bookbox=findViewById(R.id.bookbox);
        readbox=findViewById(R.id.readbox);
        wordsbox=findViewById(R.id.wordsbox);
        orbox=findViewById(R.id.orbox);
        pagesbox=findViewById(R.id.pagesbox);
        novelbox=findViewById(R.id.novelbox);
        notbox=findViewById(R.id.notbox);
        nobox=findViewById(R.id.nobox);
        dogbox=findViewById(R.id.dogbox);
        catbox=findViewById(R.id.catbox);
        manbox=findViewById(R.id.manbox);
        treebox=findViewById(R.id.treebox);

        Intent intent = getIntent();
        final String Play = intent.getStringExtra("Play");

            new CountDownTimer(1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                PopUp_PlayGame p = new PopUp_PlayGame();
                p.showPopUp(Visuoperception.this,description);
            }
        }.start();
        picture=findViewById(R.id.picture);
        urlpicture="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/picture.jpg?alt=media&token=57797058-ccc8-43ff-99aa-3825503ee41a";
        Picasso.with(this).load(urlpicture).into(picture);


        eyesgif=findViewById(R.id.eyesgif);
        urleyesgif="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/giphy.gif?alt=media&token=45d752e7-c369-4d37-aef2-0f939ed560a7";
        Picasso.with(this).load(urleyesgif).into(eyesgif);
        notbox.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                toToggle(notbox);
            }
        });

        readbox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggleImp(readbox);
            }
        });


        nobox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggle(nobox);
            }
        });


        pagesbox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggleImp(pagesbox);
            }
        });


        dogbox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggle(dogbox);
            }
        });


        orbox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggleImp(orbox);
            }
        });

        treebox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggle(treebox);
            }
        });


        manbox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggle(manbox);
            }
        });

        catbox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggle(catbox);
            }
        });

        novelbox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggleImp(novelbox);
            }
        });

        wordsbox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggleImp(wordsbox);
            }
        });

        bookbox.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                toToggleImp(bookbox);
            }
        });

        go.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {

                fuser = mAuth.getCurrentUser();
                uid=fuser.getUid();
                dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
                dbUsers.child("visuoperception").setValue(score);

                Popup_aftergame panel = new Popup_aftergame();
                panel.showPopUp(Visuoperception.this, stage_name);
                /*Intent i = new Intent(getApplicationContext(), Fluency_Intro.class);
                startActivity(i);*/
                Log.d("score",""+score);
                Log.d("cnt",""+cnt);
            }
        });

    }
    public void toToggle(CheckBox v)
    {
        if(v.isChecked()==true && cnt==6)
        {
            Toast toast = Toast.makeText(getApplicationContext(), Html.fromHtml("<font color='#ff0000' >" + "You've already selected 6 items!" + "</font>"), Toast.LENGTH_LONG);
            toast.getView().setBackgroundColor(Color.parseColor("#E6FB9A"));
            toast.show();
            Log.d("cnt",""+cnt);
            v.setChecked(false);
        }
        else
        {
            if (v.isChecked()==true)
            {
                cnt++;
                v.setChecked(true);
                Log.d("cnt",""+cnt);

            }
            else
            {
                cnt--;
                v.setChecked(false);
                Log.d("cnt",""+cnt);

            }
        }
    }
    public void toToggleImp(CheckBox v)
    {
        if(v.isChecked()==true && cnt==6)
        {
            Toast toast = Toast.makeText(getApplicationContext(), Html.fromHtml("<font color='#ff0000' >" + "You've already selected 6 items!" + "</font>"), Toast.LENGTH_LONG);
            toast.getView().setBackgroundColor(Color.parseColor("#E6FB9A"));
            toast.show();
            Log.d("cnt",""+cnt);
            v.setChecked(false);

        }
        else
        {
            if (v.isChecked()==true)
            {
                score+=0.5;
                cnt++;
                v.setChecked(true);
                Log.d("cnt",""+cnt);

            }
            else
            {
                score-=0.5;
                cnt--;
                v.setChecked(false);
                Log.d("cnt",""+cnt);

            }
        }
    }
}