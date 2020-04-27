package pl.sulej.githubusers.dependency

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import pl.sulej.githubusers.GitHubUsersApplication
import pl.sulej.users.dependency.UsersModule
import pl.sulej.utilities.dependency.AndroidModule
import pl.sulej.utilities.dependency.AsynchronicityModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UsersModule::class,
        AsynchronicityModule::class,
        AndroidModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: GitHubUsersApplication)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}