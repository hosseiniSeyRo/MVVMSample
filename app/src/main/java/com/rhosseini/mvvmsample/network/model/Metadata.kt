package com.rhosseini.mvvmsample.network.model

import com.google.gson.annotations.SerializedName

class Metadata(
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("page_count") val pageCount: Int,
    @SerializedName("total_count") val totalCount: Int
)