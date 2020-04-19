package pl.sulej.users.model

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import pl.sulej.users.model.database.UserEntity
import pl.sulej.users.model.database.UsersDao
import pl.sulej.users.model.network.GitHubUsersApi
import pl.sulej.users.model.network.RepositoryDto
import pl.sulej.users.model.network.UserDto
import pl.sulej.utilities.asynchronicity.TestSchedulerProvider

class UsersRepositoryTest {

    private val database: UsersDao = mock()
    private val network: GitHubUsersApi = mock()
    private lateinit var testSubject: UsersRepository

    @Before
    fun setUp() {
        given(network.getUsers())
            .willReturn(Single.just(DUMMY_USERS_FROM_NETWORK))
        given(network.getUserRepositories(DUMMY_USER.login))
            .willReturn(Single.just(DUMMY_REPOSITORIES))

        testSubject = UsersRepository(
            network = network,
            database = database,
            schedulerProvider = TestSchedulerProvider.INSTANCE,
            logger = mock()
        )
    }

    @Test
    fun `Update user details from network into database`() {
        val userWithoutRepositories = listOf(DUMMY_ENTITY_WITHOUT_REPOSITORIES)
        given(database.getUsers()).willReturn(Observable.just(userWithoutRepositories))

        testSubject.getUsers().test()

        then(database)
            .should()
            .update(DUMMY_ENTITY)
    }

    @Test
    fun `Insert users from network into database`() {
        given(database.getUsers()).willReturn(Observable.just(emptyList()))
        given(network.getUserRepositories(DUMMY_USER.login)).willReturn(Single.error(Throwable()))

        testSubject.getUsers().test()

        then(database)
            .should()
            .insert(DUMMY_ENTITY_WITHOUT_REPOSITORIES)
    }

    @Test
    fun `Get users from database`() {
        given(database.getUsers()).willReturn(Observable.just(listOf(DUMMY_ENTITY)))

        val result = testSubject.getUsers().test()

        result.assertValue(DUMMY_USER_DETAILS)
    }

    @Test
    fun `Ignore network error`() {
        given(network.getUsers()).willReturn(Single.error(DUMMY_ERROR))
        given(database.getUsers()).willReturn(Observable.just(listOf(DUMMY_ENTITY)))

        val result = testSubject.getUsers().test()

        result.assertValue(DUMMY_USER_DETAILS)
    }

    @Test
    fun `Propagate network error`() {
        given(network.getUsers()).willReturn(Single.error(DUMMY_ERROR))
        given(database.getUsers()).willReturn(Observable.just(emptyList()))

        val result = testSubject.getUsers().test()

        result.assertError(DUMMY_ERROR)
    }

    companion object {
        private val DUMMY_USER =
            UserDto(
                login = "Diego",
                avatarUrl = "https://gamepedia.cursecdn.com/gothic_pl_gamepedia/f/fa/Diego_%28G2%2C_cie%C5%84%29.png"
            )
        private val DUMMY_USERS_FROM_NETWORK = listOf(DUMMY_USER)
        private val EXPECTED_REPOSITORIES = listOf(
            "Stary Obóz",
            "Miecz bojowy",
            "Łuk Diego"
        )
        private val DUMMY_ENTITY = UserEntity(
            login = DUMMY_USER.login,
            avatarUrl = DUMMY_USER.avatarUrl,
            repositoryNames = "Stary Obóz,Miecz bojowy,Łuk Diego"
        )
        private val DUMMY_ENTITY_WITHOUT_REPOSITORIES = DUMMY_ENTITY.copy(repositoryNames = null)
        private val DUMMY_REPOSITORIES =
            listOf(
                RepositoryDto("Stary Obóz"),
                RepositoryDto("Miecz bojowy"),
                RepositoryDto("Łuk Diego"),
                RepositoryDto("Zbroja Cienia")
            )
        private val DUMMY_USER_DETAILS = listOf(
            UserDetails(
                login = DUMMY_USER.login,
                avatarUrl = DUMMY_USER.avatarUrl,
                repositoryNames = EXPECTED_REPOSITORIES
            )
        )
        private val DUMMY_ERROR = Throwable("Nie interesuje mnie kim jesteś.")
    }
}