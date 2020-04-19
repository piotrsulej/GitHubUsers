package pl.sulej.utilities.asynchronicity

import io.reactivex.Completable
import io.reactivex.Scheduler

fun Completable.fireAndForget(scheduler: Scheduler) {
    onErrorComplete()
        .subscribeOn(scheduler)
        .subscribe()
}