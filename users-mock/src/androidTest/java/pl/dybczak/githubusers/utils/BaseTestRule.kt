package pl.dybczak.githubusers.utils

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import org.junit.rules.RuleChain

object BaseTestRule {

    fun <T : Activity> ruleChain(activityClass: Class<T>, outerRuleChain: RuleChain = RuleChain.emptyRuleChain()): RuleChain {

        val activityRule = ActivityTestRule(activityClass)

        return RuleChain
            .outerRule(outerRuleChain)
            .around(activityRule)
    }
}