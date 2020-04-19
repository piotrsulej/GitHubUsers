package pl.sulej.users.model

import io.reactivex.Observable
import pl.sulej.users.model.data.UserDetails

interface UsersModel {

    fun getUsers(): Observable<List<UserDetails>>
}