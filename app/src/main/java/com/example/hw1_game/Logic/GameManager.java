package com.example.hw1_game.Logic;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;


import com.example.hw1_game.Utilities.SignalGenerator;

import java.util.ArrayList;

public class GameManager
{


    private int life;
    private int score;
    private int hit;
    private final int DISTANCE_SCORE = 10;
    private final int BOOST_SCORE = 100;



    public GameManager(int life) {
        this.life = life;
        this.score = 0;
        this.hit =0;
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
        } else if(collision == 2) { //hit by a coin
            score += BOOST_SCORE;
            SignalGenerator.getInstance().toast("🥳 Yay!",Toast.LENGTH_LONG);

        }
        else {      //default
            score += DISTANCE_SCORE;
        }

    }





}
