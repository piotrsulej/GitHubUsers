package pl.sulej.users.dependency

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.sulej.users.view.UsersActivity
import pl.sulej.utilities.dependency.ActivityScope

@Module(
    includes = [
        UsersPresenterModule::class,
        MockUsersModelModule::class
    ]
)
abstract class MockUsersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [UsersViewModule::class])
    internal abstract fun bindUsersActivity(): UsersActivity
}