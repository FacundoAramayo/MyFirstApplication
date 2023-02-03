package com.fcode.myfirstapplication.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ImageView.loadFromUrl(url: String, listener: RequestListener<Drawable>) {
    Glide
        .with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .addListener(listener)
        .into(this)
}

fun ImageView.loadFromUrl(url: String) {
    Glide
        .with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .into(this)
}

fun CoroutineScope.safeLaunch(
    dispatcher: CoroutineDispatcher,
    task: suspend CoroutineScope.() -> Unit,
    exception: (java.lang.Exception) -> Unit
) {
    this.launch(dispatcher) {
        try {
            task.invoke(this)
        } catch (e: java.lang.Exception) {
            exception.invoke(e)
        }
    }
}