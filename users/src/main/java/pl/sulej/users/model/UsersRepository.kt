package pl.sulej.users.model

import io.reactivex.Flowable
import io.reactivex.Single
import pl.sulej.users.model.data.RepositoryDTO
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.model.network.GitHubUsersApi
import pl.sulej.utilities.asynchronicity.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val networkApi: GitHubUsersApi,
    private val schedulerProvider: SchedulerProvider
) : UsersModel {

    private var cachedUsers: List<UserDetails>? = null

    override fun getUsers(): Flowable<List<UserDetails>> {
        val users = getCachedUsersOrNull() ?: getUsersFromNetwork()
        return users
            .flatMapPublisher { Flowable.fromIterable(it) }
            .flatMap {
                (getCachedUserRepositoriesOrNull(it) ?: getUserRepositoriesFromNetwork(it))
                    .toFlowable()
                    .subscribeOn(schedulerProvider.subscriptionScheduler())
            }
            .doOnNext { cacheUserRepositories(it.userDTO, it.repositories) }
            .map { cachedUsers.orEmpty() }
    }

    private fun getCachedUserRepositoriesOrNull(cachedUser: UserDetails): Single<UserDetails>? =
        cachedUser.repositories?.let { Single.just(cachedUser) }

    private fun getUserRepositoriesFromNetwork(cachedUser: UserDetails): Single<UserDetails> {
        return networkApi.getUserRepositories(cachedUser.userDTO.login).map { repositories ->
            UserDetails(
                userDTO = cachedUser.userDTO,
                repositories = repositories.take(REPOSITORY_NAMES_COUNT)
            )
        }
    }

    private fun getCachedUsersOrNull(): Single<List<UserDetails>>? =
        cachedUsers?.let { users -> Single.just(users) }

    private fun cacheUserRepositories(userDTO: UserDTO, repositories: List<RepositoryDTO>?) {
        cachedUsers = cachedUsers.orEmpty().map { user ->
            if (user.userDTO == userDTO)
                UserDetails(
                    userDTO = userDTO,
                    repositories = repositories.orEmpty().take(REPOSITORY_NAMES_COUNT)
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
        cachedUsers = downloadedUsers.map { userDto ->
            UserDetails(userDto)
        }
    }

    companion object {
        private const val REPOSITORY_NAMES_COUNT = 3
    }
}