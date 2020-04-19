package pl.sulej.utilities.asynchronicity

import io.reactivex.Observable

open class TestSubscriptionsMapManager : SubscriptionsManager {

    private val subscriptionsManager = SubscriptionsMapManager(TestSchedulerProvider.INSTANCE)

    override fun <Data> subscribe(
        tag: String,
        source: Observable<Data>,
        onNext: (Data) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        subscriptionsManager.subscribe(tag, source, onNext, onComplete, onError)
    }

    override fun unsubscribe(tag: String) {
        subscriptionsManager.unsubscribe(tag)
    }

    companion object {
        val INSTANCE = TestSubscriptionsMapManager()
    }
}