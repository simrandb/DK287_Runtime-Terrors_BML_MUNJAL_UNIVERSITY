package com.example.alzheimers_detection;

public class Element {
    private String date;
    private int score;


    public Element(int score,String date) {
        this.score = score;
        this.date = date;
    }


    public int getScoreInt() {
        return score;
    }

    public String getScore() {
        return "Score: " + score;
    }

    public String getDate() {
        return date;
    }

}