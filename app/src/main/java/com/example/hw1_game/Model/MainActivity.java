package com.example.hw1_game.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import com.bumptech.glide.Glide;
import com.example.hw1_game.Logic.GameManager;
import com.example.hw1_game.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView main_IMG_background;
    private ShapeableImageView[] main_IMG_hearts;
    private ImageView[] spaceship;
    private ImageView[][] alienMat;
    private ExtendedFloatingActionButton lButton;
    private ExtendedFloatingActionButton rButton;
    private ExtendedFloatingActionButton playButton;
    public final int DELAY = 1000;
    public final int CAR_ROW = 4;

    private int currentPosition = 1;
    private int heartIndex = 3;

    private GameManager gameManager;

    private final Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            checkCollision();
            alienDownwards();
            handler.postDelayed(this, DELAY); //Do it again in a second
            generateRow();
        }
    };

    private void checkCollision() {
        int carPos;
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        for (int i = 0; i < 3; i++) {
            carPos = getSpaceshipPos();
            if (alienMat[CAR_ROW - 1][i].getVisibility() == View.VISIBLE && carPos == i) {
                if (heartIndex != 0)        // for endless game
                {
                    main_IMG_hearts[heartIndex - 1].setVisibility(View.INVISIBLE);
                    heartIndex--;
                }

                Toast.makeText(MainActivity.this, "You crashed the alien ", Toast.LENGTH_LONG).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(500);
                }
            }
            alienMat[CAR_ROW - 1][i].setVisibility(View.INVISIBLE);
        }

    }

    private void alienDownwards(){
        for(int i = 3; i>=0; i--)
        {
            for(int j=0; j<alienMat[i].length; j++)
            {
                if(alienMat[i][j].getVisibility() == View.VISIBLE)
                {
                    alienMat[i][j].setVisibility(View.INVISIBLE);
                    alienMat[i+1][j].setVisibility(View.VISIBLE);

                }
            }
        }
    }





    private void generateRow()
    {
        Random r = new Random();
        int randNum = r.nextInt(3);
        alienMat[0][randNum].setVisibility(View.VISIBLE);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        gameManager = new GameManager(main_IMG_hearts.length);
        Glide
                .with(this)
                .load("https://cdn.wallpapersafari.com/37/18/VDax80.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(main_IMG_background);

        startView();

        rButton.setOnClickListener(view -> moveSpaceshipRight());
        lButton.setOnClickListener(view -> moveSpaceshipLeft());
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rButton.setVisibility(View.VISIBLE);
                lButton.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.INVISIBLE);
                handler.postDelayed(runnable,DELAY);
            }
        });


    }
    private void startView()
    {
        for(int i=0;i<alienMat.length;i++) {
            for (int j = 0; j < alienMat[i].length; j++)
            {
                alienMat[i][j].setVisibility(View.INVISIBLE);

            }
        }
        spaceship[0].setVisibility(View.INVISIBLE);
        spaceship[2].setVisibility(View.INVISIBLE);
        rButton.setVisibility(View.INVISIBLE);
        lButton.setVisibility(View.INVISIBLE);
    }
    private void moveSpaceshipRight() {
        currentPosition = getSpaceshipPos();
      if(currentPosition !=2) {
          spaceship[currentPosition + 1].setVisibility(View.VISIBLE);
          spaceship[currentPosition].setVisibility(View.INVISIBLE);
      }
    }

    private void moveSpaceshipLeft() {

        currentPosition = getSpaceshipPos();
        if(currentPosition !=0) {
            spaceship[currentPosition - 1].setVisibility(View.VISIBLE);
            spaceship[currentPosition].setVisibility(View.INVISIBLE);
        }
    }
    private int getSpaceshipPos()
    {
        for(int i =0; i< spaceship.length; i++) {
            if(spaceship[i].getVisibility() == View.VISIBLE)
               return i;
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }



    @SuppressLint("CutPasteId")
    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);

        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
        rButton = findViewById(R.id.fab_right);
        lButton = findViewById(R.id.fab_left);
        playButton = findViewById(R.id.fab_play);

        spaceship = new ImageView[]{
                findViewById(R.id.spaceship_0),
                findViewById(R.id.spaceship_1),
                findViewById(R.id.spaceship_2)};

        alienMat = new ImageView[][]{
                {findViewById(R.id.alien_0), findViewById(R.id.alien_1), findViewById(R.id.alien_2)},
                {findViewById(R.id.alien_3), findViewById(R.id.alien_4), findViewById(R.id.alien_5)},
                {findViewById(R.id.alien_6), findViewById(R.id.alien_7), findViewById(R.id.alien_8)},
                {findViewById(R.id.alien_9), findViewById(R.id.alien_10), findViewById(R.id.alien_11)}
        };


    }

}