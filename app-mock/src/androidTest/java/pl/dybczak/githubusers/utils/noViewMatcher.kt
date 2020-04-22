package pl.dybczak.githubusers.utils

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import org.hamcrest.Matcher

object noViewMatcher {

    fun noView(viewMatcher: Matcher<View>) {
        try {
            Espresso.onView(viewMatcher).perform(ViewActions.click())
            throw AssertionError("View was in hierarchy, but was expected to not exist")
        } catch (e: NoMatchingViewException) {
            // View - as expected - is not in hierarchy. We can proceed and ignore exception.
        }
    }

    fun <T> firstOf(matcher: Matcher<T>): Matcher<T> = FirstOfMatcher(matcher)
}