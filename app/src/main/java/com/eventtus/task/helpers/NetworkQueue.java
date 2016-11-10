package com.eventtus.task.helpers;

import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eventtus.task.view.MyTwitterApiClient;
import com.twitter.sdk.android.core.models.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Habiba.Khalid on 11/7/2016.
 */
public class NetworkQueue {

    private static final String HEADER_AUTHORIZATION = "Authorization";


    static int socketTimeout = 30000;

    private static RequestQueue queue = null;

    private NetworkQueue() {

    }

    public static RequestQueue getInstance(Context context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    public static void GetFollowers(final String token, String cursor,  String count, Response.Listener<String> responseListner, Response.ErrorListener errorListener) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constants.servicesScheme)
                .authority(Constants.servicesAuthority);

        builder.appendEncodedPath(Constants.followersPath);
//        if (combine != null && !combine.equals(""))
            builder.appendQueryParameter("cursor", cursor);
            builder.appendQueryParameter("count", count);
        String url = builder.build().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListner, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (params == null) params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put(HEADER_AUTHORIZATION, token);


                return params;
            }
        };

        stringRequest.setTag(Constants.followersTag);

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

        queue.add(stringRequest);
    }

//    GET https://api.twitter.com/1.1/followers/list.json?cursor=-1&screen_name=twitterdev&skip_status=true&include_user_entities=false


}
