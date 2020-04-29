package pl.dybczak.githubusers.utils

import android.app.Activity
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

abstract class TestBase {

    @RunWith(JUnit4::class)
    abstract class TestBase<T : Activity>(activityClass: Class<T>) {

        @JvmField
        @Rule
        val ruleChain: RuleChain = BaseTestRule.ruleChain(activityClass)
    }
}