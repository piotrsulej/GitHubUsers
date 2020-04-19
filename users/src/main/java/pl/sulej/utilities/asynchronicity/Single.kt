package pl.sulej.utilities.asynchronicity

import io.reactivex.Scheduler
import io.reactivex.Single

fun Single<*>.fireAndForget(scheduler: Scheduler) {
    ignoreElement()
        .onErrorComplete()
        .subscribeOn(scheduler)
        .subscribe()
}