package com.rhosseini.mvvmsample.movielist

import com.rhosseini.mvvmsample.BaseAdapter
import com.rhosseini.mvvmsample.network.model.Movie


class MovieAdapter(itemClickListener: OnItemClickListener<Movie>) :
    BaseAdapter<Movie>(itemClickListener) {

    private val data = mutableListOf<Movie>()

    override fun getItemCount(): Int = data.size

    override fun getItemForPosition(position: Int): Movie = data[position]

    override fun getLayoutIdForPosition(position: Int): Int {
        return com.rhosseini.mvvmsample.R.layout.item_movie
    }

    fun addData(data: Array<Movie>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun removeAllData() {
        this.data.clear()
        notifyDataSetChanged()
    }
}