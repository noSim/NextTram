package com.sbaechle.nexttram.display.model

import com.sbaechle.nexttram.common.adapter.AdapterConstants
import com.sbaechle.nexttram.common.adapter.ViewType

/**
 * Created by sbaechle on 20.11.2017.
 */

data class DirectionItem(val direction: String): ViewType {
    override fun getViewType(): Int {
        return AdapterConstants.TITLE
    }
}