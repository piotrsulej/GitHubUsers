package pl.sulej.users.model

import io.reactivex.Single
import pl.sulej.users.model.data.UserDetails

interface UsersModel {

    fun getUsers(): Single<List<UserDetails>>

    fun getUsersWithRepositoriesOfUser(userLogin: String): Single<List<UserDetails>>
}