package pl.dybczak.githubusers.test

import org.junit.Test
import pl.dybczak.githubusers.robots.UsersPageRobot
import pl.dybczak.githubusers.utils.TestBase
import pl.sulej.users.view.UsersActivity

class UsersPageTest : TestBase <UsersActivity>(UsersActivity::class.java) {

    private val searchPage = UsersPageRobot()

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
        searchPage.assertUserDisplayed(username = "Diego")
        searchPage.assertUserNotDisplayed(username = "Gorn")
    }
}