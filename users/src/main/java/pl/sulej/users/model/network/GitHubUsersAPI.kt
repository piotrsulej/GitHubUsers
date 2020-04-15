package pl.sulej.users.model.network

import io.reactivex.Single
import pl.sulej.users.model.data.RepositoryDTO
import pl.sulej.users.model.data.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubUsersApi {

    @GET(value = "users")
    fun getUsers(): Single<List<UserDTO>>

    @GET(value = "users/{login}/repos")
    fun getUserRepositories(@Path("login") userLogin: String): Single<List<RepositoryDTO>>
}