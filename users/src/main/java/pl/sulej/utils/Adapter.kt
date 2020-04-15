package pl.sulej.utils

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

open class Adapter : AsyncListDifferDelegationAdapter<AdapterItem>(
    object : DiffUtil.ItemCallback<AdapterItem>() {

        override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
            oldItem.isTheSameAs(newItem)

        override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
            oldItem.hasTheSameContent(newItem)
    }
)