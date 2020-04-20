package pl.sulej.users.dependency

import dagger.Module
import dagger.Provides
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.UsersRepository
import pl.sulej.users.model.database.UsersDao
import pl.sulej.users.model.database.UsersDatabase
import javax.inject.Singleton

@Module
class UsersModelModule {

    @Provides
    @Singleton
    fun provideUsersDao(database: UsersDatabase): UsersDao = database.usersDao()

    @Provides
    @Singleton
    fun provideUsersModel(model: UsersRepository): UsersModel = model
}