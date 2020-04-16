package pl.sulej.users.presentation

import pl.sulej.users.UsersContract
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.view.UserDetailClick
import pl.sulej.users.view.data.User
import pl.sulej.utilities.asynchronicity.SubscriptionsManager
import pl.sulej.utilities.design.Converter
import javax.inject.Inject

class UsersPresenter @Inject constructor(
    private val model: UsersModel,
    private val converter: Converter<UserList, List<User>>,
    private val subscriptionsManager: SubscriptionsManager
) : UsersContract.Presenter {

    private var searchQuery: String = ""
    private var expandedUserNames: List<String> = emptyList()
    private var view: UsersContract.View? = null

    override fun viewCreated(view: UsersContract.View) {
        this.view = view
    }

    override fun viewAvailable() {
        getUsers()
    }

    private fun getUsers() {
        subscriptionsManager.subscribe(
            tag = this.toString(),
            source = model.getUsers(),
            onSuccess = ::handleUsersList
        )
    }

    override fun userDetailsClicked(userDetailClick: UserDetailClick) {
        expandedUserNames = if (userDetailClick.expanded) {
            expandedUserNames + userDetailClick.userName
        } else {
            expandedUserNames - userDetailClick.userName
        }
        subscriptionsManager.subscribe(
            tag = this.toString(),
            source = model.getUsersWithRepositoriesOfUser(userDetailClick.userName),
            onSuccess = ::handleUsersList
        )
    }

    override fun searchQueryUpdated(searchQuery: String) {
        this.searchQuery = searchQuery
        getUsers()
    }

    override fun viewUnavailable() {
        subscriptionsManager.unsubscribe(tag = this.toString())
    }

    private fun handleUsersList(users: List<UserDetails>) {
        val userList = UserList(users, expandedUserNames, searchQuery)
        val convertedUsers = converter.convert(userList)
        view?.showUsers(convertedUsers)
    }
}