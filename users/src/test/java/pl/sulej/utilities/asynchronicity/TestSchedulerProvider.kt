package pl.sulej.utilities.asynchronicity

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import pl.sulej.utilities.asynchronicity.SchedulerProvider

class TestSchedulerProvider :
    SchedulerProvider {

    override fun subscriptionScheduler(): Scheduler = Schedulers.trampoline()

    override fun observationScheduler(): Scheduler = Schedulers.trampoline()
}