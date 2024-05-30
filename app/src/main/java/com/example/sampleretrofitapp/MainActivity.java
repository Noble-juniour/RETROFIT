package com.example.sampleretrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private TextView idDetails,userId,title,body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();

        //retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitClient retrofitClient = retrofit.create(RetrofitClient.class);
        Call<Post> getSinglePost = retrofitClient.getSinglePost();
        getSinglePost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d(TAG, "onResponse: onGettingSinglePost: code: " + response.code());
                Post post = response.body();
                title.setText(post.getTitle());
                body.setText(post.getBody());
                userId.setText(String.valueOf(post.getUserid()));
                idDetails.setText(String.valueOf(post.getId()));

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d(TAG, "onFailure: onGettingSinglePost: t:" + t.getMessage());

            }
        });

        Call<List<Post>>  getAllPosts = retrofitClient.getAllPosts();
        getAllPosts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d(TAG, "onResponse: code : " + response.code());
                Log.d(TAG, "onResponse: second post title : " + response.body().get(1).getTitle());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure: onGettingAllPosts: t:" + t.getMessage() );

            }
        });

        Call<Post> sendPost = retrofitClient.sendpost(new Post(2, 23, "hawayu","yeeeloo"));
        sendPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d(TAG, "onResponse: code:  " + response.code());
                Log.d(TAG, "onResponse: post" + response.body().toString() );
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d(TAG, "onFailure: onSendingPosts: t:" + t.getMessage());

            }
        });

    }
    private void initViews()
    {
        Log.d(TAG, "initViews: started");
        idDetails = (TextView) findViewById(R.id.idDetails);
        userId = (TextView) findViewById(R.id.userId);
        title = (TextView) findViewById(R.id.title);
        body = (TextView) findViewById(R.id.body);
    }
}