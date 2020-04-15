package pl.sulej.users.view.adapter

import pl.sulej.utilities.adapter.BaseAdapter
import javax.inject.Inject

class UsersAdapterFactory @Inject constructor(private val userDelegateFactory: UserDelegateFactory) {

    fun create(userDetailsClicked: (String) -> Unit) = BaseAdapter(
        delegates = listOf(userDelegateFactory.create(userDetailsClicked))
    )
}