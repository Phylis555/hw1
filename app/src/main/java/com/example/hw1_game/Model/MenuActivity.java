package com.example.hw1_game.Model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1_game.R;
import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {

    private MaterialButton play_btn;
    private ToggleButton speedToggle;
    private ToggleButton sensorToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        findViews();
        speedToggle.setTextOff("Slow");
        speedToggle.setTextOn("Fast");
        sensorToggle.setTextOff("2 Buttons(Arrows)");
        sensorToggle.setTextOn("Tilt");
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);

                if (speedToggle.isChecked()) {
                    intent.putExtra("DELAY", 500);
                } else {
                    intent.putExtra("DELAY", 1000);
                }

                startActivity(intent);


            }
        });
    }

    private void findViews() {
        play_btn = findViewById(R.id.play_btn);
        speedToggle = findViewById(R.id.toggleSpeed);
        sensorToggle = findViewById(R.id.toggleSensor);


        speedToggle.setText("Slow");
        sensorToggle.setText("2 Buttons(Arrows)");

        speedToggle.setTextOff("Slow");
        speedToggle.setTextOn("Fast");
        sensorToggle.setTextOff("2 Buttons(Arrows)");
        sensorToggle.setTextOn("Tilt");


    }

}
