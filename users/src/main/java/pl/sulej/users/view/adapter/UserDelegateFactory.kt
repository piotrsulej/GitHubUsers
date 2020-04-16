package pl.sulej.users.view.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import pl.sulej.users.R
import pl.sulej.users.view.UserDetailClick
import pl.sulej.users.view.data.User
import pl.sulej.utilities.adapter.AdapterItem
import pl.sulej.utilities.images.ImageLoader
import javax.inject.Inject

class UserDelegateFactory @Inject constructor(
    private val imageLoader: ImageLoader,
    private val context: Context
) {

    fun create(userDetailsClicked: (UserDetailClick) -> Unit) =
        adapterDelegate<User, AdapterItem>(R.layout.delegate_user) {

            val name = findViewById<TextView>(R.id.user_name)
            val avatar = findViewById<ImageView>(R.id.user_avatar)
            val repositories = findViewById<TextView>(R.id.user_repositories)
            val showDetails = findViewById<ImageView>(R.id.user_show_details)

            showDetails.setOnClickListener {
                val newExpandedState = item.detailsExpanded.not()
                userDetailsClicked(UserDetailClick(item.name, newExpandedState))
                updateDetailsView(newExpandedState, repositories, showDetails, item.name)
            }

            bind {
                imageLoader.load(imageUrl = item.avatarUrl, targetView = avatar)
                name.text = item.name
                repositories.text = item.repositoryNames
                updateDetailsView(item.detailsExpanded, repositories, showDetails, item.name)
            }
        }

    private fun updateDetailsView(
        detailsExpanded: Boolean,
        repositories: TextView,
        showDetails: ImageView,
        name: String
    ) {
        repositories.visibility = if (detailsExpanded) View.VISIBLE else View.GONE
        val resource = if (detailsExpanded) CLOSE_DRAWABLE_RESOURCE else MORE_DRAWABLE_RESOURCE
        val drawable = ContextCompat.getDrawable(context, resource)
        val descriptionResource =
            if (detailsExpanded) R.string.content_description_detail_close
            else R.string.content_description_detail_more
        val description = context.getString(descriptionResource, name)
        showDetails.contentDescription = description
        showDetails.setImageDrawable(drawable)
    }

    companion object {
        private const val CLOSE_DRAWABLE_RESOURCE = android.R.drawable.ic_menu_close_clear_cancel
        private const val MORE_DRAWABLE_RESOURCE = android.R.drawable.ic_menu_more
    }
}