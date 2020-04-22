package pl.sulej.utilities.view

import android.text.Editable
import android.text.TextWatcher

abstract class TextChangedListener : TextWatcher {

    override fun afterTextChanged(editable: Editable?) {}

    override fun beforeTextChanged(sequence: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(sequence: CharSequence, start: Int, count: Int, after: Int) {}
}