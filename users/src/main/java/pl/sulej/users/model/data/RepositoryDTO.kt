package pl.sulej.users.model.data

import com.google.gson.annotations.SerializedName

data class RepositoryDTO(
    @SerializedName("name") val name: String
)