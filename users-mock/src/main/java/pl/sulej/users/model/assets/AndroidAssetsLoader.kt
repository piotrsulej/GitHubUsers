package pl.sulej.users.model.assets

import android.content.Context
import java.io.IOException
import javax.inject.Inject

class AndroidAssetsLoader @Inject constructor(private val context: Context) : AssetsLoader {

    override fun loadTextAsset(path: String): String? =
        try {
            val input = context.assets.open(path)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            java.lang.String(buffer).toString()
        } catch (ioException: IOException) {
            null
        }
}