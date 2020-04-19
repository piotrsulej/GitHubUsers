package pl.sulej.users.model.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GitHubUsersApi {

    @Headers("Authorization: Bearer 5767cc5803c613a77401b2868b759532bc6384ce")
    @GET(value = "users")
    fun getUsers(): Single<List<UserDto>>

    @Headers("Authorization: Bearer 5767cc5803c613a77401b2868b759532bc6384ce")
    @GET(value = "users/{login}/repos")
    fun getUserRepositories(@Path("login") userLogin: String): Single<List<RepositoryDto>>
}