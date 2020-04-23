package pl.dybczak.githubusers.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import pl.dybczak.githubusers.utils.ViewMatchers
import pl.sulej.users.R

class UsersPageRobot {

    fun searchFor(searchQuery: String) {
        onView(withId(USER_SEARCH_ID)).perform(typeText(searchQuery))
    }

    fun assertUserDisplayed(username: String) {
        val usernameView = allOf(withId(USERNAME_ID), withText(username))

        onView(usernameView).check(matches(isDisplayed()))
    }

    fun assertAppNameDisplayed() {
        onView(withText(APP_NAME_TEXT)).check(matches(isDisplayed()))
    }

    fun assertUserNotDisplayed(username: String) {
        ViewMatchers.noView(allOf(withId(USERNAME_ID), withText(username)))
    }

    fun assertUserRepositoriesDisplayed(repositories: String) {
        val repositoriesView = allOf(withId(USER_REPOSITORIES_ID), withText(repositories))

        onView(repositoriesView).check(matches(isDisplayed()))
    }

    companion object {
        private val USERNAME_ID = R.id.user_name
        private val USER_SEARCH_ID = R.id.users_search
        private val USER_REPOSITORIES_ID = R.id.user_repositories
        private const val APP_NAME_TEXT = "GitHub Users (Mock)"
    }
}