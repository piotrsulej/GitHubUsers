package pl.sulej.users.model.assets

import com.google.gson.Gson
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

class GsonAssetsObjectsLoader @Inject constructor(
    private val assetsLoader: AssetsLoader
) : AssetsObjectsLoader {

    override fun <ObjectType> read(path: String, type: Type): ObjectType {
        val jsonText = assetsLoader.loadTextAsset(jsonAssetPath(path))
        return Gson().fromJson(jsonText, type) as ObjectType
    }

    override fun <ObjectType> read(path: String, typeClass: Class<ObjectType>): ObjectType {
        val jsonText = assetsLoader.loadTextAsset(jsonAssetPath(path))
        return Gson().fromJson(jsonText, typeClass)
    }

    private fun jsonAssetPath(path: String): String = "json/${path.toLowerCase(Locale.ROOT)}.json"
}