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

    fun create(userClicked: (String) -> Unit) =
        adapterDelegate<User, AdapterItem>(R.layout.delegate_user) {

            val name = findViewById<TextView>(R.id.user_name)
            val avatar = findViewById<ImageView>(R.id.user_avatar)
            val repositories = findViewById<TextView>(R.id.user_repositories)
            val user = findViewById<View>(R.id.user)

            user.setOnClickListener {
                userClicked(item.name)
            }

            bind {
                imageLoader.load(imageUrl = item.avatarUrl, targetView = avatar)
                name.text = item.name
                repositories.text = item.repositoryNames
            }
        }
}