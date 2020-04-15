package pl.sulej.users.dependency

import dagger.Module
import dagger.Provides
import pl.sulej.users.model.network.GitHubUsersApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGitHubUsersApi(): GitHubUsersApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.github.com/")
            .build()
            .create(GitHubUsersApi::class.java)
}