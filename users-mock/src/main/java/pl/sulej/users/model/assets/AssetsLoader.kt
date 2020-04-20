package pl.sulej.users.model.assets

interface AssetsLoader {

    fun loadTextAsset(path: String): String?
}