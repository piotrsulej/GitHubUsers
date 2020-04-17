package pl.sulej.users.model

import io.reactivex.Flowable
import io.reactivex.Single
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

    override fun getUsers(): Flowable<List<UserDetails>> =
        (getCachedUsersOrNull() ?: getUsersFromNetwork())
            .flatMapPublisher { users -> Flowable.fromIterable(users) }
            .flatMap { user ->
                (getCachedUserDetailsOrNull(user) ?: getUserDetailsFromNetwork(user))
                    .toFlowable()
                    .subscribeOn(schedulerProvider.subscriptionScheduler())
            }
            .doOnNext { user -> cacheUserDetails(user) }
            .map { cachedUsers.orEmpty() }

    private fun getUsersFromNetwork(): Single<List<UserDetails>> {
        return networkApi
            .getUsers()
            .map { downloadedUsers ->
                cacheUsers(downloadedUsers)
                cachedUsers
            }
    }

    private fun getUserDetailsFromNetwork(cachedUser: UserDetails): Single<UserDetails> =
        networkApi.getUserRepositories(cachedUser.userDTO.login).map { repositories ->
            UserDetails(
                userDTO = cachedUser.userDTO,
                repositories = repositories.take(REPOSITORY_NAMES_COUNT)
            )
        }.onErrorReturnItem(cachedUser)

    private fun getCachedUsersOrNull(): Single<List<UserDetails>>? =
        cachedUsers?.let { users -> Single.just(users) }

    private fun getCachedUserDetailsOrNull(cachedUser: UserDetails): Single<UserDetails>? =
        cachedUser.repositories?.let { Single.just(cachedUser) }

    private fun cacheUsers(downloadedUsers: List<UserDTO>) {
        cachedUsers = downloadedUsers.map { userDto ->
            UserDetails(userDto)
        }
    }

    private fun cacheUserDetails(userDetails: UserDetails) {
        cachedUsers = cachedUsers.orEmpty().map { cachedUserDetails ->
            if (cachedUserDetails.userDTO == userDetails.userDTO)
                userDetails
            else cachedUserDetails
        }
    }

    companion object {
        private const val REPOSITORY_NAMES_COUNT = 3
    }
}