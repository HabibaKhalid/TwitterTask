package com.eventtus.task.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.eventtus.task.R;
import com.eventtus.task.helpers.TwitterSessionManager;

/**
 * Created by Habiba.Khalid on 11/6/2016.
 */
public class ActivitySplash extends Activity{
    private final long SPLASH_DURATION_MILLIS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        lazyLoading();
    }

    //---------------->

    private void lazyLoading() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ActivitySplash.this != null && !isFinishing()) {

                    if (TwitterSessionManager.isUserLoggedIn()) {
                    }
                    else
                    {
                        navigateToLogin();


                    }
                }
            }
        }, SPLASH_DURATION_MILLIS);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }

    private void navigateToFollowers() {
//        Intent intent = new Intent(ActivitySplash.this, NAVIGATE_TO_CLASS);
//        startActivity(intent);
        finish();
    }

}
