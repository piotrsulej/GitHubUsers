package pl.sulej.githubusers.dependency

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.sulej.users.dependency.UsersModule
import pl.sulej.users.view.UsersActivity

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = [UsersModule::class])
    internal abstract fun bindUsersActivity(): UsersActivity
}