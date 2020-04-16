package pl.sulej.users.view.adapter

import pl.sulej.users.view.UserDetailClick
import pl.sulej.utilities.adapter.BaseAdapter
import javax.inject.Inject

class UsersAdapterFactory @Inject constructor(private val userDelegateFactory: UserDelegateFactory) {

    fun create(userDetailsClicked: (UserDetailClick) -> Unit) = BaseAdapter(
        delegates = listOf(userDelegateFactory.create(userDetailsClicked))
    )
}