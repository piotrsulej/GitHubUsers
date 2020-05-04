package pl.dybczak.githubusers.utils

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class DisableAnimationsRule : TestRule {

    private val device: UiDevice by lazy { UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()) }
    private var transitionAnimationScale = ANIMATION_SCALE_STANDARD
    private var windowAnimationScale = ANIMATION_SCALE_STANDARD
    private var animatorDurationScale = ANIMATION_SCALE_STANDARD

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                saveCurrentAnimationsSettings()
                disableAnimations()
                try {
                    base.evaluate()
                } finally {
                    restoreAnimationsSettings()
                }
            }
        }
    }

    private fun saveCurrentAnimationsSettings() {
        transitionAnimationScale = getSettingValue(TRANSITION_ANIMATION_SCALE)
        windowAnimationScale = getSettingValue(WINDOW_ANIMATION_SCAlE)
        animatorDurationScale = getSettingValue(ANIMATOR_DURATION_SCALE)
    }

    private fun disableAnimations() {
        setSettingValue(TRANSITION_ANIMATION_SCALE, ANIMATION_SCALE_OFF)
        setSettingValue(WINDOW_ANIMATION_SCAlE, ANIMATION_SCALE_OFF)
        setSettingValue(ANIMATOR_DURATION_SCALE, ANIMATION_SCALE_OFF)
    }

    private fun restoreAnimationsSettings() {
        setSettingValue(TRANSITION_ANIMATION_SCALE, transitionAnimationScale)
        setSettingValue(WINDOW_ANIMATION_SCAlE, windowAnimationScale)
        setSettingValue(ANIMATOR_DURATION_SCALE, animatorDurationScale)
    }

    private fun getSettingValue(settingName: String): String =
        device.executeShellCommand("settings get global $settingName")

    private fun setSettingValue(settingName: String, value: String): String =
        device.executeShellCommand("settings put global $settingName $value")

    companion object {
        private const val TRANSITION_ANIMATION_SCALE = "transition_animation_scale"
        private const val WINDOW_ANIMATION_SCAlE = "window_animation_scale"
        private const val ANIMATOR_DURATION_SCALE = "animator_duration_scale"
        private const val ANIMATION_SCALE_OFF = "0.0"
        private const val ANIMATION_SCALE_STANDARD = "1.0"
    }
}