package pl.dybczak.githubusers.utils

import com.azimolabs.conditionwatcher.ConditionWatcher
import com.azimolabs.conditionwatcher.Instruction

fun waitForEspressoCondition(condition: () -> Unit) {
    val instruction = object : Instruction() {

        override fun getDescription(): String = "Waiting for custom condition."

        override fun checkCondition(): Boolean {
            try {
                condition()
            } catch (exception: Throwable) {
                return false
            }
            return true
        }
    }
    ConditionWatcher.waitForCondition(instruction)
}