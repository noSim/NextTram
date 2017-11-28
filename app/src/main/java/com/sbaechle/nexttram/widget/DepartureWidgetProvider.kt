package com.sbaechle.nexttram.widget

import android.app.PendingIntent
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
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by sbaechle on 20.11.2017.
 */

class DepartureWidgetProvider : AppWidgetProvider() {

    companion object {
        private val REFRESH_ACTION: String = "com.sbaechle.nexttram.departureWidgetProvider.REFRESH_ACTION"
    }

    private val departureRepo by lazy { DepartureRepository() }
    private val subscriptions= CompositeDisposable()

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        updateData(context)
        super.onUpdate(context, appWidgetManager, appWidgetIds)

    }

    override fun onReceive(context: Context?, intent: Intent) {
        val action: String = intent.action
        if (action.equals(REFRESH_ACTION)) {
            updateData(context)
        }

        super.onReceive(context, intent)
    }

    private fun updateData(context: Context?) {
        val subscription = departureRepo.getArrivals("id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {retrieveArrivals ->
                            showNewData(ArrayList(retrieveArrivals), AppWidgetManager.getInstance(context), context )
                        },
                        {
                            e -> Log.d("OverviewActivity", e.message)}
                )
        subscriptions.add(subscription)
    }

    private fun  showNewData(retrieveArrivals: ArrayList<DepartureItem>?, appWidgetManager: AppWidgetManager?,  context: Context?) {
        val view: RemoteViews = RemoteViews(context?.packageName, R.layout.departure_widget)

        val intent: Intent = Intent(context, DepartureWidgetService::class.java)
        // workaround to generate a new RemoteViewsFactory after every update
        val randomNumber = (Math.random() * 1000).toInt()
        intent.data = (Uri.fromParts("content", randomNumber.toString(), null))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, randomNumber)


        //put as json because Parcel doesen´t work here
        val gson = Gson()
        val list: String = gson.toJson(retrieveArrivals)
        intent.putExtra("departureList", list)

        view.setRemoteAdapter(R.id.departureTrainList, intent)
        view.setEmptyView(R.id.departureTrainList, R.id.empty_view)

        //refresh button
        val onRefreshClickIntent = Intent(context, DepartureWidgetProvider::class.java)
        onRefreshClickIntent.action = DepartureWidgetProvider.REFRESH_ACTION
        val refreshPendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0,
                onRefreshClickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btn_refresh, refreshPendingIntent)

        //update
        appWidgetManager?.updateAppWidget(ComponentName(context, DepartureWidgetProvider::class.java.name), null)
        appWidgetManager?.updateAppWidget(ComponentName(context, DepartureWidgetProvider::class.java.name), view)
        appWidgetManager?.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(ComponentName(context, DepartureWidgetProvider::class.java.name)), R.id.departureTrainList);
        //appWidgetManager?.updateAppWidget(widgetId, view)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        //subscriptions.dispose() //TODO wrong place to dispose -> find the right place
        super.onDeleted(context, appWidgetIds)
    }

}
