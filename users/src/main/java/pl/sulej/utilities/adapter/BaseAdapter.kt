package pl.sulej.utilities.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

open class BaseAdapter : AsyncListDifferDelegationAdapter<AdapterItem>(
    object : DiffUtil.ItemCallback<AdapterItem>() {

        override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
            oldItem.isTheSameAs(newItem)

        override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
            oldItem.hasTheSameContent(newItem)
    }
)