package pl.sulej.users.model.network

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)