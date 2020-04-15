package pl.sulej.users.view.adapter

import pl.sulej.utils.Adapter

class UsersAdapter(userDelegateFactory: UserDelegateFactory) : Adapter() {

    init {
        delegatesManager.addDelegate(userDelegateFactory.create())
    }
}