package com.eventtus.task.helpers;

import com.eventtus.task.MyApplication;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Habiba.Khalid on 11/6/2016.
 */
public class TwitterSessionManager {

    private static final String KEY_TWITTER_SESSION = "user_pref";
    private static boolean SAVE_USER_ONCE_LOGGED_IN_LOCALLY = true;



    public static boolean isUserLoggedIn() {
        return getSession() != null || canLoadFromPref();
    }

    public static TwitterSession getSession() {
        return MyApplication.getAppSingleton() != null ? MyApplication.getAppSingleton().getSession() : null;
    }

    public static boolean canLoadFromPref() {
        boolean loaded = false;
        if(SAVE_USER_ONCE_LOGGED_IN_LOCALLY) {
            String json = new StringPreference(KEY_TWITTER_SESSION).readPreferences(MyApplication.getAppContext(), null);
            TwitterSession session = json != null ? new Gson().fromJson(json, TwitterSession.class) : null;
            loaded = session != null;
            if(loaded) {
                loginOrUpdateUser(session);
            }
        }
        return loaded;
    }

    public static void loginOrUpdateUser(TwitterSession session) {
        MyApplication.getAppSingleton().setSession(session);
        if(SAVE_USER_ONCE_LOGGED_IN_LOCALLY) {
            new StringPreference(KEY_TWITTER_SESSION).savePreferences(MyApplication.getAppContext(), new Gson().toJson(session));
        }
    }
}
