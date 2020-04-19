package pl.sulej.users.view.user

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import pl.sulej.users.R
import pl.sulej.utilities.adapter.AdapterItem
import pl.sulej.utilities.images.ImageLoader
import javax.inject.Inject

class UserDelegateFactory @Inject constructor(private val imageLoader: ImageLoader) {

    fun create() = adapterDelegate<User, AdapterItem>(R.layout.delegate_user) {

        val name = findViewById<TextView>(R.id.user_name)
        val avatar = findViewById<ImageView>(R.id.user_avatar)
        val repositories = findViewById<TextView>(R.id.user_repositories)
        val repositoriesLoading = findViewById<View>(R.id.user_repositories_loading)

        bind {
            imageLoader.load(imageUrl = item.avatarUrl, targetView = avatar)
            name.text = item.name
            repositories.text = item.repositoriesInfo
            repositories.visibility =
                if (repositories.text.isEmpty()) View.GONE
                else View.VISIBLE
            repositoriesLoading.visibility =
                if (item.isLoadingRepositories) View.VISIBLE
                else View.GONE
        }
    }

}