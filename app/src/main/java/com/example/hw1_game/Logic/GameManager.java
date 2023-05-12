package com.example.hw1_game.Logic;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;


import com.example.hw1_game.Adapters.ScoreAdapter;
import com.example.hw1_game.Model.Score;
import com.example.hw1_game.Model.ScoreList;
import com.example.hw1_game.Utilities.MySP;
import com.example.hw1_game.Utilities.SignalGenerator;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GameManager
{
    private int life;
    private int score;
    private int hit;
    private final int DISTANCE_SCORE = 10;
    private final int BOOST_SCORE = 100;
    private final int TOP_TEN = 10;
    private ScoreList scoreList;
    public static final String SCORE_OBJ= "score_obj";
    public GameManager(int life) {
        this.life = life;
        this.score = 0;
        this.hit =0;

        String fromSP =  MySP.getInstance().getString(GameManager.SCORE_OBJ,"");

        ScoreList scorelistFromJson;
        if(!fromSP.isEmpty())
            scorelistFromJson = new Gson().fromJson(fromSP,ScoreList.class );
        else
            scorelistFromJson = new ScoreList();
        this.scoreList = scorelistFromJson;
    }

    public int getLife(){
        return life;
    }
    public int getScore() {
        return score;
    }
    public int getHit() {
        return hit;
    }
    public boolean isLose() {
        return life == hit;
    }
    public void gameEnded(double longitude,double latitude)
    {
        this.scoreList.setName("Top 10");

        scoreList.getScores().add(new Score()
                .setScore(this.score)
                .setLongitude(longitude)
                .setLatitude(latitude));
        if(scoreList.getScores().size() > TOP_TEN)
        {
            int min = findMinScore(scoreList.getScores());

            scoreList.getScores().remove(min);

        }

        String scoreListJson = new Gson().toJson(scoreList);
        MySP.getInstance().putString(SCORE_OBJ, scoreListJson);
    }

    private int findMinScore(ArrayList<Score> arr)
    {
        int min_score = arr.get(0).getScore();
        int min_index =0;
        for(int i=1; i< arr.size(); i++)
        {
            if(min_score > arr.get(i).getScore())
            {
                min_score = arr.get(i).getScore();
                min_index =i;
            }
        }
        return min_index;
    }

    public void upgradeScore()
    {
        score += DISTANCE_SCORE;
    }

    public void updateCollision(Context context, int collision) {
        if (collision == 1) //hit by an alien
        {
            hit++;
            SignalGenerator.getInstance().vibrate();
            SignalGenerator.getInstance().toast("You crashed the alien ",Toast.LENGTH_LONG);
            SignalGenerator.getInstance().playSound(collision);
        } else if(collision == 2) { //hit by a coin
            score += BOOST_SCORE;
            SignalGenerator.getInstance().toast("🥳 Yay!",Toast.LENGTH_LONG);
            SignalGenerator.getInstance().playSound(collision);
        }
        else {      //default
            score += DISTANCE_SCORE;
        }

    }





}
