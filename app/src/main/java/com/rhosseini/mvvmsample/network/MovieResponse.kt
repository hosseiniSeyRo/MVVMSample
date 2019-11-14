package com.rhosseini.mvvmsample.network

import com.google.gson.annotations.SerializedName

class MovieResponse(
    @SerializedName("data") val movieList: Array<Movie>,
    @SerializedName("metadata") val metaData: Metadata
)