package pl.sulej.utilities.images

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import pl.sulej.utilities.dependency.ActivityScope
import javax.inject.Inject

@ActivityScope
class GlideImageLoader @Inject constructor(
    private val activity: Activity
) : ImageLoader {

    override fun load(imageUrl: String, targetView: ImageView) {
        Glide.with(activity)
            .load(imageUrl)
            .into(targetView)
    }
}