package com.eventtus.task.helpers;

import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by Habiba.Khalid on 11/6/2016.
 */
public class AppSingleton {

    TwitterSession session;
    private User selectedUser;

    public TwitterSession getSession() {
        return session;
    }

    public synchronized void setSession(TwitterSession session) {
        this.session = session;
    }


    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}
