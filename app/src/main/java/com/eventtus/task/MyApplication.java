package com.eventtus.task;

import android.app.Application;
import android.content.Context;

import com.eventtus.task.helpers.AppSingleton;
import com.eventtus.task.helpers.Constants;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Habiba.Khalid on 11/2/2016.
 */
public class MyApplication extends Application {

    private static Context mAppContext;
    private static AppSingleton mAppSingleton;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = this;


        // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public synchronized static void setAppSingleton(AppSingleton appSingleton) {
        MyApplication.mAppSingleton = appSingleton;
    }

    public static AppSingleton getAppSingleton() {
        if (mAppSingleton == null) {
            mAppSingleton = new AppSingleton();
        }
        return mAppSingleton;
    }


}
