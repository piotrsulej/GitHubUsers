package pl.sulej.utilities

import io.reactivex.Single

interface SubscriptionsManager {

    fun <Data> subscribe(
        tag: String,
        source: Single<Data>,
        onSuccess: (Data) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    )

    fun unsubscribe(tag: String)
}