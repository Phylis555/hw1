package com.example.hw1_game.Model;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1_game.Fragments.ListFragment;
import com.example.hw1_game.Fragments.MapFragment;
import com.example.hw1_game.Interfaces.CallBack_ZoomClick;
import com.example.hw1_game.R;


public class ScoreActivity  extends AppCompatActivity {



    private ListFragment listFragment;
    private MapFragment mapFragment;

    CallBack_ZoomClick callBack_zoomClick = new CallBack_ZoomClick() {
        @Override
        public void locationPin(double latitude, double longitude) {
            showUserLocation(latitude, longitude);
        }
    };

    private void showUserLocation(double latitude, double longitude) {
        mapFragment.zoomInOnLocation(latitude, longitude);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);

        initFragments();
        beginTransactions();
    }

    private void initFragments() {
        listFragment = new ListFragment();
        listFragment.setCallBack(callBack_zoomClick);
        mapFragment = new MapFragment();
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }


}
