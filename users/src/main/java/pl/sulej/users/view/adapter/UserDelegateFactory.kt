package pl.sulej.users.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import pl.sulej.users.R
import pl.sulej.users.view.data.User
import pl.sulej.utilities.adapter.AdapterItem
import pl.sulej.utilities.images.ImageLoader
import javax.inject.Inject

class UserDelegateFactory @Inject constructor(private val imageLoader: ImageLoader) {

    fun create(userDetailsClicked: (String) -> Unit) =
        adapterDelegate<User, AdapterItem>(R.layout.delegate_user) {

            val name = findViewById<TextView>(R.id.user_name)
            val avatar = findViewById<ImageView>(R.id.user_avatar)
            val repositories = findViewById<TextView>(R.id.user_repositories)
            val showDetails = findViewById<View>(R.id.user_show_details)

            showDetails.setOnClickListener {
                userDetailsClicked(item.name)
                showDetails.visibility = View.GONE
                repositories.visibility = View.VISIBLE
            }

            bind {
                imageLoader.load(imageUrl = item.avatarUrl, targetView = avatar)
                name.text = item.name
                repositories.text = item.repositoryNames
                repositories.visibility = if (item.detailsExpanded) View.VISIBLE else View.GONE
                showDetails.visibility = if (item.detailsExpanded) View.GONE else View.VISIBLE
            }
        }
}