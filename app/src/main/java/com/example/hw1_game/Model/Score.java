package com.example.hw1_game.Model;

public class Score {

    private int score = 0;
    private double mLatitude= 0;
    private double mLongitude= 0;

    public Score() {}


    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public Score setLatitude(double latitude) {
        this.mLatitude = latitude;
        return this;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public Score setLongitude(double longitude) {
        this.mLongitude = longitude;
        return this;
    }

    @Override
    public String toString() {
        return "Score: " + score+
                " , Location= ['" + mLatitude  +
                "," + mLongitude + '\'' +
                "]";
    }
}
