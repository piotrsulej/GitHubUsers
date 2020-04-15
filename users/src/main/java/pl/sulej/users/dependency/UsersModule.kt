package pl.sulej.users.dependency

import android.app.Activity
import dagger.Binds
import dagger.Module
import pl.sulej.users.UsersContract
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.UsersRepository
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.presentation.UsersPresenter
import pl.sulej.users.presentation.data.UsersConverter
import pl.sulej.users.view.UsersActivity
import pl.sulej.users.view.data.User
import pl.sulej.utilities.dependency.ImagesModule
import pl.sulej.utilities.design.Converter

@Module(includes = [ImagesModule::class])
abstract class UsersModule {

    @Binds
    abstract fun bindActivity(activity: UsersActivity): Activity

    @Binds
    abstract fun bindUsersConverter(converter: UsersConverter): Converter<List<UserDetails>, List<User>>

    @Binds
    abstract fun bindUsersPresenter(presenter: UsersPresenter): UsersContract.Presenter

    @Binds
    abstract fun bindUsersModel(model: UsersRepository): UsersModel
}