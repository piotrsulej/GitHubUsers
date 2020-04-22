package pl.dybczak.githubusers.test

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.sulej.users.R
import pl.sulej.users.view.UsersActivity
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import android.widget.EditText
import android.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher


@RunWith(AndroidJUnit4::class)
class UITest {

    @get:Rule
    val activityRule = ActivityTestRule(UsersActivity::class.java)


    @Test
    fun assertUserNameDisplayed() {
//        onView(withText("GitHub Users (Mock)")).check(matches(isDisplayed()))
//        onView(allOf(withId(R.id.user_name), withText("Diego"))).check(matches(isDisplayed()))
//        onView(allOf(withId(R.id.user_name), withText("Gorn"))).check(matches(isDisplayed()))

        onView(withId(R.id.users_search)).perform(click())

        onView(isAssignableFrom(SearchView::class.java)).perform(typeSearchViewText("Diego"))

        sleep()


    }

    fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Change view text"
            }
            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }
            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }

    private fun sleep() {
        Thread.sleep(3000)
    }
}