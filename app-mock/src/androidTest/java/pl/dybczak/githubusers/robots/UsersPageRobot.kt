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
        onView(withId(USER_SEARCH_ID)).perform(typeText(username))
    }

    fun assertUserDisplayed(username: String) {
        val username = allOf(withId(USERNAME_ID), withText(username))

        onView(username).check(matches(isDisplayed()))
    }

    fun assertAppNameDisplayed() {
        onView(withText(APP_NAME_TEXT)).check(matches(isDisplayed()))
    }

    fun assertUserNotDisplayed(username: String) {
        ViewMatchers.noView(allOf(withId(USERNAME_ID), withText(username)))
    }

    fun assertUserRepositoriesDisplayed(userRepos: String) {
        val userRepos = allOf(withId(USER_REPOSITORIES_ID), withText(userRepos))

        onView(userRepos).check(matches(isDisplayed()))
    }

    companion object {
        private val USERNAME_ID = R.id.user_name
        private val USER_SEARCH_ID = R.id.users_search
        private val USER_REPOSITORIES_ID = R.id.user_repositories
        private const val APP_NAME_TEXT = "GitHub Users (Mock)"
    }
}