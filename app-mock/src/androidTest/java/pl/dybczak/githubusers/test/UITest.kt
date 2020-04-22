package pl.dybczak.githubusers.test

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.dybczak.githubusers.robots.SearchPageRobot
import pl.sulej.users.view.UsersActivity

@RunWith(AndroidJUnit4::class)
class UITest {

    private val searchPageRobot = SearchPageRobot()

    @get:Rule
    val activityRule = ActivityTestRule(UsersActivity::class.java)

    @Test
    fun assertUserRepoPageOpened() {
        searchPageRobot.assertThatGitHubUsersPageDisplayed(
            username = "Diego",
            usernameNoView = "Gorn",
            userRepos = "zemsta-gorna, zbroja-najemnika, new-camp"
        )
    }
}