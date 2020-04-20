package pl.sulej.githubusers.dependency

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import pl.sulej.utilities.log.LogCat
import pl.sulej.utilities.log.Logger
import pl.sulej.utilities.resources.AndroidStringProvider
import pl.sulej.utilities.resources.StringProvider
import javax.inject.Singleton

@Module(includes = [AndroidInjectionModule::class])
class AndroidModule {

    @Provides
    @Singleton
    internal fun provideLogger(logger: LogCat): Logger = logger

    @Provides
    internal fun provideResources(application: Application): Resources = application.resources

    @Provides
    internal fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    internal fun provideStringProvider(provider: AndroidStringProvider): StringProvider = provider
}