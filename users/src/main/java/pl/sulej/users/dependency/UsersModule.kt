package pl.sulej.users.dependency

import dagger.Binds
import dagger.Module
import pl.sulej.users.UsersContract
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.UsersRepository
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.presentation.UsersPresenter
import pl.sulej.users.presentation.data.UsersConverter
import pl.sulej.users.view.data.User
import pl.sulej.utilities.Converter

@Module
abstract class UsersModule {

    @Binds
    abstract fun bindUsersConverter(converter: UsersConverter): Converter<List<UserDTO>, List<User>>

    @Binds
    abstract fun bindUsersPresenter(presenter: UsersPresenter): UsersContract.Presenter

    @Binds
    abstract fun bindUsersModel(model: UsersRepository): UsersModel
}