package com.example.alzheimers_detection;

import android.content.res.Resources;

public class QuestionLibrary {
    //questions
    String mQuestions[] ={
            " What has the Caterpillar lost ?","What was the sequence in which caterpillar met her friends?","The Frog's shoe is ____ for the Caterpillar.","What are Crab's shoes used for ?"
            ,"Who did the Caterpillar not ask for help ?","What does the caterpillar decide to do at the end ?"};

    //choices
    String mChoice[][]={
            {"Shoe","Glasses","Hat"},
            {"Frog -> Spider -> Crab","Spider -> Frog -> Crab","Crab -> Spider -> Frog"},
            {"too small","too big","suitable"},
            {"swimming","walking","digging"},
            {"squirrel","frog","spider"},
            {"Lay eggs","Make a cozy cocoon","Look for more shoes"}
    };

    //correct answers
    String mCorrect_answer[]={"Spider -> Frog -> Crab","make a cozy cocoon","Shoe","too big","digging","squirrel"};

    public String getQuestions(int a) {
        String questions = mQuestions[a];
        return questions;
    }

    public String getCorrect_answer(int a) {
        String correct_answer=mCorrect_answer[a];
        return correct_answer;
    }

    public String getChoice0(int a) {
        String choice0 = mChoice[a][0];
        return choice0;
    }

    public String getChoice1(int a) {
        String choice1 = mChoice[a][1];
        return choice1;
    }
    public String getChoice2(int a) {
        String choice2 = mChoice[a][2];
        return choice2;
    }
}
