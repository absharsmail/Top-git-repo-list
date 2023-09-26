package com.example.assignment_apnamart.network

import com.example.assignment_apnamart.network.model.ResponseParser

interface IRepoSvc {
    fun onRepoSuccess(responseParser: ResponseParser?)
    fun failure(message: String?, errorCode: Int, extraParams: Throwable?)
    fun noData()
}