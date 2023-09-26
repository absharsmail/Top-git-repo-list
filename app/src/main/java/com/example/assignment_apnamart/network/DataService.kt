package com.example.assignment_apnamart.network

import com.example.assignment_apnamart.network.api.Api
import com.example.assignment_apnamart.network.model.ResponseParser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DataService: NetworkManager {

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    private val getRetrofit by lazy {
        getRetrofit(BASE_URL, Api::class.java)
    }

    override fun fetchData(iRepoSvc: IRepoSvc) {
        val call: Call<ResponseParser> = getRetrofit.fetchRepoData("stars")
        call.enqueue(RepoCallBack(iRepoSvc))
    }

    private fun getRetrofit(baseURL: String, api: Class<Api>): Api {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(api)
    }
}