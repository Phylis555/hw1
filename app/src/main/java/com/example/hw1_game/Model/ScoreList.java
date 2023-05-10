package com.example.hw1_game.Model;

import java.util.ArrayList;

public class ScoreList {

    private String name = "";
    private ArrayList<Score> scores = new ArrayList<>();

    public ScoreList() {
    }

    public String getName() {
        return name;
    }

    public ScoreList setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public ScoreList setScores(ArrayList<Score> scores) {
        this.scores = scores;
        return this;
    }

    @Override
    public String toString() {
        return "ScoreList{" +
                "name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }
}
