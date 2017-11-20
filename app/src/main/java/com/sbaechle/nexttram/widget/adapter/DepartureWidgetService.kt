package com.sbaechle.nexttram.widget.adapter

import android.content.Intent
import android.widget.RemoteViewsService
import com.sbaechle.nexttram.display.model.DepartureItem

/**
 * Created by sbaechle on 20.11.2017.
 */
class DepartureWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return DepartureListProvider(applicationContext, intent)
    }
}