package com.example.afinal;

public class Score {
    private String gmail;
    private int score;


    public Score(String gmail, int score) {
        this.gmail = gmail;
        this.score = score;
    }

    public String getFootballName() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
