package com.sbaechle.nexttram.settings.searchAdapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.sbaechle.nexttram.R
import com.sbaechle.nexttram.common.inflate
import com.sbaechle.nexttram.data.restApi.Station
import com.sbaechle.nexttram.settings.SettingsViewModel
import kotlinx.android.synthetic.main.station_search_item.view.*



/**
 * Created by sbaechle on 25.02.2018.
 */
class StationListAdapter(val viewModel: SettingsViewModel?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<Station> = ArrayList()

    //TODO maybe use DifUtil here
    fun swap(stations: List<Station>?) {
        if (stations != null) {
            items.clear()
            items.addAll(stations)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as StationViewHolder
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StationViewHolder(parent)
    }

    inner class StationViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.station_search_item)) {
        fun bind(station: Station) = with(itemView) {
            stationName.text = station.name
            stationName.setOnClickListener(OnStationClickListener(station.id))
        }
    }

    inner class OnStationClickListener(var stationId: String): View.OnClickListener {
        override fun onClick(p0: View?) {
            viewModel?.selectStation(stationId)
        }
    }

}