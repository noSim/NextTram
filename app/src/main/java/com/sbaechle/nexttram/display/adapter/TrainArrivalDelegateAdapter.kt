package com.sbaechle.nexttram.display.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sbaechle.nexttram.R
import com.sbaechle.nexttram.common.adapter.ViewType
import com.sbaechle.nexttram.common.adapter.ViewTypeDelegateAdapter
import com.sbaechle.nexttram.common.inflate
import com.sbaechle.nexttram.display.model.DepartureItem
import kotlinx.android.synthetic.main.tram_arrival_item.view.*

/**
 * Created by sbaechle on 19.11.2017.
 */

class TrainArrivalDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TrainArrivalHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TrainArrivalHolder
        holder.bind(item as DepartureItem)
    }

    class TrainArrivalHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.tram_arrival_item)) {

        fun bind(item: DepartureItem) = with(itemView) {
            routeName.text = item.route
            destination.text = item.destination
            time.text = item.time
        }
    }
}