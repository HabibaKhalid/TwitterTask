package com.eventtus.task.helpers;

import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Habiba.Khalid on 11/6/2016.
 */
public class AppSingleton {

    TwitterSession session;

    public TwitterSession getSession() {
        return session;
    }

    public synchronized void setSession(TwitterSession session) {
        this.session = session;
    }


}
