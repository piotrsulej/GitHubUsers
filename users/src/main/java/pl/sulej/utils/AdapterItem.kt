package pl.sulej.utils

interface AdapterItem {

    fun isTheSameAs(other: Any): Boolean

    fun hasTheSameContent(other: Any): Boolean = this == other
}