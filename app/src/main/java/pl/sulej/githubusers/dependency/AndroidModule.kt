package pl.sulej.githubusers.dependency

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule {

    @Singleton
    @Provides
    fun provideResources(application: Application): Resources = application.resources
}
