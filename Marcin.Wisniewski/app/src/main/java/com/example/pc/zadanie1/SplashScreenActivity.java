package com.example.pc.zadanie1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;

import com.felipecsl.gifimageview.library.GifImageView;

import android.app.Activity;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreenActivity extends Activity {

    private static final String PREFS_NAME = "mypref";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final int THREAD_LENGTH = 5000;
    private boolean PAUSE_FLAG = false;
    private Runnable RUNNABLE;
    private Handler RUNNABLE_HANDLER;
    private long TIME_OF_PAUSE;
    private long TIME_OF_CREATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TIME_OF_CREATE = System.currentTimeMillis();
        setContentView(R.layout.activity_splash_screen);
        if (getUser()) {
            return;
        } else {
            GifImageView gifImageView = createGifImageView();
            initialize();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        RUNNABLE_HANDLER.removeCallbacks(RUNNABLE);
    }

    private void initialize() {
        RUNNABLE_HANDLER = new Handler();
        RUNNABLE = new Runnable() {
            @Override
            public void run() {
                if (!PAUSE_FLAG) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        };
        RUNNABLE_HANDLER.postDelayed(RUNNABLE, THREAD_LENGTH);
    }

    protected void onPause() {
        TIME_OF_PAUSE = System.currentTimeMillis();
        super.onPause();
        PAUSE_FLAG = true;
        RUNNABLE_HANDLER.removeCallbacks(RUNNABLE);
    }

    protected void onRestart() {
        super.onRestart();
        PAUSE_FLAG = false;
        RUNNABLE_HANDLER.postDelayed(RUNNABLE, TIME_OF_CREATE - TIME_OF_PAUSE);
    }

    private boolean getUser() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);
        if (username != null || password != null) {
            showLogout(username);
            finish();
            return true;
        }
        return false;
    }

    private void showLogout(String username) {
        Intent intent = new Intent(this, LoggedActivity.class);
        intent.putExtra("user", username);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private GifImageView createGifImageView() {
        GifImageView gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        try {
            InputStream inputStream = getAssets().open("samolocik.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
            gifImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gifImageView;
    }
}