package pl.sulej.users.presentation.data

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import pl.sulej.users.R
import pl.sulej.users.model.UserDetails
import pl.sulej.users.presentation.FilteredUserList
import pl.sulej.users.presentation.UsersConverter
import pl.sulej.users.view.user.User
import pl.sulej.utilities.resources.StringProvider

@RunWith(Parameterized::class)
class UsersConverterTest(private val testCase: TestCase) {

    private val stringProvider: StringProvider = mock {
        on { getString(R.string.user_no_repositories) } doReturn NO_REPOSITORIES
        on { getString(R.string.user_no_data_about_repositories) } doReturn NO_DATA_ABOUT_REPOSITORIES
    }
    private val testSubject =
        UsersConverter(stringProvider)

    @Test
    fun `Convert users`() {
        val list = FilteredUserList(testCase.inputUserDetails, testCase.inputSearchQuery)

        val result = testSubject.convert(list)

        assertThat(result).isEqualTo(testCase.expectedOutput)
    }

    data class TestCase(
        val inputUserDetails: List<UserDetails>,
        val inputSearchQuery: String = "",
        val expectedOutput: List<User>
    )

    companion object {

        private const val NO_DATA_ABOUT_REPOSITORIES = "Nie znam się na otwieraniu zamków."
        private const val NO_REPOSITORIES = "Niczego tu nie znajdę."

        @JvmStatic
        @Parameterized.Parameters
        fun testCases() = arrayOf(
            TestCase(
                inputUserDetails = listOf(createUserDetails("1"), createUserDetails("2")),
                expectedOutput = listOf(createUser("1"), createUser("2"))
            ),
            TestCase(
                inputUserDetails = listOf(createUserDetails("1"), createUserDetails("2")),
                inputSearchQuery = "user1",
                expectedOutput = listOf(createUser("1"))
            ),
            TestCase(
                inputUserDetails = listOf(createUserDetails("1"), createUserDetails("2")),
                inputSearchQuery = "repo1",
                expectedOutput = listOf(createUser("1"))
            ),
            TestCase(
                inputUserDetails = listOf(createUserDetails("1"), createUserDetails("2")),
                inputSearchQuery = "user2",
                expectedOutput = listOf(createUser("2"))
            ),
            TestCase(
                inputUserDetails = listOf(createUserDetails("1"), createUserDetails("2")),
                inputSearchQuery = "repo2",
                expectedOutput = listOf(createUser("2"))
            ),
            TestCase(
                inputUserDetails = listOf(createUserDetails("1"), createUserDetails("2")),
                inputSearchQuery = "repo",
                expectedOutput = listOf(createUser("1"), createUser("2"))
            ),
            TestCase(
                inputUserDetails = listOf(createUserDetails("1"), createUserDetails("2")),
                inputSearchQuery = "user",
                expectedOutput = listOf(createUser("1"), createUser("2"))
            ),
            TestCase(
                inputUserDetails = listOf(
                    createUserDetails("1").copy(repositoryNames = null),
                    createUserDetails("2")
                ),
                inputSearchQuery = "user",
                expectedOutput = listOf(
                    createUser("1").copy(repositoriesInfo = "", isLoadingRepositories = true),
                    createUser("2")
                )
            ),
            TestCase(
                inputUserDetails = listOf(
                    createUserDetails("1").copy(repositoryNames = emptyList()),
                    createUserDetails("2")
                ),
                inputSearchQuery = "user",
                expectedOutput = listOf(
                    createUser("1").copy(repositoriesInfo = NO_REPOSITORIES),
                    createUser("2")
                )
            )
        )

        private fun createUser(id: String) =
            User(
                name = "user$id",
                avatarUrl = "url$id",
                repositoriesInfo = "repo${id}x1, repo${id}x2",
                isLoadingRepositories = false
            )

        private fun createUserDetails(id: String) =
            UserDetails(
                login = "user$id",
                avatarUrl = "url$id",
                repositoryNames = listOf(
                    "repo${id}x1",
                    "repo${id}x2"
                )
            )
    }
}