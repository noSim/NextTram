package com.sbaechle.nexttram.display.model

import com.sbaechle.nexttram.common.adapter.AdapterConstants
import com.sbaechle.nexttram.common.adapter.ViewType

/**
 * Created by sbaechle on 19.11.2017.
 */

data class DepartureItem(var route: String, var destination: String, var direction: String, var time: String): ViewType {
    override fun getViewType(): Int {
       return AdapterConstants.TRAIN_ARRIVAL
    }
}