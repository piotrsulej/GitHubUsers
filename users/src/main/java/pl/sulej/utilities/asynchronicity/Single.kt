package pl.sulej.utilities.asynchronicity

import io.reactivex.Scheduler
import io.reactivex.Single

fun Single<*>.fireAndForget(scheduler: Scheduler) {
    ignoreElement()
        .onErrorComplete()
        .subscribeOn(scheduler)
        .subscribe()
}

fun <Type> Type?.asSingle(): Single<Type>? = this?.let { Single.just(it) }