package pl.dybczak.githubusers.test

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.dybczak.githubusers.robots.UsersPageRobot
import pl.sulej.users.view.UsersActivity

@RunWith(AndroidJUnit4::class)
class UsersPageTest {

    private val searchPageRobot = UsersPageRobot()

    @get:Rule
    val activityRule = ActivityTestRule(UsersActivity::class.java)

    @Test
    fun assertAppNameDisplayed() {
        searchPageRobot.assertAppNameDisplayed()
    }

    @Test
    fun displayUserLogins() {
        searchPageRobot.assertUserDisplayed(username = "Gorn")
        searchPageRobot.assertUserDisplayed(username = "Diego")
    }

    @Test
    fun displayRepositories() {
        searchPageRobot.assertUserReposDisplayed(userRepos = "zemsta-gorna, zbroja-najemnika, new-camp")
        searchPageRobot.assertUserReposDisplayed(userRepos = "old-camp, miecz-bojowy, diegos_bogen")
    }

    @Test
    fun filterByLogin() {
        searchPageRobot.searchFor(username = "Gorn")
        searchPageRobot.assertUserDisplayed(username = "Gorn")
        searchPageRobot.assertUserNotDisplayed(username = "Diego")
    }

    @Test
    fun filterByRepo() {
        searchPageRobot.searchFor("old-camp")
        searchPageRobot.assertUserDisplayed("Diego")
        searchPageRobot.assertUserNotDisplayed("Gorn")
    }
}