package pl.sulej.utilities

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun subscriptionScheduler(): Scheduler

    fun observationScheduler(): Scheduler
}