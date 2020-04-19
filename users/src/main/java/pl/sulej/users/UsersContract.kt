package pl.sulej.users

import pl.sulej.users.view.user.User

interface UsersContract {

    interface View {

        fun showUsers(users: List<User>)

        fun showLoadingIndicator()

        fun showError(errorMessage: String)
    }

    interface Presenter {

        fun viewCreated(view: View)

        fun viewAvailable()

        fun viewUnavailable()

        fun errorClicked()

        fun searchQueryUpdated(searchQuery: String)
    }
}