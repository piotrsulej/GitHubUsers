package pl.sulej.utilities

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {

    override fun subscriptionScheduler(): Scheduler = Schedulers.trampoline()

    override fun observationScheduler(): Scheduler = Schedulers.trampoline()
}