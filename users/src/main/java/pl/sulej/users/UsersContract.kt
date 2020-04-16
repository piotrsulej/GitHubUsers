package pl.sulej.users

import pl.sulej.users.view.UserDetailClick
import pl.sulej.users.view.data.User

interface UsersContract {

    interface View {

        fun showUsers(users: List<User>)
    }

    interface Presenter {

        fun viewCreated(view: View)

        fun viewAvailable()

        fun viewUnavailable()

        fun userDetailsClicked(userDetailClick: UserDetailClick)

        fun searchQueryUpdated(searchQuery: String)
    }
}