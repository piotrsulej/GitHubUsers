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

    private val searchPage = UsersPageRobot()

    @get:Rule
    val activityRule = ActivityTestRule(UsersActivity::class.java)

    @Test
    fun displayAppName() {
        searchPage.assertAppNameDisplayed()
    }

    @Test
    fun displayUserLogins() {
        searchPage.assertUserDisplayed(username = "Gorn")
        searchPage.assertUserDisplayed(username = "Diego")
    }

    @Test
    fun displayRepositories() {
        searchPage.assertUserRepositoriesDisplayed(repositories = "zemsta-gorna, zbroja-najemnika, new-camp")
        searchPage.assertUserRepositoriesDisplayed(repositories = "old-camp, miecz-bojowy, diegos_bogen")
    }

    @Test
    fun filterByLogin() {
        searchPage.searchFor("Gorn")
        searchPage.assertUserDisplayed(username = "Gorn")
        searchPage.assertUserNotDisplayed(username = "Diego")
    }

    @Test
    fun filterByRepo() {
        searchPage.searchFor("old-camp")
        searchPage.assertUserDisplayed("Diego")
        searchPage.assertUserNotDisplayed("Gorn")
    }
}