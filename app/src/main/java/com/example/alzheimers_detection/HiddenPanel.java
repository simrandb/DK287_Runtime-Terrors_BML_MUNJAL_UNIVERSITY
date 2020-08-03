package com.example.alzheimers_detection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

class HiddenPanel {

    String description;

    //call this method to show a pop up
    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    void showPopUp(final Context c, final String stage_name){
        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout (no need for root id, using entire layout)
        View layout = inflater.inflate(R.layout.menu_popup,null);
        //to set game description
        //Get the devices screen density to calculate correct pixel sizes
        float density=c.getResources().getDisplayMetrics().density;
        // create a focusable PopupWindow with the given layout and correct size
        final PopupWindow pw = new PopupWindow(layout, (int)density*390, (int)density*520, true);
        pw.setOutsideTouchable(false);

        // display the pop-up in the center
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

        layout.findViewById(R.id.how_to_play).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(stage_name.contains("ExecutiveFunctioning"))
                    description = "You are “ "+"xyz"+" the Thief ! ”"+"\nEach coin inside the cave has a number or alphabet engraved on it.\n\nTap these coins to " +
                            "collect them into sack such that a number is followed by its corresponding alphabet, in " +
                            "increasing order, making an alternate trail.";
                if(stage_name.contains("Naming"))
                    description = "\nYou are in Jungle Safari and you spot four animals.\n\n\n" +
                            "Tap on the jumbled alphabets to arrange them in order to form appropriate name of the animal.";
                if(stage_name.contains("Abstraction"))
                    description = "\nMultiple items corresponding to three different categories namely -\n Sports, Instruments and Fruits \nare displayed on a shelf."+"\n\nDrag and drop each item into its corresponding " +
                            "basket to empty\n the shelf.";
                if(stage_name.contains("Calculation"))
                    description = "\n\nFly and enjoy through three \nlevels of sky. \n\nTap on the cloud with greater value to burst it and move to a higher level in the sky.";
                if(stage_name.contains("Orientation"))
                    description = "\nYou are "+"xyz"+", the Chief Editor of “The Alzheimer’s Times “! An incomplete Newspaper format" +
                            " \nwill be shown.\nClick on the highlighted fields to select the appropriate details and complete the" +
                            " format.";
                if(stage_name.contains("ImmediateRecall"))
                    description = "\nAnswer the next two questions based on the previous video."+
                            "\n\n\nNote that you cannot go back to any of the questions that you have already answered.";
                if(stage_name.contains("Attention"))
                    description = "\nYou are \n“"+"xyz"+" – The Doughnut Seller“.\n\nLook at each doughnut carefully and answer in terms of" +
                            " \n“YES / NO” based on whether the previous doughnut is same as the current one.";                if(stage_name.contains("Visuoperception"))
                if(stage_name.contains("Fluency"))
                    description = "\nPress the microphone on the screen, and speak as many words as you can, think of words from the " +
                            "letter given in 60 seconds.\n\nWords that are proper nouns, numbers, and different forms of a verb " +
                            "are not permitted.";
                if(stage_name.contains("DelayedRecall"))
                    description = "\nRecall the story from Caterpillar's adventure, and answer six related " +
                            "questions.\n\nNote that you cannot go back to any of the questions that you have already answered.";

                new CountDownTimer(500,500){

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        PopUp_PlayGame p = new PopUp_PlayGame();
                        p.showPopUp(c,description);
                    }
                }.start();
                pw.dismiss();

            }
        });
        layout.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(c, HomeScreen.class);
                c.startActivity(myIntent);
                pw.dismiss();
            }
        });
        layout.findViewById(R.id.nextstage).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(stage_name.contains("ExecutiveFunctioning"))
                    next_stage1(c);
                if(stage_name.contains("Naming"))
                    next_stage2(c);
                if(stage_name.contains("Abstraction"))
                    next_stage3(c);
                if(stage_name.contains("Calculation"))
                    next_stage4(c);
                if(stage_name.contains("Orientation"))
                    next_stage5(c);
                if(stage_name.contains("ImmediateRecall"))
                    next_stage6(c);
                if(stage_name.contains("Attention"))
                    next_stage7(c);
                if(stage_name.contains("Visuoperception"))
                    next_stage8(c);
                if(stage_name.contains("Fluency"))
                    next_stage9(c);
                if(stage_name.contains("DelayedRecall"))
                    next_stage10(c);
                pw.dismiss();
            }
        });
        layout.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(c, Settings.class);
                c.startActivity(myIntent);
                pw.dismiss();
            }
        });
    }
    private void next_stage10(Context c) {
        Intent myIntent = new Intent(c, AskForJournal.class);
        myIntent.putExtra("Play", "no");
        c.startActivity(myIntent);
    }

    private void next_stage9(Context c) {
        Intent myIntent = new Intent(c, DelayedRecall_Intro.class);
        myIntent.putExtra("Play", "no");
        c.startActivity(myIntent);
    }

    private void next_stage8(Context c) {
        Intent myIntent = new Intent(c, Fluency_Intro.class);
        myIntent.putExtra("Play", "no");
        c.startActivity(myIntent);
    }

    private void next_stage7(Context c) {
        Intent myIntent = new Intent(c, Visuoperception_Intro.class);
        myIntent.putExtra("Play", "no");
        c.startActivity(myIntent);
    }

    private void next_stage6(Context c) {
        Intent myIntent = new Intent(c, Attention_Intro.class);
        myIntent.putExtra("Play", "no");
        c.startActivity(myIntent);
    }

    private void next_stage5(Context c) {
        Intent myIntent = new Intent(c, ImmediateRecall_Intro.class);
        myIntent.putExtra("Play", "no");
        c.startActivity(myIntent);
    }

    private void next_stage4(Context c) {
        Intent myIntent = new Intent(c, Orientation_Intro.class);
        myIntent.putExtra("Play", "no");
        c.startActivity(myIntent);
    }

    private void next_stage3(Context c) {
        Intent myIntent = new Intent(c, Calculation_Intro.class);
        myIntent.putExtra("Play", "no");
        c.startActivity(myIntent);
    }

    private void next_stage2(Context c) {
        Intent myIntent = new Intent(c, Abstraction_Intro.class);
        myIntent.putExtra("Play", "no");
        c.startActivity(myIntent);
    }

    private void next_stage1(Context c) {
        Intent myIntent = new Intent(c, Naming_Intro.class);
        myIntent.putExtra("Play", "no");
        c.startActivity(myIntent);
    }

}


