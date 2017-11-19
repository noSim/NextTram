package com.sbaechle.nexttram.display.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sbaechle.nexttram.R
import com.sbaechle.nexttram.common.adapter.ViewType
import com.sbaechle.nexttram.common.adapter.ViewTypeDelegateAdapter
import com.sbaechle.nexttram.common.inflate
import com.sbaechle.nexttram.display.model.DirectionItem
import kotlinx.android.synthetic.main.tram_arrival_item_header.view.*

/**
 * Created by sbaechle on 19.11.2017.
 */
class TitleDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TitleViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TitleViewHolder
        holder.bind(item as DirectionItem)
    }

    class TitleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.tram_arrival_item_header)) {
        fun bind(direction: DirectionItem) = with(itemView) {
            if (direction.direction.equals("1"))
            title.text =  "->"
            else title.text = "<-"
        }
    }

}