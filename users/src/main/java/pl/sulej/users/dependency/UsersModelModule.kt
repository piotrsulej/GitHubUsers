package pl.sulej.users.dependency

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.UsersRepository
import pl.sulej.users.model.database.UsersDao
import pl.sulej.users.model.database.UsersDatabase
import pl.sulej.users.model.network.GitHubUsersApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class UsersModelModule {

    @Provides
    @Singleton
    fun provideGitHubUsersApi(): GitHubUsersApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(NETWORK_API_URL)
            .build()
            .create(GitHubUsersApi::class.java)

    @Provides
    @Singleton
    fun provideUsersDatabase(application: Application): UsersDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            UsersDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideUsersDao(database: UsersDatabase): UsersDao = database.usersDao()

    @Provides
    @Singleton
    fun provideUsersModel(model: UsersRepository): UsersModel = model

    companion object {
        private const val DATABASE_NAME = "users_database"
        private const val NETWORK_API_URL = "https://api.github.com/"
    }
}