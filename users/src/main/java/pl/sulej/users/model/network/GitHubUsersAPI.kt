package pl.sulej.users.model.network

import io.reactivex.Single
import pl.sulej.users.model.data.UserDTO
import retrofit2.http.GET

interface GitHubUsersAPI {

    @GET(value = "users")
    fun getUsers(): Single<List<UserDTO>>
}