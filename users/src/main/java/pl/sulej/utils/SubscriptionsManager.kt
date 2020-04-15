package pl.sulej.utils

import io.reactivex.Single

interface SubscriptionsManager {

    fun subscribe(tag: String, source: Single<*>)

    fun unsubscribe(tag: String)
}