package pl.sulej.githubusers

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import pl.sulej.githubusers.dependency.MockApplicationComponent
import pl.sulej.githubusers.dependency.DaggerMockApplicationComponent
import javax.inject.Inject

class MockGitHubUsersApplication : DaggerApplication() {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<MockGitHubUsersApplication>

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