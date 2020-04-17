package pl.sulej.users.presentation

import pl.sulej.users.model.data.UserDetails

data class UserList(
    val userDetails: List<UserDetails> = emptyList(),
    val searchQuery: String = ""
)