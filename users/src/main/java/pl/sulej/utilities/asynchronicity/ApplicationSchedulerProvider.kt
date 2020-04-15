package pl.sulej.utilities.asynchronicity

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationSchedulerProvider @Inject constructor() :
    SchedulerProvider {

    override fun observationScheduler(): Scheduler = AndroidSchedulers.mainThread()

    override fun subscriptionScheduler(): Scheduler = Schedulers.io()
}