package com.rhosseini.mvvmsample.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rhosseini.adakreqres.model.webService.model.ResponseWrapper
import com.rhosseini.mvvmsample.network.model.MovieResponse

class MovieListViewModel : ViewModel() {

    private var nextPage: Int = 1
    private var hasNextPage: Boolean = true

    private val _errorText = MutableLiveData<String>()
    val errorText = _errorText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    val allMovieLiveData: LiveData<ResponseWrapper<MovieResponse>> =
        Transformations.map(MovieListRepository.allMovieLiveData) {
            when (it.status) {
                ResponseWrapper.Status.LOADING -> {
                    _isLoading.value = true
                }
                ResponseWrapper.Status.ERROR -> {
                    _isLoading.value = false
                    _errorText.value = it.error
                }
                ResponseWrapper.Status.SUCCESS -> {
                    _isLoading.value = false
                    if (nextPage < it.data!!.metaData.pageCount) {
                        hasNextPage = true
                        nextPage++
                    } else {
                        hasNextPage = false
                    }
                }
            }

            it
        }

    fun getAllMovies() {
        if (hasNextPage)
            MovieListRepository.getAllMovie(nextPage)
    }

    fun refreshAllUsers() {
        nextPage = 1
        hasNextPage = true
        getAllMovies()
    }

}