package pl.sulej.users.presentation.data

import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.view.data.User
import pl.sulej.utilities.design.Converter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersConverter @Inject constructor() :
    Converter<List<@JvmSuppressWildcards UserDTO>, List<@JvmSuppressWildcards User>> {

    override fun convert(input: List<UserDTO>): List<User> =
        input.map { userDTO ->
            User(
                name = userDTO.login,
                avatarUrl = userDTO.avatar_url,
                repositoryNames = emptyList()
            )
        }
}