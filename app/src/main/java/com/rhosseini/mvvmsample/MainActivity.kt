package com.rhosseini.mvvmsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.replace
import com.rhosseini.mvvmsample.movielist.MovieListFragment
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.rhosseini.mvvmsample.addMovie.AddMovieFragment


interface FragmentInteractionListener {
    fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String?
    )

    fun onAddClicked()
}

class MainActivity : AppCompatActivity(), FragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieListFragment = MovieListFragment()
        addFragment(R.id.frame_layout, movieListFragment, movieListFragment.tag)
    }

    override fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String?
    ) {
        supportFragmentManager.commit {
            add(containerViewId, fragment, fragmentTag)
        }
    }

    override fun onAddClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_layout, AddMovieFragment(), AddMovieFragment().tag)
            addToBackStack(null)
        }
    }

}
