package id.rrdevfundamental.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import id.rrdevfundamental.R

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.loading_anim)
        .error(R.drawable.ic_broken_image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .circleCrop()
        .into(this)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.snackbar(message: String, function: () -> (Unit)) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("OK") {
            function()
        }
    }.show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun tag(context: Context) : String {
    return context.javaClass.simpleName
}