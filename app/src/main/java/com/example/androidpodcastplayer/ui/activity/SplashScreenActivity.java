package com.example.androidpodcastplayer.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class SplashScreenActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.launch(SplashScreenActivity.this);
        finish();
    }


}
