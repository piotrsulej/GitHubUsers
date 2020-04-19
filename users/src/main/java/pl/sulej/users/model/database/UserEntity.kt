package pl.sulej.users.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val login: String,
    val avatarUrl: String,
    val repositoryNames: String?
)