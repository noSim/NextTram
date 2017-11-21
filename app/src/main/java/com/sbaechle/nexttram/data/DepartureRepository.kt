package com.sbaechle.nexttram.data

import com.sbaechle.nexttram.data.restApi.RestAPI
import com.sbaechle.nexttram.display.model.DepartureItem
import io.reactivex.Observable


/**
 * Created by sbaechle on 19.11.2017.
 */

class DepartureRepository (private val api: RestAPI = RestAPI()) {
    fun getArrivals(stationId: String): Observable<List<DepartureItem>> {
        return Observable.create {
            subscriber ->

            val callResponse = api.getDepartures("de:8212:1")
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val departures = response.body().departures.map {
                    val time = if (it.time.equals("0")) "jetzt" else it.time
                    DepartureItem(it.route, it.destination, it.direction, it.time)
                }
                subscriber.onNext(departures)

            } else {
              subscriber.onError(Throwable(response.message()))
            }

        }
    }
}