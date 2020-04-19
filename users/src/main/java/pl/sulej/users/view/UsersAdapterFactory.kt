package pl.sulej.users.view

import pl.sulej.users.view.user.UserDelegateFactory
import pl.sulej.utilities.adapter.BaseAdapter
import javax.inject.Inject

class UsersAdapterFactory @Inject constructor(private val userDelegateFactory: UserDelegateFactory) {

    fun create() = BaseAdapter(delegates = listOf(userDelegateFactory.create()))
}