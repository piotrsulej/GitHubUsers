package pl.sulej.users.model

import io.reactivex.Single
import pl.sulej.users.model.data.RepositoryDTO
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.model.network.GitHubUsersApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val networkApi: GitHubUsersApi
) : UsersModel {

    private var cachedUsers: List<UserDetails>? = null

    override fun getUsersWithRepositoriesOfUser(userLogin: String): Single<List<UserDetails>> {
        val cachedUser = cachedUsers?.find { it.userDTO.login == userLogin }
        return if (cachedUser != null && cachedUser.repositories.isEmpty()) {
            getUsersRepositories(cachedUser.userDTO).map {
                cachedUsers
            }
        } else {
            Single.just(cachedUsers)
        }
    }

    override fun getUsers(): Single<List<UserDetails>> =
        getCachedUsersOrNull() ?: getUsersFromNetwork()

    private fun getCachedUsersOrNull(): Single<List<UserDetails>>? =
        cachedUsers?.let { users -> Single.just(users) }

    private fun getUsersRepositories(userDTO: UserDTO): Single<List<UserDetails>> {
        return networkApi
            .getUserRepositories(userDTO.login)
            .map { downloadedRepositories ->
                cacheUserRepositories(userDTO, downloadedRepositories)
                cachedUsers
            }
    }

    private fun cacheUserRepositories(userDTO: UserDTO, repositories: List<RepositoryDTO>) {
        cachedUsers = cachedUsers.orEmpty().map { user ->
            if (user.userDTO == userDTO)
                UserDetails(
                    userDTO = userDTO,
                    repositories = repositories.take(REPOSITORY_NAMES_COUNT)
                )
            else user
        }
    }

    private fun getUsersFromNetwork(): Single<List<UserDetails>> {
        return networkApi
            .getUsers()
            .map { downloadedUsers ->
                cacheUsers(downloadedUsers)
                cachedUsers
            }
    }

    private fun cacheUsers(downloadedUsers: List<UserDTO>) {
        cachedUsers = downloadedUsers.map { downloadedUser ->
            UserDetails(
                userDTO = downloadedUser,
                repositories = emptyList()
            )
        }
    }

    companion object {
        private const val REPOSITORY_NAMES_COUNT = 3
    }
}