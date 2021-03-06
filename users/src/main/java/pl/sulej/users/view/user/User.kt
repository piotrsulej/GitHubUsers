package pl.sulej.users.view.user

import pl.sulej.utilities.adapter.AdapterItem

data class User(
    val name: String,
    val avatarUrl: String,
    val repositoriesInfo: String,
    val isLoadingRepositories: Boolean
) : AdapterItem {

    override fun isTheSameAs(other: Any): Boolean = this.name == (other as? User)?.name
}