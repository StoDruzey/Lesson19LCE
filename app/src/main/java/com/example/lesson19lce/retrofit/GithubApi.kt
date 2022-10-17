package com.example.lesson19lce.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<GithubUser>
}