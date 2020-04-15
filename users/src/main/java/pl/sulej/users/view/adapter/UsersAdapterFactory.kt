package pl.sulej.users.view.adapter

import pl.sulej.utilities.adapter.BaseAdapter
import javax.inject.Inject

class UsersAdapterFactory @Inject constructor(
    private val userDelegateFactory: UserDelegateFactory
) {

    fun create(userClicked: (String) -> Unit) = object : BaseAdapter() {

        init {
            delegatesManager.addDelegate(userDelegateFactory.create(userClicked))
        }
    }
}