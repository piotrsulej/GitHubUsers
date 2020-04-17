package pl.sulej.githubusers.dependency

import android.app.Application
import dagger.Module
import dagger.Provides
import pl.sulej.utilities.resources.AndroidStringProvider
import pl.sulej.utilities.resources.StringProvider

@Module
class AndroidModule {

    @Provides
    internal fun bindResources(application: Application) = application.resources

    @Provides
    internal fun provideStringProvider(provider: AndroidStringProvider): StringProvider = provider
}