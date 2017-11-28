package com.sbaechle.nexttram.widget.adapter

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sbaechle.nexttram.R
import com.sbaechle.nexttram.display.model.DepartureItem

/**
 * Created by sbaechle on 20.11.2017.
 */
class DepartureListProvider(context: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {

    private var items: ArrayList<DepartureItem>
    private val contxt: Context
    private val intent: Intent

    init {
        items = ArrayList()
        this.contxt = context
        this.intent = intent
    }

    fun updateWidgetListView() {
        items.clear()

        val departuresAsJson: String = intent.getStringExtra("departureList")
        val gson = Gson()
        var departureList: List<DepartureItem> = gson.fromJson(departuresAsJson, object : TypeToken<List<DepartureItem>>() {}.type)

        for (item in departureList)
        {
            items.add(item)
        }
    }

    override fun onCreate() {
        updateWidgetListView()
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(contxt.packageName, R.layout.empty_layout)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onDataSetChanged() {
        updateWidgetListView()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(contxt.packageName, R.layout.tram_arrival_item_widget)
        val thisItem = items.get(position)
        val direction: String = getDirectionString(thisItem.direction);
        remoteViews.setTextViewText(R.id.routeName, thisItem.route)
        remoteViews.setTextViewText(R.id.destination, thisItem.destination)
        remoteViews.setTextViewText(R.id.time, thisItem.time)
        remoteViews.setTextViewText(R.id.direction, direction)
        return remoteViews
    }

    private fun  getDirectionString(direction: String): String {
        if (direction.equals("1")) {
            return "<"
        }
        else if (direction.equals("2")) {
            return ">"
        }
        else {
            return " "
        }
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

}