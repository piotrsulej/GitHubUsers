package pl.sulej.utils

import io.reactivex.Single
import io.reactivex.disposables.Disposable

class SubscriptionsMapManager(
    private val schedulerProvider: SchedulerProvider
) : SubscriptionsManager {

    private val subscriptions: MutableMap<String, List<Disposable>> = mutableMapOf()

    override fun subscribe(tag: String, source: Single<*>) {
        val subscription = source
            .subscribeOn(schedulerProvider.subscriptionScheduler())
            .observeOn(schedulerProvider.observationScheduler())
            .subscribe()

        val currentSubscriptions = subscriptionsForTag(tag)
        subscriptions[tag] = currentSubscriptions + subscription
    }

    override fun unsubscribe(tag: String) {
        val subscriptionsForTag = subscriptionsForTag(tag)
        subscriptionsForTag.forEach(Disposable::dispose)
        subscriptions.remove(tag)
    }

    private fun subscriptionsForTag(tag: String) = subscriptions[tag].orEmpty()
}