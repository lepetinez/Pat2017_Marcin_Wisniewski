package com.example.pc.zadanie1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;

import com.felipecsl.gifimageview.library.GifImageView;

import android.app.Activity;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import static com.example.pc.zadanie1.R.id.gifImageView;

public class SplashScreenActivity extends Activity {

    private boolean pauseFlag = false;
    private MediaPlayer flightSound;
    private GifImageView gifImageView;
    private Runnable runnable;
    private Handler runnableHandler;
    private long timeOfPause;
    private long timeOfCreate;
    private final static int threadLength = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeOfCreate = System.currentTimeMillis();
        setContentView(R.layout.activity_splash_screen);
        gifImageView = (GifImageView) findViewById(R.id.gifImageView);

        try {

            InputStream inputStream = getAssets().open("samolocik.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);

            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
            gifImageView.setScaleType(ImageView.ScaleType.FIT_XY);

            flightSound = MediaPlayer.create(getBaseContext(), R.raw.samolotprogram);
            flightSound.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        initialize();

    }

    public void onBackPressed() {

        super.onBackPressed();
        runnableHandler.removeCallbacks(runnable);
        flightSound.release();
    }

    private void initialize() {

        runnableHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (!pauseFlag) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        };
        runnableHandler.postDelayed(runnable, threadLength);
    }

    protected void onPause() {

        flightSound.release();
        timeOfPause = System.currentTimeMillis();
        super.onPause();
        pauseFlag = true;
        runnableHandler.removeCallbacks(runnable);
    }

    protected void onRestart() {

        super.onRestart();
        pauseFlag = false;
        flightSound.release();
        runnableHandler.postDelayed(runnable, timeOfCreate - timeOfPause);
    }
}