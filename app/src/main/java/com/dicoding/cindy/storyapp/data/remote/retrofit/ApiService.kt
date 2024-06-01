package com.dicoding.cindy.storyapp.data.remote.retrofit

import com.dicoding.cindy.storyapp.data.remote.response.login.LoginResponse
import com.dicoding.cindy.storyapp.data.remote.response.signup.SignupResponse
import com.dicoding.cindy.storyapp.data.remote.response.story.AddNewStoryResponse
import com.dicoding.cindy.storyapp.data.remote.response.story.GetAllStoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignupResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): GetAllStoriesResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location : Int = 1,
    ): GetAllStoriesResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") lon: RequestBody? = null,
    ): AddNewStoryResponse

}