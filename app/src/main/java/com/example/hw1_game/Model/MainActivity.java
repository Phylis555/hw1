package com.example.hw1_game.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

import com.bumptech.glide.Glide;
import com.example.hw1_game.Interfaces.StepCallback;
import com.example.hw1_game.Logic.GameManager;
import com.example.hw1_game.R;
import com.example.hw1_game.Utilities.SignalGenerator;
import com.example.hw1_game.Utilities.StepDetector;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView main_IMG_background;
    private MaterialTextView main_LBL_score;
    private ShapeableImageView[] main_IMG_hearts;
    private ImageView[] spaceship;
    private ImageView[][] alienMat;
    private ImageView[][] powerMat;
    private ExtendedFloatingActionButton lButton;
    private ExtendedFloatingActionButton rButton;

    private LocationManager locationManager;

    private Location location;
    private double latitude, longitude;

    public static final String KEY_DELAY = "KEY_DELAY";

    public int DELAY = 1000;
    public static final String KEY_SENSOR = "KEY_SENSOR";
    public boolean sensorFlag = false;
    public boolean gameOverFlag = false;

    public final int ROW_BOARD = 6;
    public final int COL_BOARD = 5;
    private int currentPosition = 2;
    private int heartIndex = 3;
    private GameManager gm;
    private StepDetector stepDetector;

    private final Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshUI();
            alienDownwards();
            generateRow();
            handler.postDelayed(this, DELAY); //Do it again in a second
        }
    };


    private int checkCollision() {
        int hit = 0;
        for (int i = 0; i < COL_BOARD; i++) {
            if (alienMat[ROW_BOARD - 1][i].getVisibility() == View.VISIBLE && currentPosition == i) {
                hit = 1;

            }
            if (powerMat[ROW_BOARD - 1][i].getVisibility() == View.VISIBLE && currentPosition == i) {
                hit = 2;

            }

            alienMat[ROW_BOARD - 1][i].setVisibility(View.INVISIBLE);
            powerMat[ROW_BOARD - 1][i].setVisibility(View.INVISIBLE);

        }
        return hit;
    }

    private void refreshUI() {
        int state = checkCollision();

        if (gm.isLose() && !gameOverFlag) {
            gm.gameEnded(longitude,latitude);
           gameOverFlag=true;
            openScoreScreen();

        }
        if(!gameOverFlag)
                gm.updateCollision(this, state);
        if (gm.getHit() > 0 && state == 1 && gm.getHit() <= 3) // change to gm.getHit() != 0
        {
            main_IMG_hearts[main_IMG_hearts.length - gm.getHit()].setVisibility(View.INVISIBLE);
        } else {
            main_LBL_score.setText("" + gm.getScore());
        }
    }

    private void openScoreScreen() {
        Intent scoreIntent = new Intent(MainActivity.this, ScoreActivity.class);
        startActivity(scoreIntent);
        finish();
    }


    private void alienDownwards() {
        for (int i = ROW_BOARD - 1; i >= 0; i--) {
            for (int j = 0; j < COL_BOARD; j++) {
                if (alienMat[i][j].getVisibility() == View.VISIBLE) {
                    alienMat[i][j].setVisibility(View.INVISIBLE);
                    alienMat[i + 1][j].setVisibility(View.VISIBLE);
                }
                if (powerMat[i][j].getVisibility() == View.VISIBLE) {
                    powerMat[i][j].setVisibility(View.INVISIBLE);
                    powerMat[i + 1][j].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void generateRow() {
        Random r = new Random();
        int rNum1 = r.nextInt(COL_BOARD);
        int rNum2 = r.nextInt(COL_BOARD);
        if (rNum1 == rNum2)
            powerMat[0][rNum2].setVisibility(View.VISIBLE);
        else
            alienMat[0][rNum1].setVisibility(View.VISIBLE);
    }

    private void initStepDetector() {

        if (sensorFlag) {
            stepDetector = new StepDetector(this, new StepCallback() {
                @Override
                public void stepX() {

                    if (currentPosition != COL_BOARD - 1 || currentPosition != 0) {
                        spaceship[stepDetector.getStepsX()].setVisibility(View.VISIBLE);
                        spaceship[currentPosition].setVisibility(View.INVISIBLE);
                        currentPosition = stepDetector.getStepsX();
                    }
                }

            });

            rButton.setVisibility(View.INVISIBLE);
            lButton.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        SignalGenerator.init(this);

        gm = new GameManager(main_IMG_hearts.length);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });



        Glide
                .with(this)
                .load("https://cdn.wallpapersafari.com/37/18/VDax80.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(main_IMG_background);

        startView();

        rButton.setOnClickListener(view -> moveSpaceshipRight());
        lButton.setOnClickListener(view -> moveSpaceshipLeft());
        Intent intentSpeed = getIntent();
        if (intentSpeed != null) {
           DELAY = intentSpeed.getIntExtra(MainActivity.KEY_DELAY, 1000);
        }

        Intent intentSensor = getIntent();
        if (intentSensor != null) {
            sensorFlag = intentSpeed.getBooleanExtra(MainActivity.KEY_SENSOR,false);
        }

        initStepDetector();
        handler.postDelayed(runnable,DELAY);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorFlag)
            stepDetector.start();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorFlag)
            stepDetector.stop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
    private void startView()
    {
        spaceship[0].setVisibility(View.INVISIBLE);
        spaceship[1].setVisibility(View.INVISIBLE);
        spaceship[3].setVisibility(View.INVISIBLE);
        spaceship[4].setVisibility(View.INVISIBLE);

    }
    private void moveSpaceshipRight() {
      if(currentPosition != COL_BOARD-1) {
          spaceship[currentPosition + 1].setVisibility(View.VISIBLE);
          spaceship[currentPosition].setVisibility(View.INVISIBLE);
          currentPosition++;
      }
    }

    private void moveSpaceshipLeft() {

        if(currentPosition !=0) {
            spaceship[currentPosition - 1].setVisibility(View.VISIBLE);
            spaceship[currentPosition].setVisibility(View.INVISIBLE);
            currentPosition--;
        }
    }

    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        main_LBL_score = findViewById(R.id.main_LBL_score);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
        rButton = findViewById(R.id.fab_right);
        lButton = findViewById(R.id.fab_left);


        spaceship = new ImageView[]{
                findViewById(R.id.spaceship_0),
                findViewById(R.id.spaceship_1),
                findViewById(R.id.spaceship_2),
                findViewById(R.id.spaceship_3),
                findViewById(R.id.spaceship_4)};

        alienMat = new ImageView[][]{
                {findViewById(R.id.alien_0), findViewById(R.id.alien_1), findViewById(R.id.alien_2),findViewById(R.id.alien_3), findViewById(R.id.alien_4)},
                {findViewById(R.id.alien_5), findViewById(R.id.alien_6), findViewById(R.id.alien_7),findViewById(R.id.alien_8), findViewById(R.id.alien_9)},
                {findViewById(R.id.alien_10), findViewById(R.id.alien_11), findViewById(R.id.alien_12),findViewById(R.id.alien_13), findViewById(R.id.alien_14)},
                {findViewById(R.id.alien_15), findViewById(R.id.alien_16), findViewById(R.id.alien_17),findViewById(R.id.alien_18), findViewById(R.id.alien_19)},
                {findViewById(R.id.alien_20), findViewById(R.id.alien_21), findViewById(R.id.alien_22),findViewById(R.id.alien_23), findViewById(R.id.alien_24)},
                {findViewById(R.id.alien_25), findViewById(R.id.alien_26), findViewById(R.id.alien_27),findViewById(R.id.alien_28), findViewById(R.id.alien_29)}
        };


        powerMat = new ImageView[][]{
                {findViewById(R.id.power_0), findViewById(R.id.power_1), findViewById(R.id.power_2),findViewById(R.id.power_3), findViewById(R.id.power_4)},
                {findViewById(R.id.power_5), findViewById(R.id.power_6), findViewById(R.id.power_7),findViewById(R.id.power_8), findViewById(R.id.power_9)},
                {findViewById(R.id.power_10), findViewById(R.id.power_11), findViewById(R.id.power_12),findViewById(R.id.power_13), findViewById(R.id.power_14)},
                {findViewById(R.id.power_15), findViewById(R.id.power_16), findViewById(R.id.power_17),findViewById(R.id.power_18), findViewById(R.id.power_19)},
                {findViewById(R.id.power_20), findViewById(R.id.power_21), findViewById(R.id.power_22),findViewById(R.id.power_23), findViewById(R.id.power_24)},
                {findViewById(R.id.power_25), findViewById(R.id.power_26), findViewById(R.id.power_27),findViewById(R.id.power_28), findViewById(R.id.power_29)}

        };

    }

}