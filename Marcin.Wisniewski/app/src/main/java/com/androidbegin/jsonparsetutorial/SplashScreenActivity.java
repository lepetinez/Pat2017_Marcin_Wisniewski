package com.androidbegin.jsonparsetutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.felipecsl.gifimageview.library.GifImageView;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreenActivity extends MvpActivity<SplashView, SplashPresenter> implements SplashView {

    private static final String PREFS_NAME = "mypref";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final int THREAD_LENGTH = 5000;
    private static final String USER = "user";
    private static final String SAMOLOT = "samolocik.gif";
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
        getPresenter().setAppropriateActivity();
    }

    @NonNull
    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    public void onBackPressed() {
        super.onBackPressed();
        RUNNABLE_HANDLER.removeCallbacks(RUNNABLE);
    }

    public void initialize() {
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

    public boolean getUser() {
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
        intent.putExtra(USER, username);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void createGifImageView() {
        GifImageView gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        try {
            InputStream inputStream = getAssets().open(SAMOLOT);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
            gifImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}