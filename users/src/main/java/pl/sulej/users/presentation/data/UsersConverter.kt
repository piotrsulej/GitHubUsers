package pl.sulej.users.presentation.data

import pl.sulej.utils.Converter
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.view.data.User

class UsersConverter : Converter<UserDTO, User> {

    override fun convert(input: UserDTO): User =
        User(
            name = input.login,
            avatarUrl = input.avatar_url,
            repositoryNames = emptyList()
        )
}