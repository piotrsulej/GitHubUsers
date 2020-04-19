package pl.sulej.users.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface UsersDao {

    @Insert
    fun insert(user: UserEntity): Completable

    @Update
    fun update(user: UserEntity): Completable

    @Query("SELECT * from users")
    fun getUsers(): Observable<List<UserEntity>>
}