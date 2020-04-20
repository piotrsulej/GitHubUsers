package pl.sulej.githubusers.dependency

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import pl.sulej.githubusers.MockGitHubUsersApplication
import pl.sulej.users.dependency.MockUsersModule
import pl.sulej.utilities.dependency.AsynchronicityModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        MockUsersModule::class,
        AsynchronicityModule::class,
        AndroidModule::class
    ]
)
interface MockApplicationComponent {

    fun inject(application: MockGitHubUsersApplication)

    @Component.Builder
    interface Builder {

        fun build(): MockApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}