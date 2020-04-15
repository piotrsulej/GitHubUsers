package pl.sulej.users.model

import io.reactivex.Single
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.model.network.GitHubUsersAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor() : UsersModel {

    override fun getUsers(): Single<List<UserDTO>> {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(GIT_HUB_API_BASE_URL)
            .build()
            .create(GitHubUsersAPI::class.java)
            .getUsers()
    }

    companion object {
        private const val GIT_HUB_API_BASE_URL = "https://api.github.com/"
    }
}