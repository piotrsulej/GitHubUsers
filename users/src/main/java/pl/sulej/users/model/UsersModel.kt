package pl.sulej.users.model

import io.reactivex.Single
import pl.sulej.users.model.data.UserDTO

interface UsersModel {

    fun getUsers(): Single<List<UserDTO>>
}