package com.sbaechle.nexttram.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import com.sbaechle.nexttram.R
import com.sbaechle.nexttram.data.DepartureRepository
import com.sbaechle.nexttram.display.model.DepartureItem
import com.sbaechle.nexttram.widget.adapter.DepartureWidgetService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.content.ComponentName



/**
 * Created by sbaechle on 20.11.2017.
 */

class DepartureWidgetProvider : AppWidgetProvider() {

    private val departureRepo by lazy { DepartureRepository() }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {

        if (appWidgetIds != null) {
            val widgetCount = appWidgetIds.size - 1
            for (i in 0..widgetCount) {
                val widgetId = appWidgetIds[i]

                val subscription = departureRepo.getArrivals("id")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {retrieveArrivals ->
                                    showNewData(ArrayList(retrieveArrivals), appWidgetManager, widgetId, context )
                                },
                                {
                                    e -> Log.d("OverviewActivity", e.message)}
                        )
            }
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)

    }

    private fun  showNewData(retrieveArrivals: ArrayList<DepartureItem>?, appWidgetManager: AppWidgetManager?, widgetId: Int, context: Context?) {
        val view: RemoteViews = RemoteViews(context?.packageName, R.layout.departure_widget)
        view.setTextViewText(R.id.stationName, retrieveArrivals!!.get(1).destination)

        val intent: Intent = Intent(context, DepartureWidgetService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
        intent.putExtra("hanspeter", "geht das?")
        //intent.putParcelableArrayListExtra("departure_data",retrieveArrivals)
        view.setRemoteAdapter(R.id.departureTrainList, intent)
        view.setEmptyView(R.id.departureTrainList, R.id.empty_view)

        //val cn = ComponentName(context, DepartureWidgetProvider::class.java)
        //appWidgetManager?.notifyAppWidgetViewDataChanged(widgetId, R.id.departureTrainList);
        appWidgetManager?.updateAppWidget(widgetId, view)
    }

}
