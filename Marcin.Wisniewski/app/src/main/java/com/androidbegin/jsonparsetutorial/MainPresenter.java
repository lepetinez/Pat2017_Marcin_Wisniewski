package com.androidbegin.jsonparsetutorial;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

class MainPresenter extends MvpBasePresenter<MainView> {

    void loginButtonClicked(String user, String password) {
        if (MainUsage.validateEmail(user)) {
            getView().getEmailEditText().setError("invalid E-mail !");
            getView().getEmailEditText().requestFocus();
        } else if (!MainUsage.validatePassword(password)) {
            getView().getPasswordEditText().setError("invalid password !");
            getView().getPasswordEditText().requestFocus();

        } else {
            getView().rememberMe(user, password);
            getView().showLogout();
        }
    }
}
