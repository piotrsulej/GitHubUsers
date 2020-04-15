package pl.sulej.users.presentation

import pl.sulej.users.UsersContract
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.view.data.User
import pl.sulej.utils.Converter
import pl.sulej.utils.SubscriptionsManager

class UsersPresenter(
    private val model: UsersModel,
    private val converter: Converter<List<UserDTO>, List<User>>,
    private val subscriptionsManager: SubscriptionsManager
) : UsersContract.Presenter {

    private var view: UsersContract.View? = null

    override fun viewCreated(view: UsersContract.View) {
        this.view = view
    }

    override fun viewAvailable() {
        val subscription = model.getUsers()
            .doOnSuccess(::handleUsersList)

        subscriptionsManager.subscribe(tag = this.toString(), source = subscription)
    }

    override fun viewUnavailable() {
        subscriptionsManager.unsubscribe(tag = this.toString())
    }

    private fun handleUsersList(userDTOs: List<UserDTO>) {
        val users = converter.convert(userDTOs)
        view?.showUsers(users)
    }
}