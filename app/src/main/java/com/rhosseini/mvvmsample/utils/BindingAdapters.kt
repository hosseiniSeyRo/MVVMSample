package com.rhosseini.mvvmsample.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter(value = ["bind:imageUrl", "bind:placeHolder"], requireAll = false)
fun loadImageByUrl(view: ImageView, url: String, placeHolder: Drawable) {
//    Picasso.get().load(url).into(view)
    val requestCreator = Picasso.get().load(url)
    if (placeHolder != null) {
        requestCreator.placeholder(placeHolder)
    }
    requestCreator.into(view)

    //by glide
//    Glide
//        .with(view.context)
//        .load(url)
//        .centerCrop()
//        .placeholder(placeHolder)
//        .into(view)
}
