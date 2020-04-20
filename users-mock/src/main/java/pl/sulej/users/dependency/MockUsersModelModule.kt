package pl.sulej.users.dependency

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.reactivex.Completable
import pl.sulej.users.model.assets.*
import pl.sulej.users.model.database.UsersDatabase
import pl.sulej.users.model.network.GitHubUsersApi
import pl.sulej.utilities.asynchronicity.SchedulerProvider
import pl.sulej.utilities.asynchronicity.fireAndForget
import javax.inject.Singleton

@Module(includes = [UsersModelModule::class])
class MockUsersModelModule {

    @Provides
    @Singleton
    fun provideGitHubUsersApi(api: MockAssetsGitHubUsersApi): GitHubUsersApi = api

    @Provides
    @Singleton
    fun provideUsersDatabase(
        application: Application,
        schedulerProvider: SchedulerProvider
    ): UsersDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            UsersDatabase::class.java,
            DATABASE_NAME
        ).build().apply {
            Completable.fromCallable {
                clearAllTables()
            }.fireAndForget(schedulerProvider.subscriptionScheduler())
        }

    @Provides
    @Singleton
    fun provideAssetsObjectsLoader(loader: GsonAssetsObjectsLoader): AssetsObjectsLoader = loader

    @Provides
    @Singleton
    fun provideAssetsLoader(loader: AndroidAssetsLoader): AssetsLoader = loader

    companion object {
        private const val DATABASE_NAME = "mock_users_database"
    }
}