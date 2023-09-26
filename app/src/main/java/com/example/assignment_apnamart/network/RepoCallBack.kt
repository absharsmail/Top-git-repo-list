package com.example.assignment_apnamart.network

import com.example.assignment_apnamart.network.model.ResponseParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoCallBack(val iRepoSvc: IRepoSvc): Callback<ResponseParser> {
    override fun onResponse(call: Call<ResponseParser>, response: Response<ResponseParser>) {
        if (response.body()?.items!!.isNotEmpty())
            iRepoSvc.onRepoSuccess(response.body())
        else
            iRepoSvc.noData()
    }

    override fun onFailure(call: Call<ResponseParser>, t: Throwable) {
        iRepoSvc.failure(t.message, 1, t.cause)
    }
}