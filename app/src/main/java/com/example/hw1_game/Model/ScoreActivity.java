package com.example.hw1_game.Model;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1_game.R;
import com.google.android.material.textview.MaterialTextView;

public class ScoreActivity  extends AppCompatActivity {
    public static final String KEY_STATUS = "KEY_STATUS";
    public static final String KEY_SCORE = "KEY_SCORE";
    public static int SCORE;


    private MaterialTextView score_LBL_score;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_item);

        score_LBL_score = findViewById(R.id.score_LBL);

        Intent intentScore = getIntent();
        if (intentScore != null) {
            SCORE = intentScore.getIntExtra(KEY_SCORE, 0);
        }
        score_LBL_score.setText("" + SCORE);
    }


}
