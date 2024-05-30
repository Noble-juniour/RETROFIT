package com.example.sampleretrofitapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitClient {

    @GET("posts/1")
    Call<Post> getSinglePost();

   @GET("posts")
    Call<List<Post>> getAllPosts();
   @POST("posts")
    Call<Post> sendpost(@Body Post post);

}
