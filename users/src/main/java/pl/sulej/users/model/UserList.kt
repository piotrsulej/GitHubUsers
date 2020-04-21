package pl.sulej.users.model

data class UserList(val users: List<UserDetails> = emptyList(), val error: Throwable? = null)