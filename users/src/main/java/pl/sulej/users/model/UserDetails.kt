package pl.sulej.users.model

data class UserDetails(
    val login: String,
    val avatarUrl: String,
    val repositoryNames: List<String>? = null
)