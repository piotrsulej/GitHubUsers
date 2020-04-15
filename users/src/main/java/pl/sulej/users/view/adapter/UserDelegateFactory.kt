package pl.sulej.users.view.adapter

import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import pl.sulej.users.R
import pl.sulej.users.view.data.User
import pl.sulej.utilities.AdapterItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDelegateFactory @Inject constructor() {

    fun create() = adapterDelegate<User, AdapterItem>(R.layout.delegate_user) {

        val name = findViewById<TextView>(R.id.user_name)

        bind {
            name.text = item.name
        }
    }
}