package pl.sulej.users.view.adapter

import pl.sulej.utilities.Adapter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersAdapter @Inject constructor(userDelegateFactory: UserDelegateFactory) : Adapter() {

    init {
        delegatesManager.addDelegate(userDelegateFactory.create())
    }
}