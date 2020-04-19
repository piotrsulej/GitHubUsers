package pl.sulej.users.presentation

import pl.sulej.users.model.UserDetails

data class FilteredUserList(
    val userDetails: List<UserDetails>,
    val searchQuery: String = ""
)