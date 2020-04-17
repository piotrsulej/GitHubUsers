package pl.sulej.utilities.asynchronicity

import io.reactivex.Flowable

interface SubscriptionsManager {

    fun <Data> subscribe(
        tag: String,
        source: Flowable<Data>,
        onNext: (Data) -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    )

    fun unsubscribe(tag: String)
}