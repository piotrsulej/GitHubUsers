package pl.sulej.utilities.asynchronicity

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionsMapManager @Inject constructor(
    private val schedulerProvider: SchedulerProvider
) : SubscriptionsManager {

    private val subscriptions: MutableMap<String, List<Disposable>> = mutableMapOf()

    override fun <Data> subscribe(
        tag: String,
        source: Single<Data>,
        onSuccess: (Data) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val subscription = source
            .subscribeOn(schedulerProvider.subscriptionScheduler())
            .observeOn(schedulerProvider.observationScheduler())
            .subscribe(onSuccess, onError)

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