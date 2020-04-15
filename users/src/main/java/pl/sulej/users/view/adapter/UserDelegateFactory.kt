package pl.sulej.users.view.adapter

import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import pl.sulej.users.R
import pl.sulej.users.view.data.User
import pl.sulej.utils.AdapterItem

class UserDelegateFactory {

    fun create() = adapterDelegate<User, AdapterItem>(R.layout.delegate_user) {

        val name = findViewById<TextView>(R.id.user_name)

        bind {
            name.text = item.name
        }
    }
}