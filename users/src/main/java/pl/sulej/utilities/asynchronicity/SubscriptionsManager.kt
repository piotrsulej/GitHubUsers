package pl.sulej.utilities.asynchronicity

import io.reactivex.Observable

interface SubscriptionsManager {

    fun <Data> subscribe(
        tag: String,
        source: Observable<Data>,
        onNext: (Data) -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    )

    fun unsubscribe(tag: String)
}