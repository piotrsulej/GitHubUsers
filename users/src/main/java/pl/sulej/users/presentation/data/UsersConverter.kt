package pl.sulej.users.presentation.data

import pl.sulej.users.R
import pl.sulej.users.model.data.RepositoryDTO
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.presentation.UserList
import pl.sulej.users.view.data.User
import pl.sulej.utilities.design.Converter
import pl.sulej.utilities.resources.StringProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersConverter @Inject constructor(
    private val stringProvider: StringProvider
) : Converter<UserList, List<@JvmSuppressWildcards User>> {

    override fun convert(input: UserList): List<User> =
        input.userDetails.map { user ->
            User(
                name = user.userDTO.login,
                avatarUrl = user.userDTO.avatarUrl,
                repositoriesInfo = getRepositoryNames(user)
            )
        }.filter { user ->
            isCompatibleWithSearchQuery(user, input)
        }

    private fun getRepositoryNames(user: UserDetails): String =
        when {
            user.repositories == null -> stringProvider.getString(R.string.user_no_data_about_repositories)
            user.repositories.isEmpty() -> stringProvider.getString(R.string.user_no_repositories)
            else -> user.repositories.joinToString(transform = RepositoryDTO::name)
        }

    private fun isCompatibleWithSearchQuery(user: User, input: UserList) =
        input.searchQuery.isEmpty()
                || user.name.contains(input.searchQuery)
                || user.repositoriesInfo.contains(input.searchQuery)
}