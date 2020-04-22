package pl.dybczak.githubusers.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import pl.dybczak.githubusers.utils.noViewMatcher
import pl.sulej.users.R

class SearchPageRobot {

    fun assertThatGitHubUsersPageDisplayed(username: String, usernameNoView: String, userRepos: String) {
        assertAppNameDisplayed()
        assertUsernameDisplayed(username)
        assertUserReposDisplayed(userRepos)
        searchForUser(username)
        assertThatSearchForUserWorks(usernameNoView)

    }

    private fun searchForUser(username: String) {
        onView(withId(userSearchId)).perform(typeText(username))
    }

    private fun assertUsernameDisplayed(username: String) {
        val username = allOf(withId(username_id), withText(username))

        onView(username).check(matches(isDisplayed()))
    }

    private fun assertAppNameDisplayed() {
        onView(withText(APP_NAME_TEXT)).check(matches(isDisplayed()))
    }

    private fun assertThatSearchForUserWorks(username: String) {
        noViewMatcher.noView(allOf(withId(username_id), withText(username)))
    }

    private fun assertUserReposDisplayed(userRepos: String) {
        val userRepos = allOf(withId(userRepoId), withText(userRepos))

        onView(userRepos).check(matches(isDisplayed()))
    }

    companion object {
        private val username_id = R.id.user_name
        private val userSearchId = R.id.users_search
        private val userRepoId = R.id.user_repositories
        private const val APP_NAME_TEXT = "GitHub Users (Mock)"
    }
}