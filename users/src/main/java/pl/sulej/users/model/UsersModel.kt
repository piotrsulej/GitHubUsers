package pl.sulej.users.model

import io.reactivex.Observable

interface UsersModel {

    fun getUsers(): Observable<List<UserDetails>>
}