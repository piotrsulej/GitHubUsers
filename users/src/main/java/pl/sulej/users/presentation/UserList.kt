package pl.sulej.users.presentation

import pl.sulej.users.model.data.UserDetails

data class UserList(
    val users: List<UserDetails> = emptyList(),
    val searchQuery: String = ""
)