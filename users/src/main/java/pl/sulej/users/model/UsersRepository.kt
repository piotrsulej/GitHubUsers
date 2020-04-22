package pl.sulej.users.model

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import pl.sulej.users.model.database.UserEntity
import pl.sulej.users.model.database.UsersDao
import pl.sulej.users.model.network.GitHubUsersApi
import pl.sulej.users.model.network.RepositoryDto
import pl.sulej.users.model.network.UserDto
import pl.sulej.utilities.asynchronicity.SchedulerProvider
import pl.sulej.utilities.asynchronicity.fireAndForget
import retrofit2.adapter.rxjava2.Result
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val network: GitHubUsersApi,
    private val database: UsersDao,
    private val schedulerProvider: SchedulerProvider
) : UsersModel {

    private val detailRequestsInProgress = mutableListOf<String>()
    private var networkRequest = PublishSubject.create<UserList>()
    private var listRequestInProgress = false

    override fun getUsers(): Observable<UserList> = getDatabaseUsers().mergeWith(networkRequest)

    private fun getDatabaseUsers() =
        database.getUsers()
            .doOnNext { users ->
                if (users.isEmpty() && listRequestInProgress.not()) {
                    downloadUsers()
                }
            }
            .filter { users -> users.isNotEmpty() }
            .map { users -> UserList(users = users.map(this::getUserDetails)) }

    private fun getUserDetails(user: UserEntity): UserDetails =
        UserDetails(
            login = user.login,
            avatarUrl = user.avatarUrl,
            repositoryNames = convertOrDownloadRepositoryNames(user)
        )

    private fun convertOrDownloadRepositoryNames(user: UserEntity): List<String>? =
        if (user.repositoryNames != null || detailRequestsInProgress.contains(user.login)) {
            user.repositoryNames?.split(",")?.filter(String::isNotBlank)
        } else {
            downloadUserDetails(user.login, user.avatarUrl)
            detailRequestsInProgress.add(user.login)
            null
        }

    private fun downloadUsers() {
        listRequestInProgress = true
        network
            .getUsers()
            .doOnEvent { _, _ -> listRequestInProgress = false }
            .doOnSuccess(this::cacheUsers)
            .doOnSuccess(this::returnError)
            .fireAndForget(schedulerProvider.subscriptionScheduler())
    }

    private fun returnError(usersResponse: Result<List<UserDto>>) {
        usersResponse.error()?.let { throwable ->
            val listWithError = UserList(error = throwable)
            networkRequest.onNext(listWithError)
        }
    }

    private fun cacheUsers(usersResponse: Result<List<UserDto>>) {
        val downloadedUsers = usersResponse.response()?.body().orEmpty()
        downloadedUsers.forEach { userDto ->
            val entity = UserEntity(
                userDto.login,
                userDto.avatarUrl,
                null
            )
            database.insert(entity).fireAndForget(schedulerProvider.subscriptionScheduler())
        }
    }

    private fun downloadUserDetails(login: String, avatarUrl: String) {
        network.getUserRepositories(login)
            .doOnEvent { _, _ -> detailRequestsInProgress.remove(login) }
            .doOnSuccess { repositories ->
                val details = mapDetails(login, avatarUrl, repositories)
                cacheUserDetails(details)
            }
            .fireAndForget(schedulerProvider.subscriptionScheduler())
    }

    private fun mapDetails(login: String, avatarUrl: String, repositories: List<RepositoryDto>) =
        UserDetails(
            login = login,
            avatarUrl = avatarUrl,
            repositoryNames = repositories.take(REPOSITORY_NAMES_COUNT).map(RepositoryDto::name)
        )

    private fun cacheUserDetails(userDetails: UserDetails) {
        val entity = UserEntity(
            userDetails.login,
            userDetails.avatarUrl,
            userDetails.repositoryNames?.joinToString(",")
        )
        database.update(entity).fireAndForget(schedulerProvider.subscriptionScheduler())
    }

    companion object {
        private const val REPOSITORY_NAMES_COUNT = 3
    }
}