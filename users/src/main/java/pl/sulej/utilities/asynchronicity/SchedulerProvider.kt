package pl.sulej.utilities.asynchronicity

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun subscriptionScheduler(): Scheduler

    fun observationScheduler(): Scheduler
}