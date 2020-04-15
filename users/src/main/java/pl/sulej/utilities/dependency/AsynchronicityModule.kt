package pl.sulej.utilities.dependency

import dagger.Binds
import dagger.Module
import pl.sulej.utilities.ApplicationSchedulerProvider
import pl.sulej.utilities.SchedulerProvider
import pl.sulej.utilities.SubscriptionsManager
import pl.sulej.utilities.SubscriptionsMapManager

@Module
abstract class AsynchronicityModule {

    @Binds
    internal abstract fun bindSubscriptionsManager(manager: SubscriptionsMapManager): SubscriptionsManager

    @Binds
    internal abstract fun bindSchedulerProvider(provider: ApplicationSchedulerProvider): SchedulerProvider
}