package com.androidbegin.jsonparsetutorial;

import com.hannesdorfmann.mosby.mvp.MvpView;

interface SplashView extends MvpView {
    boolean getUser();

    void createGifImageView();

    void initialize();

}
