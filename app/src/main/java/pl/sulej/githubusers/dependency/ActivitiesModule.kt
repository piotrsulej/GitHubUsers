package pl.sulej.githubusers.dependency

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.sulej.users.dependency.UsersModule
import pl.sulej.users.view.UsersActivity
import pl.sulej.utilities.dependency.ActivityScope

@Module
abstract class ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [UsersModule::class])
    internal abstract fun bindUsersActivity(): UsersActivity
}