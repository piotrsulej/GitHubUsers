package pl.sulej.users.model.network

import com.google.gson.annotations.SerializedName

data class RepositoryDto(
    @SerializedName("name") val name: String
)