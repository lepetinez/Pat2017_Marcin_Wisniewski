package com.example.pc.zadanie1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    private static final String PREFS_NAME = "mypref";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText emailEditText = (EditText) findViewById(R.id.loginEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail(emailEditText.getText().toString())) {
                    emailEditText.setError("invalid E-mail !");
                    emailEditText.requestFocus();
                } else if (!validatePassword(passwordEditText.getText().toString())) {
                    passwordEditText.setError("invalid password !");
                    passwordEditText.requestFocus();

                } else {
                    rememberMe(emailEditText.getText().toString(), passwordEditText.getText().toString());
                    showLogout(emailEditText.getText().toString());
                }
            }
        });
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    private boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void rememberMe(String user, String password) {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, user)
                .putString(PREF_PASSWORD, password)
                .apply();
    }

    private void showLogout(String username) {
        Intent intent = new Intent(this, LoggedActivity.class);
        intent.putExtra("user", username);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}