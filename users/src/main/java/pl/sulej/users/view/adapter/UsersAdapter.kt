package pl.sulej.users.view.adapter

import pl.sulej.utilities.adapter.Adapter
import javax.inject.Inject

class UsersAdapter @Inject constructor(userDelegateFactory: UserDelegateFactory) : Adapter() {

    init {
        delegatesManager.addDelegate(userDelegateFactory.create())
    }
}