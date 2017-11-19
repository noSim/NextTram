package com.sbaechle.nexttram.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by sbaechle on 19.11.2017.
 */

interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}