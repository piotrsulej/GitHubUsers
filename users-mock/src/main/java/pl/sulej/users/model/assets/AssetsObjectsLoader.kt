package pl.sulej.users.model.assets

import java.lang.reflect.Type

interface AssetsObjectsLoader {

    fun <ObjectType> read(path: String, type: Type): ObjectType

    fun <ObjectType> read(path: String, typeClass: Class<ObjectType>): ObjectType
}