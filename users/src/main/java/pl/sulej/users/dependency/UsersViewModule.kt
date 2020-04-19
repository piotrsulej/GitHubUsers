package pl.sulej.users.dependency

import android.app.Activity
import dagger.Binds
import dagger.Module
import pl.sulej.users.view.UsersActivity
import pl.sulej.utilities.dependency.ImagesModule

@Module(includes = [ImagesModule::class])
abstract class UsersViewModule {

    @Binds
    abstract fun bindActivity(activity: UsersActivity): Activity
}