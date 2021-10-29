package id.rrdevfundamental.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.rrdevfundamental.R

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(resources.getIdentifier(url, "drawable", context.packageName))
        .placeholder(R.drawable.loading_anim)
        .error(R.drawable.ic_broken_image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .circleCrop()
        .into(this)
}