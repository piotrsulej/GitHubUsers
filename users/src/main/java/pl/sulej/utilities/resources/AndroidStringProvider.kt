package pl.sulej.utilities.resources

import android.content.res.Resources
import javax.inject.Inject

class AndroidStringProvider @Inject constructor(private val resources: Resources) : StringProvider {

    override fun getString(resourceId: Int) = resources.getString(resourceId)
}