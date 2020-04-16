package pl.sulej.users.model

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.internal.verification.Times
import pl.sulej.users.model.data.RepositoryDTO
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.model.network.GitHubUsersApi

class UsersRepositoryTest {

    private val networkApi: GitHubUsersApi = mock()
    private lateinit var testSubject: UsersRepository

    @Before
    fun setUp() {
        given(networkApi.getUsers())
            .willReturn(Single.just(DUMMY_USERS_FROM_NETWORK))
        testSubject = UsersRepository(networkApi)
    }

    @Test
    fun `Return users from network API`() {
        val result = testSubject.getUsers().test()

        result.assertValue(oneUserWithRepositories(repositories = emptyList()))
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

        result.assertValue(oneUserWithRepositories(repositories = emptyList()))
    }

    @Test
    fun `Return users with three user's repositories`() {
        given(networkApi.getUserRepositories(DUMMY_USER.login))
            .willReturn(Single.just(DUMMY_REPOSITORIES))

        testSubject.getUsers().test()
        val result = testSubject.getUsersWithRepositoriesOfUser(DUMMY_USER.login).test()

        result.assertValue(oneUserWithRepositories(EXPECTED_REPOSITORIES))
    }

    @Test
    fun `Return cached users without repositories`() {
        testSubject.getUsers().test()
        val result = testSubject.getUsersWithRepositoriesOfUser("Lares").test()

        result.assertValue(oneUserWithRepositories(emptyList()))
    }

    companion object {
        private val ONCE = Times(1)
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

        fun oneUserWithRepositories(repositories: List<RepositoryDTO>): List<UserDetails> = listOf(
            UserDetails(
                userDTO = DUMMY_USER,
                repositories = repositories
            )
        )
    }
}