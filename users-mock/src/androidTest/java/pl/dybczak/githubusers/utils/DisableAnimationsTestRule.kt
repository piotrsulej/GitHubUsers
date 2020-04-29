package pl.dybczak.githubusers.utils

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.IOException

class DisableAnimationsRule : TestRule {

    private val device: UiDevice by lazy { UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()) }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                disableAnimations()
                try {
                    base.evaluate()
                } finally {
                    enableAnimations()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun disableAnimations() {
        device.executeShellCommand("settings put global transition_animation_scale 0")
        device.executeShellCommand("settings put global window_animation_scale 0")
        device.executeShellCommand("settings put global animator_duration_scale 0")
    }

    @Throws(IOException::class)
    private fun enableAnimations() {
        device.executeShellCommand("settings put global transition_animation_scale 1")
        device.executeShellCommand("settings put global window_animation_scale 1")
        device.executeShellCommand("settings put global animator_duration_scale 1")
    }
}