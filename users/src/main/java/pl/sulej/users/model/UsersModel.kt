package pl.sulej.users.model

import io.reactivex.Flowable
import pl.sulej.users.model.data.UserDetails

interface UsersModel {

    fun getUsers(): Flowable<List<UserDetails>>
}