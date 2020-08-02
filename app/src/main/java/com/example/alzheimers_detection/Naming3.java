package com.example.alzheimers_detection;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Naming3 extends AppCompatActivity {
    ProgressBar frog ;
    float x_pos;
    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;
     String stage_name;
    int user_index;
    int score=1;
    String[] user_input={"","","",""};
    String[] correct_sequence={"f","r","o","g"};
    String[] Score={"zero","one","two","three"};
    OnSwipeTouchListener onSwipeTouchListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naming3);
        mAuth = FirebaseAuth.getInstance();

       stage_name="Naming";
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.Naming3),stage_name);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        frog = findViewById(R.id.progressBar13);
        user_index=-1;
        x_pos=200;


         /* Glide.with(this)
                .load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar13.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar13.setVisibility(View.GONE);
                return false;
            }
        }).into(gify);*/

    }

    public void nextActivity(View view) {
        check();
        Bundle extras = getIntent().getExtras();
        String stringVariableName = extras.getString("score");
        float prevScore=0;
        for(int i=0;i<Score.length;i++)
        {
           if(stringVariableName.contains(Score[i]))
           {
               prevScore=i;
               break;
           }
        }


        //float prevScore=Float.parseFloat(stringVariableName);

        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();
        dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
        dbUsers.child("naming").setValue((prevScore+score));


        /*Intent intent = new Intent(this, Abstraction_Intro.class);
        startActivity(intent);*/
        Popup_aftergame panel = new Popup_aftergame();
        panel.showPopUp(Naming3.this, stage_name);


    }

    private void check() {
        for(int i=0;i<correct_sequence.length;i++)
        {
            if(!user_input[i].contains(correct_sequence[i]))
                score=0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void animate(float x_position, Button b)
    {
        Path path = new Path();
        path.moveTo(x_position,340);
        ObjectAnimator anim = ObjectAnimator.ofFloat(b,View.X,View.Y ,path);
        anim.setDuration(100);
        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void click_frog(View button) {
        user_index++;
        make_invisible(user_index+1);
        switch (button.getId())
        {
            case R.id.frog_f:
                user_input[user_index]="f";
                break;
            case R.id.frog_r:
                user_input[user_index]="r";
                break;
            case R.id.frog_o:
                user_input[user_index]="o";
                break;
            case R.id.frog_g:
                user_input[user_index]="g";
                break;

        }
        animate(x_pos, (Button) button);
        x_pos = x_pos +180;
    }

    private void make_invisible(int dash_number)
    {
        View v = null;
        if(dash_number==1)
            v=findViewById(R.id.Dash1);
        if(dash_number==2)
            v=findViewById(R.id.Dash2);
        if(dash_number==3)
            v=findViewById(R.id.Dash3);
        if(dash_number==4)
            v=findViewById(R.id.Dash4);
        v.setVisibility(View.GONE);
    }

    public void resetActivity(View view) {
        Intent intent = new Intent(this, Naming3.class);
        check();
        Bundle extras = getIntent().getExtras();
        String stringVariableName = extras.getString("score");
        int prevScore=0;
        for(int i=0;i<Score.length;i++)
        {
            if(stringVariableName.contains(Score[i]))
            {
                prevScore=i;
                break;
            }
        }
        intent.putExtra("score",(Score[prevScore+score]));
        startActivity(intent);
    }


}
