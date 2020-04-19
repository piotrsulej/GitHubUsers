package pl.sulej.utilities.asynchronicity

import io.reactivex.Observable
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
        source: Observable<Data>,
        onNext: (Data) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val subscription = source
            .subscribeOn(schedulerProvider.subscriptionScheduler())
            .observeOn(schedulerProvider.observationScheduler())
            .subscribe(onNext, onError, onComplete)

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