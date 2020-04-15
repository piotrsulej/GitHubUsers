package pl.sulej.users.presentation.data

import pl.sulej.users.model.data.RepositoryDTO
import pl.sulej.users.presentation.UserList
import pl.sulej.users.view.data.User
import pl.sulej.utilities.design.Converter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersConverter @Inject constructor() : Converter<UserList, List<@JvmSuppressWildcards User>> {

    override fun convert(input: UserList): List<User> =
        input.users.map { user ->
            User(
                name = user.userDTO.login,
                avatarUrl = user.userDTO.avatarUrl,
                repositoryNames = user.repositories.joinToString(transform = RepositoryDTO::name),
                detailsExpanded = input.expandedUserNames.any { it == user.userDTO.login }
            )
        }
}