package pl.sulej.utilities.images

import android.widget.ImageView

interface ImageLoader {

    fun load(imageUrl: String, targetView: ImageView)
}