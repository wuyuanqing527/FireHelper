package com.wyq.firehelper.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        startActivity(new Intent(this,MainActivity.class));
//        MainActivity.lunch(this);
        finish();
    }
}
