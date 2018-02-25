package com.sbaechle.nexttram.data

import android.content.res.Resources
import com.sbaechle.nexttram.R
import com.sbaechle.nexttram.data.restApi.DeparturesResponse
import com.sbaechle.nexttram.data.restApi.RestAPI
import com.sbaechle.nexttram.display.model.DepartureItem
import io.reactivex.Observable


/**
 * Created by sbaechle on 19.11.2017.
 */

class DepartureRepository (private val api: RestAPI = RestAPI()) {
    fun getArrivals(stationId: String): Observable<List<DepartureItem>> {

        return api.getDepartures("de:8212:1").switchMap {
                response: DeparturesResponse -> Observable.just( response.departures.map {
                    val time = if (it.time.equals("0")) "jetzt" else it.time //TODO get string "jetzt" from ressources!
                    DepartureItem(it.route, it.destination, it.direction, time)
                    }
                )
        }
    }
}