package pl.sulej.users.view.data

import pl.sulej.utilities.adapter.AdapterItem

data class User(
    val name: String,
    val avatarUrl: String,
    val repositoryNames: List<String>
) : AdapterItem {

    override fun isTheSameAs(other: Any): Boolean = this.name == (other as? User)?.name
}