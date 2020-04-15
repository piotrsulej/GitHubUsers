package pl.sulej.users.model.data

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatar_url: String,
    @SerializedName("url") val url: String
)