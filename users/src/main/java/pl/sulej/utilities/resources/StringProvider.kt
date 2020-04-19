package pl.sulej.utilities.resources

import androidx.annotation.StringRes

interface StringProvider {

    fun getString(@StringRes resourceId: Int): String
}