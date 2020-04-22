package pl.dybczak.githubusers.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import pl.dybczak.githubusers.utils.ViewMatchers
import pl.sulej.users.R

class UsersPageRobot {

    fun searchFor(username: String) {
        onView(withId(userSearchId)).perform(typeText(username))
    }

    fun assertUserDisplayed(username: String) {
        val username = allOf(withId(username_id), withText(username))

        onView(username).check(matches(isDisplayed()))
    }

    fun assertAppNameDisplayed() {
        onView(withText(APP_NAME_TEXT)).check(matches(isDisplayed()))
    }

    fun assertUserNotDisplayed(username: String) {
        ViewMatchers.noView(allOf(withId(username_id), withText(username)))
    }

    fun assertUserReposDisplayed(userRepos: String) {
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