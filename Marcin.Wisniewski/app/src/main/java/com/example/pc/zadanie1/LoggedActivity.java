package com.example.pc.zadanie1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "mypref";
    private String USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        USER = b.getString("user");
        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        nameTextView.setText("Witamy " + USER);
    }

    public void logout(View view) {
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.apply();
        USER = "";
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}