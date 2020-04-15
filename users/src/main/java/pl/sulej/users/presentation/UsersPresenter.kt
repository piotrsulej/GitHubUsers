package pl.sulej.users.presentation

import pl.sulej.users.UsersContract
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.view.data.User
import pl.sulej.utilities.asynchronicity.SubscriptionsManager
import pl.sulej.utilities.design.Converter
import javax.inject.Inject

class UsersPresenter @Inject constructor(
    private val model: UsersModel,
    private val converter: Converter<List<UserDetails>, List<User>>,
    private val subscriptionsManager: SubscriptionsManager
) : UsersContract.Presenter {

    private var view: UsersContract.View? = null

    override fun viewCreated(view: UsersContract.View) {
        this.view = view
    }

    override fun viewAvailable() {
        subscriptionsManager.subscribe(
            tag = this.toString(),
            source = model.getUsers(),
            onSuccess = ::handleUsersList
        )
    }

    override fun userClicked(userName: String) {
        subscriptionsManager.subscribe(
            tag = this.toString(),
            source = model.getUsersWithRepositoriesOfUser(userName),
            onSuccess = ::handleUsersList
        )
    }

    override fun viewUnavailable() {
        subscriptionsManager.unsubscribe(tag = this.toString())
    }

    private fun handleUsersList(users: List<UserDetails>) {
        val convertedUsers = converter.convert(users)
        view?.showUsers(convertedUsers)
    }
}