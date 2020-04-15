package pl.sulej.users.model.data

data class UserDetails(
    val userDTO: UserDTO,
    val repositories: List<RepositoryDTO>
)