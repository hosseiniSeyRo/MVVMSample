package com.rhosseini.mvvmsample.network

import com.rhosseini.mvvmsample.network.model.MovieResponse
import retrofit2.Call
import retrofit2.http.*

interface MovieService {

    @GET("v1/movies")
    fun getAllMovies(@Query("page") page: Int): Call<MovieResponse>

//    @DELETE("/api/users/{id}")
//    fun deleteUser(@Path("id") userId: Int): Call<Void>
//
//    @POST("/api/users")
//    @FormUrlEncoded
//    fun addNewUser(@Field("name") name: String, @Field("job") job: String): Call<AddNewUserResponse>
//
//    @PUT("/api/users/{id}")
//    fun updateUser(@Path("id") id: Int, @Body user: UpdateUserResponse): Call<UpdateUserResponse>
}