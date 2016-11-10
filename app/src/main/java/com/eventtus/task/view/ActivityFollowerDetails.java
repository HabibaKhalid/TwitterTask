package com.eventtus.task.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.eventtus.task.MyApplication;
import com.eventtus.task.R;
import com.eventtus.task.helpers.Utilities;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Habiba.Khalid on 11/10/2016.
 */
public class ActivityFollowerDetails extends AppCompatActivity {

    User user;
    long id;
    String profileImgUrl;
    String profileBackgroundUrl;

    ImageView backgroundImg;
    ImageView profileImg;

    LinearLayout myLayout;

    TextView protectedTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_followerdetails);
        setUpActionBar();

        user = MyApplication.getAppSingleton().getSelectedUser();

        backgroundImg = (ImageView) findViewById(R.id.imgbackground);
        profileImg = (ImageView) findViewById(R.id.imgprofile);
        myLayout = (LinearLayout) findViewById(R.id.tweetview);
        protectedTweets = (TextView) findViewById(R.id.errorView);

        bindImgs(user.profileImageUrl, profileImg);

        if (!user.profileBannerUrl.equals("") && user.profileBannerUrl != null) {
            bindImgs(user.profileBannerUrl, backgroundImg);
        } else {
            backgroundImg.setImageResource(R.drawable.default_background);
        }


        LoadRecentTweets();

    }

    public void LoadRecentTweets() {

        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();
        Call<List<Tweet>> call = statusesService.userTimeline(user.getId(), user.screenName, 10, null, null, null, null, null, null);
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                //Do something with result
                for (Tweet tweet : result.data) {
                    myLayout.addView(new TweetView(ActivityFollowerDetails.this, tweet));
                }

            }

            public void failure(TwitterException exception) {
                //Do something on failure
//              401  This account's Tweets are protected.

                protectedTweets.setVisibility(View.VISIBLE);

            }
        });
    }

    public void bindImgs(String url, final ImageView imgView) {

        if (url != null && !url.equals("")) {

            Glide.with(this)
                    .load(url)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (this != null) {
                                imgView.setImageBitmap(resource);
                                resource = null;
                            }
                        }
                    });

        }
    }

    public void setUpActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
