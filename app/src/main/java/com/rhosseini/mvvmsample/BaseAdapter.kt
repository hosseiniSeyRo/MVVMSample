package com.rhosseini.mvvmsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.rhosseini.mvvmsample.BR

abstract class BaseAdapter<T>(private val itemClickListener: OnItemClickListener<T>) :
    RecyclerView.Adapter<BaseAdapter<T>.MyViewHolder>() {

    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItemForPosition(position)

        holder.itemView.setOnClickListener { itemClickListener.onItemClick(item) }

        holder.bind(item)
    }

    abstract override fun getItemCount(): Int

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    abstract fun getItemForPosition(position: Int): T

    abstract fun getLayoutIdForPosition(position: Int): Int

    inner class MyViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: T) {
            binding.setVariable(BR.obj, obj)
            binding.executePendingBindings()
        }
    }
}