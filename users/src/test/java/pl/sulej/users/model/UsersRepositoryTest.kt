package pl.sulej.users.model

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import pl.sulej.users.model.data.RepositoryDTO
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.model.network.GitHubUsersApi
import pl.sulej.utilities.ONCE
import pl.sulej.utilities.asynchronicity.TestSchedulerProvider

class UsersRepositoryTest {

    private val networkApi: GitHubUsersApi = mock()
    private lateinit var testSubject: UsersRepository

    @Before
    fun setUp() {
        given(networkApi.getUsers()).willReturn(Single.just(DUMMY_USERS_FROM_NETWORK))
        given(networkApi.getUserRepositories(DUMMY_USER.login))
            .willReturn(Single.just(DUMMY_REPOSITORIES))
        testSubject = UsersRepository(networkApi, TestSchedulerProvider.INSTANCE)
    }

    @Test
    fun `Return users from network`() {
        val result = testSubject.getUsers().test()

        result.assertValue(DUMMY_USER_DETAILS)
    }

    @Test
    fun `Return users without details`() {
        given(networkApi.getUserRepositories(DUMMY_USER.login))
            .willReturn(Single.error(Throwable()))

        val result = testSubject.getUsers().test()

        result.assertValue(DUMMY_USER_WITHOUT_DETAILS)
    }

    @Test
    fun `Don't repeat network call`() {
        repeat(3) {
            testSubject.getUsers().test()
        }

        then(networkApi)
            .should(ONCE)
            .getUsers()
    }

    @Test
    fun `Return cached users`() {
        repeat(3) {
            testSubject.getUsers().test()
        }

        val result = testSubject.getUsers().test()

        result.assertValue(DUMMY_USER_DETAILS)
    }

    companion object {
        private val DUMMY_USER =
            UserDTO(
                login = "Diego",
                avatarUrl = "https://gamepedia.cursecdn.com/gothic_pl_gamepedia/f/fa/Diego_%28G2%2C_cie%C5%84%29.png"
            )
        private val DUMMY_USERS_FROM_NETWORK = listOf(DUMMY_USER)
        private val EXPECTED_REPOSITORIES = listOf(
            RepositoryDTO("Stary Obóz"),
            RepositoryDTO("Miecz bojowy"),
            RepositoryDTO("Łuk Diego")
        )
        private val DUMMY_REPOSITORIES = EXPECTED_REPOSITORIES.plus(RepositoryDTO("Zbroja Cienia"))
        private val DUMMY_USER_DETAILS = listOf(
            UserDetails(
                userDTO = DUMMY_USER,
                repositories = EXPECTED_REPOSITORIES
            )
        )
        private val DUMMY_USER_WITHOUT_DETAILS = listOf(
            UserDetails(userDTO = DUMMY_USER)
        )
    }
}