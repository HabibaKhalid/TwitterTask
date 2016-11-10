package com.eventtus.task.view;

import com.eventtus.task.data.Followers;
import com.eventtus.task.data.Tweets;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
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

    public FollowersService getFollowersService() {
        return getService(FollowersService.class);
    }

    public RecentTweetsService getRecentTweetsService() {
        return getService(RecentTweetsService.class);
    }

}



interface FollowersService {
    @GET("/1.1/followers/list.json")
    Call<Followers> list(@Query("user_id") long id, @Query("cursor") long cursor);
}

interface RecentTweetsService {
    @GET("/1.1/statuses/user_timeline.json")
    Call<Tweet> list(@Query("user_id") long id, @Query("count") int cursor);

}

