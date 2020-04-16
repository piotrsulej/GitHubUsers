package pl.sulej.githubusers.dependency

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import pl.sulej.githubusers.GitHubUsersApplication
import pl.sulej.users.dependency.NetworkModule
import pl.sulej.utilities.dependency.AsynchronicityModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivitiesModule::class,
        AsynchronicityModule::class,
        NetworkModule::class
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