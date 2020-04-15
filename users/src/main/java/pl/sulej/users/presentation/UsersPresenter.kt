package pl.sulej.users.presentation

import pl.sulej.users.UsersContract
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.view.data.User
import pl.sulej.utilities.design.Converter
import pl.sulej.utilities.asynchronicity.SubscriptionsManager
import javax.inject.Inject

class UsersPresenter @Inject constructor(
    private val model: UsersModel,
    private val converter: Converter<List<UserDTO>, List<User>>,
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

    override fun viewUnavailable() {
        subscriptionsManager.unsubscribe(tag = this.toString())
    }

    private fun handleUsersList(userDTOs: List<UserDTO>) {
        val users = converter.convert(userDTOs)
        view?.showUsers(users)
    }
}