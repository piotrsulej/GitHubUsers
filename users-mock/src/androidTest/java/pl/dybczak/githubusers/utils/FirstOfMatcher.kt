package pl.dybczak.githubusers.utils

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class FirstOfMatcher<T>(private val matcher: Matcher<T>) : BaseMatcher<T>() {
    private var first = true

    override fun matches(item: Any?): Boolean {
        if (first && matcher.matches(item)) {
            first = false
            return true
        }
        return false
    }

    override fun describeTo(description: Description?) {
        description?.appendText("return first matching element")
    }
}