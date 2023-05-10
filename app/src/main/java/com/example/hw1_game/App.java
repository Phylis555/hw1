package com.example.hw1_game;

import android.app.Application;

import com.example.hw1_game.Utilities.MySP;

public class App  extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        MySP.init(this);
       // SignalGenerator.init(this);
    }
}
