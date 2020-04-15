package pl.sulej.githubusers

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import pl.sulej.githubusers.dependency.ApplicationComponent
import pl.sulej.githubusers.dependency.DaggerApplicationComponent
import javax.inject.Inject

class GitHubUsersApplication : DaggerApplication() {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<GitHubUsersApplication>

    private lateinit var applicationComponent: ApplicationComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = injector

    override fun onCreate() {
        applicationComponent = DaggerApplicationComponent.builder()
            .application(this)
            .build()
        applicationComponent.inject(this)
        super.onCreate()
    }
}