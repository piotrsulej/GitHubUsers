package pl.dybczak.githubusers.utils

import android.app.Activity
import org.junit.ClassRule
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
abstract class TestBase<T : Activity>(activityClass: Class<T>) {

    @JvmField
    @Rule
    val ruleChain: RuleChain = BaseTestRule.ruleChain(activityClass)

    companion object {

        @JvmField
        @ClassRule
        val classRule = DisableAnimationsRule()
    }
}