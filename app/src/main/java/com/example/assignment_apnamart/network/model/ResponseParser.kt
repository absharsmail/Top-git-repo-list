package com.example.assignment_apnamart.network.model

import com.google.gson.annotations.SerializedName

data class ResponseParser(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = null,

    @SerializedName("total_count")
    var totalCount: Long? = null,

    @SerializedName("items")
    var items: List<GithubRepo>? = null
)