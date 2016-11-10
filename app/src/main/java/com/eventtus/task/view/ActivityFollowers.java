package com.eventtus.task.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.eventtus.task.MyApplication;
import com.eventtus.task.R;
import com.eventtus.task.adapters.RvUsersAdapter;
import com.eventtus.task.data.Followers;
import com.eventtus.task.helpers.Constants;
import com.eventtus.task.helpers.NetworkQueue;
import com.eventtus.task.helpers.TwitterSessionManager;
import com.eventtus.task.helpers.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Habiba.Khalid on 11/7/2016.
 */
public class ActivityFollowers extends AppCompatActivity{

    RvUsersAdapter rvUserAdapter;


    ProgressBar progressBar;
    RecyclerView rvList;

    LinearLayoutManager linearManager;

    List<User> allFollowersList = new ArrayList<>();
    int mTotalFollowersCount = Integer.MAX_VALUE;

    int pageSize = 20;

    int visibleItemCount;
    int totalItemCount;
    int pastVisiblesItems;

    public boolean loading = false;

    RequestQueue queue;

    public static Followers followers;
    public static long cursor = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_followers);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rvList = (RecyclerView) findViewById(R.id.recyclerView);
        linearManager = new LinearLayoutManager(this);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearManager);

        rvList.addOnScrollListener(entriesRecyclerViewOnScrollListener);

        Utilities.ShowProgressBar(progressBar);

        setUpActionBar();


        loadMoreFollowers();
    }


    public void loadMoreFollowers() {
        if (mod(allFollowersList.size(), pageSize) == 0 && allFollowersList.size() < mTotalFollowersCount) {
            long nextPage = ((allFollowersList.size() / pageSize) - 1);
            Log.e("page index", Long.toString(nextPage));
            if (nextPage > -1)//not first load
                loading = true;


            MyTwitterApiClient client = new MyTwitterApiClient(TwitterSessionManager.getSession());
            FollowersService service = client.getFollowersService();
            long id = TwitterSessionManager.getSession().getUserId();
            Call<Followers> call = service.list(id, cursor);
            call.enqueue(getFollowersCallback);

        }
    }

    Callback<Followers> getFollowersCallback = new Callback<Followers>() {
        @Override
        public void success(Result<Followers> result) {
            Utilities.HideProgressBar(progressBar);
            cursor = result.data.getNext_cursor();

            try {
                List<User> followersList = result.data.getUsers();
                if (allFollowersList == null)
                    allFollowersList = new ArrayList<>();
                allFollowersList.addAll(followersList);

                if (rvUserAdapter == null) {
                    rvUserAdapter = new RvUsersAdapter(allFollowersList, getApplicationContext(), clickListner);
                    rvList.setAdapter(rvUserAdapter);
                } else
                    rvUserAdapter.notifyDataSetChanged();

                loading = false;

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), getString(R.string.errorMsg), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void failure(TwitterException exception) {
            loading = false;

        }
    };


    RvUsersAdapter.OnItemClickListener clickListner = new RvUsersAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(User item) {

            MyApplication.getAppSingleton().setSelectedUser(item);
            Intent intent = new Intent(ActivityFollowers.this, ActivityFollowerDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }
    };

    RecyclerView.OnScrollListener entriesRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0) //check for scroll down
            {
                visibleItemCount = linearManager.getChildCount();
                totalItemCount = linearManager.getItemCount();
                pastVisiblesItems = linearManager.findFirstVisibleItemPosition();

                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        //Log.v("...", "Last Item Wow !");
                        //Do pagination.. i.e. fetch new data

                        loadMoreFollowers();


                    }
                }
            }
        }

    };


    private int mod(int x, int y) {
        int result = x % y;
        return result < 0 ? result + y : result;
    }

    public void setUpActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.followersActivityName);

    }


    @Override
    public void onPause() {
        super.onPause();
    }


}
