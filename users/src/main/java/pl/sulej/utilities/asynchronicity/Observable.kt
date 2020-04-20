package pl.sulej.utilities.asynchronicity

import io.reactivex.Observable

fun <Type> Type?.asObservable(): Observable<Type>? = this?.let { Observable.just(this) }