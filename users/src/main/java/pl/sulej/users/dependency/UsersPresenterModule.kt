package pl.sulej.users.dependency

import dagger.Binds
import dagger.Module
import pl.sulej.users.UsersContract
import pl.sulej.users.presentation.FilteredUserList
import pl.sulej.users.presentation.UsersPresenter
import pl.sulej.users.presentation.UsersConverter
import pl.sulej.users.view.user.User
import pl.sulej.utilities.design.Converter

@Module
abstract class UsersPresenterModule {

    @Binds
    abstract fun bindUsersConverter(converter: UsersConverter): Converter<FilteredUserList, List<User>>

    @Binds
    abstract fun bindUsersPresenter(presenter: UsersPresenter): UsersContract.Presenter
}