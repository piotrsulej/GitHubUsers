package pl.sulej.users.presentation.data

import pl.sulej.users.model.data.RepositoryDTO
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.view.data.User
import pl.sulej.utilities.design.Converter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersConverter @Inject constructor() :
    Converter<List<@JvmSuppressWildcards UserDetails>, List<@JvmSuppressWildcards User>> {

    override fun convert(input: List<UserDetails>): List<User> =
        input.map { user ->
            User(
                name = user.userDTO.login,
                avatarUrl = user.userDTO.avatarUrl,
                repositoryNames = user.repositories.joinToString(transform = RepositoryDTO::name)
            )
        }
}