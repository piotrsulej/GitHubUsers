package pl.sulej.users.dependency

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import pl.sulej.users.MockUsersApplication
import pl.sulej.utilities.dependency.AndroidModule
import pl.sulej.utilities.dependency.AsynchronicityModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MockUsersModule::class,
        AsynchronicityModule::class,
        AndroidModule::class
    ]
)
interface MockApplicationComponent {

    fun inject(application: MockUsersApplication)

    @Component.Builder
    interface Builder {

        fun build(): MockApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}