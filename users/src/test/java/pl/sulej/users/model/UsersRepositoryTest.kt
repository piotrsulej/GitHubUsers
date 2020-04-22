package pl.sulej.users.model

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.junit.Before
import org.junit.Test
import pl.sulej.users.model.database.UserEntity
import pl.sulej.users.model.database.UsersDao
import pl.sulej.users.model.network.GitHubUsersApi
import pl.sulej.users.model.network.RepositoryDto
import pl.sulej.users.model.network.UserDto
import pl.sulej.utilities.ONCE
import pl.sulej.utilities.TWICE
import pl.sulej.utilities.asynchronicity.TestSchedulerProvider
import pl.sulej.utilities.asynchronicity.asObservable
import pl.sulej.utilities.asynchronicity.asSingle
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result

class UsersRepositoryTest {

    private val database: UsersDao = mock()
    private val network: GitHubUsersApi = mock()
    private lateinit var testSubject: UsersRepository

    @Before
    fun setUp() {
        given(network.getUsers()).willReturn(DUMMY_USERS_FROM_NETWORK.asSingle())
        given(network.getUserRepositories(DUMMY_USER.login)).willReturn(DUMMY_REPOSITORIES.asSingle())

        testSubject = UsersRepository(
            network = network,
            database = database,
            schedulerProvider = TestSchedulerProvider.INSTANCE
        )
    }

    @Test
    fun `Update user details from network into database`() {
        val userWithoutRepositories = listOf(DUMMY_ENTITY_WITHOUT_REPOSITORIES)
        given(database.getUsers()).willReturn(userWithoutRepositories.asObservable())

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
    fun `Don't start multiple network requests`() {
        given(network.getUsers()).willReturn(Single.never())
        given(database.getUsers()).willReturn(Observable.just(emptyList()))

        testSubject.getUsers().test()
        testSubject.getUsers().test()

        then(network)
            .should(ONCE)
            .getUsers()
    }

    @Test
    fun `Request network after error`() {
        given(database.getUsers()).willReturn(Observable.just(emptyList()))

        testSubject.getUsers().test()

        given(network.getUsers()).willReturn(DUMMY_USERS_FROM_NETWORK.asSingle())
        testSubject.getUsers().test()

        then(network)
            .should(TWICE)
            .getUsers()
    }

    @Test
    fun `Ignore empty user list in database`() {
        given(database.getUsers()).willReturn(Observable.just(emptyList()))

        val result = testSubject.getUsers().test()

        result.assertNoValues()
    }

    @Test
    fun `Get users from database`() {
        given(database.getUsers()).willReturn(listOf(DUMMY_ENTITY).asObservable())

        val result = testSubject.getUsers().test()

        result.assertValue(DUMMY_USER_LIST)
    }

    @Test
    fun `Ignore network error`() {
        given(network.getUsers()).willReturn(Single.just(Result.error(DUMMY_ERROR)))
        given(database.getUsers()).willReturn(listOf(DUMMY_ENTITY).asObservable())

        val result = testSubject.getUsers().test()

        result.assertValue(DUMMY_USER_LIST)
    }

    @Test
    fun `Propagate network error`() {
        val networkSingle = SingleSubject.create<Result<List<UserDto>>>()
        given(network.getUsers()).willReturn(networkSingle)
        given(database.getUsers()).willReturn(Observable.just(emptyList()))

        val result = testSubject.getUsers().test()

        networkSingle.onSuccess(Result.error(DUMMY_ERROR))

        result.assertValue(UserList(error = DUMMY_ERROR))
    }

    companion object {
        private val DUMMY_USER =
            UserDto(
                login = "Diego",
                avatarUrl = "https://gamepedia.cursecdn.com/gothic_pl_gamepedia/f/fa/Diego_%28G2%2C_cie%C5%84%29.png"
            )
        private val DUMMY_USERS_FROM_NETWORK = Result.response(Response.success(listOf(DUMMY_USER)))
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
        private val DUMMY_USER_LIST =
            UserList(
                users = listOf(
                    UserDetails(
                        login = DUMMY_USER.login,
                        avatarUrl = DUMMY_USER.avatarUrl,
                        repositoryNames = EXPECTED_REPOSITORIES
                    )
                )
            )
        private val DUMMY_ERROR = Throwable("Nie interesuje mnie kim jesteś.")
    }
}