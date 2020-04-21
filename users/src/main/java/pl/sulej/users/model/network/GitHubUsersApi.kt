package pl.sulej.users.model.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubUsersApi {

    @GET(value = "users")
    fun getUsers(): Single<Result<List<UserDto>>>

    @GET(value = "users/{login}/repos")
    fun getUserRepositories(@Path("login") userLogin: String): Single<List<RepositoryDto>>
}