package pl.sulej.users.presentation

import pl.sulej.users.R
import pl.sulej.users.UsersContract
import pl.sulej.users.model.UserList
import pl.sulej.users.model.UsersModel
import pl.sulej.users.view.user.User
import pl.sulej.utilities.asynchronicity.SubscriptionsManager
import pl.sulej.utilities.design.Converter
import pl.sulej.utilities.resources.StringProvider
import javax.inject.Inject

class UsersPresenter @Inject constructor(
    private val model: UsersModel,
    private val converter: Converter<FilteredUserList, List<User>>,
    private val subscriptionsManager: SubscriptionsManager,
    private val stringProvider: StringProvider
) : UsersContract.Presenter {

    private var searchQuery: String = ""
    private var view: UsersContract.View? = null

    override fun viewCreated(view: UsersContract.View) {
        this.view = view
    }

    override fun viewAvailable() {
        view?.showLoadingIndicator()
        getUsers()
    }

    override fun errorClicked() {
        view?.showLoadingIndicator()
        getUsers()
    }

    private fun getUsers() {
        subscriptionsManager.subscribe(
            tag = this.toString(),
            source = model.getUsers(),
            onNext = this::handleUsersList,
            onError = this::showError
        )
    }

    private fun showError(error: Throwable) {
        val message = error.message ?: stringProvider.getString(R.string.unknown_error)
        view?.showError(message)
    }

    override fun searchQueryUpdated(searchQuery: String) {
        this.searchQuery = searchQuery
        getUsers()
    }

    override fun viewUnavailable() {
        subscriptionsManager.unsubscribe(tag = this.toString())
    }

    private fun handleUsersList(usersList: UserList) {
        usersList.error?.let(this::showError)

        val filteredUserList = FilteredUserList(usersList.users, searchQuery)
        val convertedUsers = converter.convert(filteredUserList)
        view?.showUsers(convertedUsers)
    }
}