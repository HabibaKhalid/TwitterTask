package com.eventtus.task.view;

import com.eventtus.task.data.Followers;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Habiba.Khalid on 11/7/2016.
 */
public class MyTwitterApiClient extends TwitterApiClient {

    public MyTwitterApiClient(TwitterSession session) {
        super(session);
    }

    /**
     * Provide CustomService with defined endpoints
     */
    public CustomService getCustomService() {
        return getService(CustomService.class);
    }

    public FollowersService getFollowersService() {
        return getService(FollowersService.class);
    }


}
interface CustomService {
    @GET("/1.1/users/show.json")
    Call<User> show(@Query("user_id") long id);

}

interface FollowersService {
    @GET("/1.1/followers/list.json")
    Call<Followers> list(@Query("user_id") long id, @Query("cursor") long cursor);
}

// example users/show service endpoint

