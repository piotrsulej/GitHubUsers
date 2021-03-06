package pl.sulej.users.model.assets

import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import pl.sulej.users.model.network.GitHubUsersApi
import pl.sulej.users.model.network.RepositoryDto
import pl.sulej.users.model.network.UserDto
import pl.sulej.utilities.asynchronicity.asSingle
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result
import javax.inject.Inject

class MockAssetsGitHubUsersApi @Inject constructor(
    private val assetsObjectsLoader: AssetsObjectsLoader
) : GitHubUsersApi {

    override fun getUsers(): Single<Result<List<UserDto>>> {
        val classType = object : TypeToken<List<UserDto>>() {}.type
        val response = assetsObjectsLoader.read<List<UserDto>>(
            path = "users",
            type = classType
        )
        return Result.response(Response.success(response)).asSingle()
    }

    override fun getUserRepositories(userLogin: String): Single<List<RepositoryDto>> {
        val classType = object : TypeToken<List<RepositoryDto>>() {}.type
        return assetsObjectsLoader.read<List<RepositoryDto>>(
            path = "users/$userLogin/repos",
            type = classType
        ).asSingle()
    }
}