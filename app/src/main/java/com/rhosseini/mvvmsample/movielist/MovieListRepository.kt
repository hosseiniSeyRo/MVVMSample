package com.rhosseini.mvvmsample.movielist

import androidx.lifecycle.MutableLiveData
import com.rhosseini.adakreqres.model.webService.model.ResponseWrapper
import com.rhosseini.mvvmsample.network.MovieService
import com.rhosseini.mvvmsample.network.RetrofitClient
import com.rhosseini.mvvmsample.network.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object MovieListRepository {

    private var movieListRepository: MovieListRepository? = null
    private val movieService = RetrofitClient.createService(MovieService::class.java)

    private val _allMovieLiveData = MutableLiveData<ResponseWrapper<MovieResponse>>()
    val allMovieLiveData = _allMovieLiveData

    fun getAllMovie(page: Int) {
        _allMovieLiveData.value = ResponseWrapper.loading()

        movieService.getAllMovies(page).enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _allMovieLiveData.value = ResponseWrapper.error(t.message ?: "Unknown error")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    _allMovieLiveData.value = ResponseWrapper.success(response.body())
                } else {
                    _allMovieLiveData.value =
                        ResponseWrapper.error(response.message())
                }
            }
        })
    }
}