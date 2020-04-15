package pl.sulej.utils

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun subscriptionScheduler(): Scheduler

    fun observationScheduler(): Scheduler
}