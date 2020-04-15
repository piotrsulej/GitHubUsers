package pl.sulej.users.view.data

data class User(
    val name: String,
    val avatarUrl: String,
    val repositoryNames: List<String>
)