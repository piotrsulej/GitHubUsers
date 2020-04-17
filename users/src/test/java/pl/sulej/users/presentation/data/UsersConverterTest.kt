package pl.sulej.users.presentation.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import pl.sulej.users.model.data.RepositoryDTO
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.presentation.UserList
import pl.sulej.users.view.data.User

class UsersConverterTest {

    private val testSubject = UsersConverter()

    @Test
    fun `Convert users`() {
        val list = UserList(DUMMY_USER_DETAILS)

        val result = testSubject.convert(list)

        val expectedResult = listOf(DUMMY_USER, OTHER_USER)
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Search for user login`() {
        val list = UserList(DUMMY_USER_DETAILS, searchQuery = DUMMY_LOGIN)

        val result = testSubject.convert(list)

        val expectedResult = listOf(DUMMY_USER)
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Search for repository names`() {
        val list = UserList(DUMMY_USER_DETAILS, searchQuery = DUMMY_REPO)

        val result = testSubject.convert(list)

        val expectedResult = listOf(DUMMY_USER)
        assertThat(result).isEqualTo(expectedResult)
    }

    companion object {
        private const val DUMMY_LOGIN = "user1"
        private const val DUMMY_REPO = "repo1x1"
        private val DUMMY_USER_DETAILS = listOf(
            UserDetails(
                userDTO = UserDTO(login = DUMMY_LOGIN, avatarUrl = "url1"),
                repositories = listOf(
                    RepositoryDTO(DUMMY_REPO),
                    RepositoryDTO("repo1x2")
                )
            ),
            UserDetails(
                userDTO = UserDTO(login = "user2", avatarUrl = "url2"),
                repositories = listOf(
                    RepositoryDTO("repo2x1"),
                    RepositoryDTO("repo2x2")
                )
            )
        )
        private val OTHER_USER = User(
            name = "user2",
            avatarUrl = "url2",
            repositoryNames = "repo2x1, repo2x2"
        )

        private val DUMMY_USER = User(
            name = DUMMY_LOGIN,
            avatarUrl = "url1",
            repositoryNames = "$DUMMY_REPO, repo1x2"
        )
    }
}