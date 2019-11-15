package com.rhosseini.mvvmsample.movielist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rhosseini.adakreqres.model.webService.model.ResponseWrapper
import com.rhosseini.mvvmsample.BaseAdapter
import com.rhosseini.mvvmsample.FragmentInteractionListener
import com.rhosseini.mvvmsample.MainActivity

import com.rhosseini.mvvmsample.R
import com.rhosseini.mvvmsample.databinding.FragmentMovieListBinding
import com.rhosseini.mvvmsample.network.model.Movie
import com.rhosseini.mvvmsample.network.model.MovieResponse


class MovieListFragment : Fragment() {
    private lateinit var fragmentInterface: FragmentInteractionListener
    private lateinit var binding: FragmentMovieListBinding
    private val viewModel by viewModels<MovieListViewModel>()
    private lateinit var movieAdapter: MovieAdapter
    private val visibleThreshold = 2
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentInterface = context as FragmentInteractionListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MovieAdapter(object : BaseAdapter.OnItemClickListener<Movie> {
            override fun onItemClick(item: Movie) {
                Toast.makeText(activity, "id: " + item.id, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.fab.setOnClickListener{
            fragmentInterface.onAddClicked()
        }
        binding.swipeRefresh?.setOnRefreshListener {
            movieAdapter.removeAllData()
            viewModel.refreshAllUsers()
            binding.swipeRefresh.isRefreshing = false
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMovieRecyclerView()
        observeData()
        viewModel.getAllMovies()
    }

    private fun observeData() {
        viewModel.allMovieLiveData.observe(this, Observer { consumeResponse(it) })
        viewModel.errorText.observe(
            this,
            Observer { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() })
    }

    private fun consumeResponse(it: ResponseWrapper<MovieResponse>) {
        when (it.status) {
            ResponseWrapper.Status.LOADING -> {
                binding.loadingLayout.visibility =
                    if (movieAdapter.itemCount == 0) View.VISIBLE else View.GONE
                viewModel.isLoading.value = true
            }
            ResponseWrapper.Status.SUCCESS -> {
                movieAdapter.addData(it.data!!.movieList)
                binding.emptyLayout.visibility =
                    if (movieAdapter.itemCount == 0) View.VISIBLE else View.GONE
                binding.loadingLayout.visibility = View.GONE
            }
        }
    }

    private fun initMovieRecyclerView() {
        val rvLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerMovie.layoutManager = rvLayoutManager

        binding.recyclerMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                totalItemCount = rvLayoutManager.itemCount
                lastVisibleItem = rvLayoutManager.findLastVisibleItemPosition()

                if (viewModel.isLoading.value != true && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    viewModel.getAllMovies()
                }
            }
        })
        binding.recyclerMovie.adapter = movieAdapter
    }
}
