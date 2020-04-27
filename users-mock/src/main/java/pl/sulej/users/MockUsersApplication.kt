package pl.sulej.users

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import pl.sulej.users.dependency.MockApplicationComponent
import pl.sulej.users.dependency.DaggerMockApplicationComponent
import javax.inject.Inject

class MockUsersApplication : DaggerApplication() {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<MockUsersApplication>

    private lateinit var applicationComponent: MockApplicationComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = injector

    override fun onCreate() {
        applicationComponent = DaggerMockApplicationComponent.builder()
            .application(this)
            .build()
        applicationComponent.inject(this)
        super.onCreate()
    }
}