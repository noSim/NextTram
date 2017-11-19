package com.sbaechle.nexttram.display.adapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.ViewGroup
import com.sbaechle.nexttram.common.adapter.AdapterConstants
import com.sbaechle.nexttram.common.adapter.ViewType
import com.sbaechle.nexttram.common.adapter.ViewTypeDelegateAdapter
import com.sbaechle.nexttram.display.model.DepartureItem
import com.sbaechle.nexttram.display.model.DirectionItem

/**
 * Created by sbaechle on 19.11.2017.
 */

class TrainArrivalAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArray<ViewTypeDelegateAdapter>()

    init {
        items = ArrayList()
        delegateAdapters.put(AdapterConstants.TRAIN_ARRIVAL, TrainArrivalDelegateAdapter())
        delegateAdapters.put(AdapterConstants.TITLE, TitleDelegateAdapter())
    }

    override fun getItemCount(): Int = items.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun clearAndAddDepartures(departures: MutableList<DepartureItem>) {
        items.clear()
        items.add(DirectionItem("1"))
        items.addAll(departures.filter { departure -> departure.direction.equals("1") })
        items.add(DirectionItem("2"))
        items.addAll(departures.filter { departure -> departure.direction.equals("2") })
        notifyDataSetChanged()
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

}