package pl.sulej.utilities.dependency

import dagger.Binds
import dagger.Module
import pl.sulej.utilities.asynchronicity.ApplicationSchedulerProvider
import pl.sulej.utilities.asynchronicity.SchedulerProvider
import pl.sulej.utilities.asynchronicity.SubscriptionsManager
import pl.sulej.utilities.asynchronicity.SubscriptionsMapManager

@Module
abstract class AsynchronicityModule {

    @Binds
    internal abstract fun bindSubscriptionsManager(manager: SubscriptionsMapManager): SubscriptionsManager

    @Binds
    internal abstract fun bindSchedulerProvider(provider: ApplicationSchedulerProvider): SchedulerProvider
}