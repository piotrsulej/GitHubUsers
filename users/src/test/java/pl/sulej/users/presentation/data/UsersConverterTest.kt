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

        val expectedResult = listOf(dummyUser(), OTHER_USER)
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Show expanded user`() {
        val list = UserList(DUMMY_USER_DETAILS, expandedUserNames = listOf(DUMMY_USER_LOGIN))

        val result = testSubject.convert(list)

        val expectedResult = listOf(dummyUser(expanded = true), OTHER_USER)
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Show filtered user`() {
        val list = UserList(DUMMY_USER_DETAILS, searchQuery = DUMMY_USER_LOGIN)

        val result = testSubject.convert(list)

        val expectedResult = listOf(dummyUser())
        assertThat(result).isEqualTo(expectedResult)
    }

    companion object {
        private const val DUMMY_USER_LOGIN = "user1"
        private val DUMMY_USER_DETAILS = listOf(
            UserDetails(
                userDTO = UserDTO(login = DUMMY_USER_LOGIN, avatarUrl = "url1"),
                repositories = listOf(
                    RepositoryDTO("repo1x1"),
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
            repositoryNames = "repo2x1\nrepo2x2",
            detailsExpanded = false
        )

        private fun dummyUser(expanded: Boolean = false): User {
            return User(
                name = "user1",
                avatarUrl = "url1",
                repositoryNames = "repo1x1\nrepo1x2",
                detailsExpanded = expanded
            )
        }
    }
}