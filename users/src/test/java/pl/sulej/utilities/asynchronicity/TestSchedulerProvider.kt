package pl.sulej.utilities.asynchronicity

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {

    override fun subscriptionScheduler(): Scheduler = Schedulers.trampoline()

    override fun observationScheduler(): Scheduler = Schedulers.trampoline()

    companion object {
        val INSTANCE = TestSchedulerProvider()
    }
}