package com.eventtus.task.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.eventtus.task.helpers.Constants;
import com.eventtus.task.helpers.TwitterSessionManager;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import com.eventtus.task.R;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class ActivityLogin extends AppCompatActivity {

    TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpActionBar();
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(callBack);
    }

    Callback<TwitterSession> callBack = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            // Do something with result, which provides a TwitterSession for making API calls

            TwitterSessionManager.loginOrUpdateUser(result.data);

            Intent intent = new Intent(ActivityLogin.this, ActivityFollowers.class);
            startActivity(intent);

        }

        @Override
        public void failure(TwitterException exception) {
            // Do something on failure

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
        TwitterSessionManager.getSession();


    }

    public void setUpActionBar()
    {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.loginActivityName);

    }
}