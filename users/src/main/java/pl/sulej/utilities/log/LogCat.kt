package pl.sulej.utilities.log

import android.util.Log
import javax.inject.Inject

class LogCat @Inject constructor() : Logger {

    override fun debugLog(tag: String, message: String) {
        Log.d(tag, message)
    }
}