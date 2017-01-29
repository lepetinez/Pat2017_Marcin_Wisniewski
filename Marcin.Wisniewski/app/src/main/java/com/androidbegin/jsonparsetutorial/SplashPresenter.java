package com.androidbegin.jsonparsetutorial;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;


 class SplashPresenter extends MvpBasePresenter<SplashView> {

    void setAppropriateActivity(){
       if (getView().getUser()) {
           return;
       } else {
           getView().createGifImageView();
           getView().initialize();
       }

   }
}
