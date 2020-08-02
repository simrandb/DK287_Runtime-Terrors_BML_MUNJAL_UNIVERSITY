package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Roadmap extends AppCompatActivity {
    TextView myjournal, start,stage1,stage2,stage3,stage4,stage5,stage6,stage7,stage8,stage9,stage10;
    TextView inst_myjournal,inst_start,inst_stage1,inst_stage2,inst_stage3,inst_stage4,inst_stage5,inst_stage6,inst_stage7,inst_stage8,inst_stage9,inst_stage10;
    ImageView man_myjournal,man_start,man_stage1,man_stage2,man_stage3,man_stage4,man_stage5,man_stage6,man_stage7,man_stage8,man_stage9,man_stage10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roadmap);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String stage_name = intent.getStringExtra("stage_name");

        start=findViewById(R.id.Start);
        myjournal=findViewById(R.id.MyJournal);
        stage1=findViewById(R.id.supercoincollector);
        stage2=findViewById(R.id.guestimate_us);
        stage3=findViewById(R.id.groupitright);
        stage4=findViewById(R.id.burstthemathout);
        stage5=findViewById(R.id.thealzheimerstimes);
        stage6=findViewById(R.id.CaterpillarAdventures);
        stage7=findViewById(R.id.DoughnutvsDoughnut);
        stage8=findViewById(R.id.MysteryRoom);
        stage9=findViewById(R.id.Talktillyoudrop);
        stage10=findViewById(R.id.EideticEvocation);



        inst_start=findViewById(R.id.youarehere_start);
        inst_myjournal=findViewById(R.id.youarehere_MyJournal);
        inst_stage1=findViewById(R.id.youarehere_supercoincollector);
        inst_stage2=findViewById(R.id.youarehere_guestimate_us);
        inst_stage3=findViewById(R.id.youarehere_groupitright);
        inst_stage4=findViewById(R.id.youarehere_burstthemathout);
        inst_stage5=findViewById(R.id.youarehere_thealzheimerstimes);
        inst_stage6=findViewById(R.id.youarehere_CaterpillarAdventures);
        inst_stage7=findViewById(R.id.youarehere_DoughnutvsDoughnut);
        inst_stage8=findViewById(R.id.youarehere_MysteryRoom);
        inst_stage9=findViewById(R.id.youarehere_Talktillyoudrop);
        inst_stage10=findViewById(R.id.youarehere_EideticEvocation);

        man_start=findViewById(R.id.man_start);
        man_myjournal=findViewById(R.id.man_MyJournal);
        man_stage1=findViewById(R.id.man_supercoincollector);
        man_stage2=findViewById(R.id.man_guestimate_us);
        man_stage3=findViewById(R.id.man_groupitright);
        man_stage4=findViewById(R.id.man_burstthemathout);
        man_stage5=findViewById(R.id.man_thealzheimerstimes);
        man_stage6=findViewById(R.id.man_CaterpillarAdventures);
        man_stage7=findViewById(R.id.man_DoughnutvsDoughnut);
        man_stage8=findViewById(R.id.man_MysteryRoom);
        man_stage9=findViewById(R.id.man_Talktillyoudrop);
        man_stage10=findViewById(R.id.man_EideticEvocation);

        inst_myjournal.setVisibility(View.GONE);
        man_myjournal.setVisibility(View.GONE);

        start.setClickable(false);
        myjournal.setClickable(false);
        stage1.setClickable(false);
        stage2.setClickable(false);
        stage3.setClickable(false);
        stage4.setClickable(false);
        stage5.setClickable(false);
        stage6.setClickable(false);
        stage7.setClickable(false);
        stage8.setClickable(false);
        stage9.setClickable(false);
        stage10.setClickable(false);


        man_start.setVisibility(View.GONE);
        man_stage1.setVisibility(View.GONE);
        man_stage2.setVisibility(View.GONE);
        man_stage3.setVisibility(View.GONE);
        man_stage4.setVisibility(View.GONE);
        man_stage5.setVisibility(View.GONE);
        man_stage6.setVisibility(View.GONE);
        man_stage7.setVisibility(View.GONE);
        man_stage8.setVisibility(View.GONE);
        man_stage9.setVisibility(View.GONE);
        man_stage10.setVisibility(View.GONE);

        inst_start.setVisibility(View.GONE);
        inst_stage1.setVisibility(View.GONE);
        inst_stage2.setVisibility(View.GONE);
        inst_stage3.setVisibility(View.GONE);
        inst_stage4.setVisibility(View.GONE);
        inst_stage5.setVisibility(View.GONE);
        inst_stage6.setVisibility(View.GONE);
        inst_stage7.setVisibility(View.GONE);
        inst_stage8.setVisibility(View.GONE);
        inst_stage9.setVisibility(View.GONE);
        inst_stage10.setVisibility(View.GONE);

        if(stage_name==null)
        {
            start.setClickable(true);
            man_start.setVisibility(View.VISIBLE);
            inst_start.setVisibility(View.VISIBLE);
        }
        else
        {
            if(stage_name.contains("ExecutiveFunctioning"))
            {
                stage2.setClickable(true);
                man_stage2.setVisibility(View.VISIBLE);
                inst_stage2.setVisibility(View.VISIBLE);
            }
            else if(stage_name.contains("Naming"))
            {
                stage3.setClickable(true);
                man_stage3.setVisibility(View.VISIBLE);
                inst_stage3.setVisibility(View.VISIBLE);

            }
            else if(stage_name.contains("Abstraction"))
            {
                stage4.setClickable(true);
                man_stage4.setVisibility(View.VISIBLE);
                inst_stage4.setVisibility(View.VISIBLE);

            }
            else if(stage_name.contains("Calculation"))
            {
                stage5.setClickable(true);
                man_stage5.setVisibility(View.VISIBLE);
                inst_stage5.setVisibility(View.VISIBLE);

            }
            else if(stage_name.contains("Orientation"))
            {
                stage6.setClickable(true);
                man_stage6.setVisibility(View.VISIBLE);
                inst_stage6.setVisibility(View.VISIBLE);

            }
            else if(stage_name.contains("ImmediateRecall"))
            {
                stage7.setClickable(true);
                man_stage7.setVisibility(View.VISIBLE);
                inst_stage7.setVisibility(View.VISIBLE);

            }
            else if(stage_name.contains("Attention"))
            {
                stage8.setClickable(true);
                man_stage8.setVisibility(View.VISIBLE);
                inst_stage8.setVisibility(View.VISIBLE);

            }
            else if(stage_name.contains("Visuoperception"))
            {
                stage9.setClickable(true);
                man_stage9.setVisibility(View.VISIBLE);
                inst_stage9.setVisibility(View.VISIBLE);

            }
            else if(stage_name.contains("Fluency"))
            {
                stage10.setClickable(true);
                man_stage10.setVisibility(View.VISIBLE);
                inst_stage10.setVisibility(View.VISIBLE);

            }
            else if(stage_name.contains("DelayedRecall"))
            {
                myjournal.setClickable(true);
                inst_myjournal.setVisibility(View.VISIBLE);
                man_myjournal.setVisibility(View.VISIBLE);

            }
        }


    }

    public void Start_game(View view) {
        Intent i=new Intent(getApplicationContext(),ExecutiveFunctioning_Intro.class);
        startActivity(i);

    }

    public void Stage1(View view) {Intent i=new Intent(getApplicationContext(),ExecutiveFunctioning_Intro.class);
        startActivity(i);

    }

    public void stage2(View view) {Intent i=new Intent(getApplicationContext(),Naming_Intro.class);
        startActivity(i);

    }

    public void stage3(View view) {Intent i=new Intent(getApplicationContext(),Abstraction_Intro.class);
        startActivity(i);

    }

    public void stage4(View view) { Intent i=new Intent(getApplicationContext(),Calculation_Intro.class);
        startActivity(i);

    }

    public void stage5(View view) {Intent i=new Intent(getApplicationContext(),Orientation_Intro.class);
        startActivity(i);

    }

    public void stage6(View view) {Intent i=new Intent(getApplicationContext(),ImmediateRecall_Intro.class);
        startActivity(i);

    }

    public void stage7(View view) {Intent i=new Intent(getApplicationContext(),Attention_Intro.class);
        startActivity(i);

    }

    public void stage8(View view) {Intent i=new Intent(getApplicationContext(),Visuoperception_Intro.class);
        startActivity(i);

    }

    public void stage9(View view) {Intent i=new Intent(getApplicationContext(),Fluency_Intro.class);
        startActivity(i);

    }

    public void stage10(View view) { Intent i=new Intent(getApplicationContext(),DelayedRecall_Intro.class);
        startActivity(i);

    }

    public void results(View view) {
        Intent i=new Intent(getApplicationContext(),AskForJournal.class);
        startActivity(i);
    }

    public void askforjournal(View view) {
        Intent i=new Intent(getApplicationContext(),AskForJournal.class);
        startActivity(i);
    }
}