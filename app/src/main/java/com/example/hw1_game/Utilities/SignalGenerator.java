package com.example.hw1_game.Utilities;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.hw1_game.R;

public class SignalGenerator {

    private static SignalGenerator instance;
    private Context context;
    private static Vibrator vibrator;

    private  static SoundPool soundPool;
    private static int mSoundId1;
    private static int mSoundId2;



    private SignalGenerator(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SignalGenerator(context);
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .build();
                soundPool = new SoundPool.Builder()
                        .setMaxStreams(2)
                        .setAudioAttributes(audioAttributes)
                        .build();
            } else
                soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
            mSoundId1= soundPool.load(context, R.raw.explosion, 1);
            mSoundId2= soundPool.load(context, R.raw.charged, 1);
        }
    }

    public static SignalGenerator getInstance() {
        return instance;
    }

    public void toast(String text,int length){
        Toast
                .makeText(context,text,length)
                .show();
    }

    public void vibrate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(500);
        }
    }

    public void playSound(int id)
    {
        if(id ==1)
             soundPool.play(mSoundId1, 1, 1, 0, 0, 1);
        else
            soundPool.play(mSoundId2,1, 1, 0, 0, 1);
    }
}