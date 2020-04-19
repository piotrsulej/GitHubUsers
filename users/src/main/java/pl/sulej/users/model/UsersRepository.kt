package pl.sulej.users.model

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.model.database.UserEntity
import pl.sulej.users.model.database.UsersDao
import pl.sulej.users.model.network.GitHubUsersApi
import pl.sulej.users.model.network.RepositoryDto
import pl.sulej.users.model.network.UserDto
import pl.sulej.utilities.asynchronicity.SchedulerProvider
import pl.sulej.utilities.asynchronicity.fireAndForget
import pl.sulej.utilities.log.Logger
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val network: GitHubUsersApi,
    private val database: UsersDao,
    private val schedulerProvider: SchedulerProvider,
    private val logger: Logger
) : UsersModel {

    private val detailRequestsInProgress = mutableListOf<String>()
    private lateinit var databaseDownloadProgress: PublishSubject<List<UserEntity>>

    override fun getUsers(): Observable<List<UserDetails>> {
        databaseDownloadProgress = PublishSubject.create()
        return database.getUsers().mergeWith(databaseDownloadProgress).map { users ->
            if (users.isEmpty()) {
                downloadUsers()
            }
            users.map(this::getUserDetails)
        }
    }

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
        logger.debugLog(LOGGER_TAG, "Getting list of users from network.")
        network
            .getUsers()
            .doOnError(databaseDownloadProgress::onError)
            .doOnSuccess(this::cacheUsers)
            .fireAndForget(schedulerProvider.subscriptionScheduler())
    }

    private fun cacheUsers(downloadedUsers: List<UserDto>) {
        logger.debugLog(LOGGER_TAG, "Saving ${downloadedUsers.size} users to database.")
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
        logger.debugLog(LOGGER_TAG, "Getting $login's details from network.")
        logger.debugLog(LOGGER_TAG, "${detailRequestsInProgress.size} detail requests in progress.")
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
        logger.debugLog(LOGGER_TAG, "Saving ${userDetails.login}'s details to database.")
        val entity = UserEntity(
            userDetails.login,
            userDetails.avatarUrl,
            userDetails.repositoryNames?.joinToString(",")
        )
        database.update(entity).fireAndForget(schedulerProvider.subscriptionScheduler())
    }

    companion object {
        private const val LOGGER_TAG = "UserRepository"
        private const val REPOSITORY_NAMES_COUNT = 3
    }
}