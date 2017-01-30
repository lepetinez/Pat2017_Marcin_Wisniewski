package com.androidbegin.jsonparsetutorial;

import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpView;

interface MainView extends MvpView {
    void rememberMe(String user, String password);

    EditText getEmailEditText();

    EditText getPasswordEditText();

    void showLogout();
}
