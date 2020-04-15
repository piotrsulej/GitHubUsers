package pl.sulej.utilities.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class BaseAdapter(delegates: List<AdapterDelegate<List<AdapterItem>>>) :
    AsyncListDifferDelegationAdapter<AdapterItem>(

        object : DiffUtil.ItemCallback<AdapterItem>() {

            override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
                oldItem.isTheSameAs(newItem)

            override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
                oldItem.hasTheSameContent(newItem)
        }
    ) {

    init {
        delegates.forEach { delegate ->
            delegatesManager.addDelegate(delegate)
        }
    }
}