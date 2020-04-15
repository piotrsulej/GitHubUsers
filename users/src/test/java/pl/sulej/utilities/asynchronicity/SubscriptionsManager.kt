package pl.sulej.utilities.asynchronicity

val TEST_SUBSCRIPTIONS_MANAGER =
    SubscriptionsMapManager(
        TestSchedulerProvider()
    )