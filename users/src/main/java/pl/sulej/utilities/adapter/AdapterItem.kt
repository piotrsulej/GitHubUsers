package pl.sulej.utilities.adapter

interface AdapterItem {

    fun isTheSameAs(other: Any): Boolean

    fun hasTheSameContent(other: Any): Boolean = this == other
}