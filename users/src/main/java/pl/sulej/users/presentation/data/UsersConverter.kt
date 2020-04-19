package pl.sulej.users.presentation.data

import pl.sulej.users.R
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.presentation.FilteredUserList
import pl.sulej.users.view.data.User
import pl.sulej.utilities.design.Converter
import pl.sulej.utilities.resources.StringProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersConverter @Inject constructor(
    private val stringProvider: StringProvider
) : Converter<FilteredUserList, List<@JvmSuppressWildcards User>> {

    override fun convert(input: FilteredUserList): List<User> =
        input.userDetails.map { user ->
            User(
                name = user.login,
                avatarUrl = user.avatarUrl,
                repositoriesInfo = getRepositoryNames(user),
                isLoadingRepositories = user.repositoryNames == null
            )
        }.filter { user ->
            isCompatibleWithSearchQuery(user, input)
        }

    private fun getRepositoryNames(user: UserDetails): String =
        when {
            user.repositoryNames == null -> ""
            user.repositoryNames.isEmpty() -> stringProvider.getString(R.string.user_no_repositories)
            else -> user.repositoryNames.joinToString()
        }

    private fun isCompatibleWithSearchQuery(user: User, input: FilteredUserList) =
        input.searchQuery.isEmpty()
                || user.name.contains(input.searchQuery)
                || user.repositoriesInfo.contains(input.searchQuery)
}