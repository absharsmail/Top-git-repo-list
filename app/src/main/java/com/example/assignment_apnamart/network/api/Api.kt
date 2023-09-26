package com.example.assignment_apnamart.network.api

import com.example.assignment_apnamart.network.model.ResponseParser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/search/repositories")
    fun fetchRepoData(@Query("q") q: String): Call<ResponseParser>
}