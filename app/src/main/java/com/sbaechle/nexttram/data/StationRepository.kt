package com.sbaechle.nexttram.data

import com.sbaechle.nexttram.data.restApi.RestAPI
import com.sbaechle.nexttram.data.restApi.Station
import com.sbaechle.nexttram.data.restApi.Stops
import io.reactivex.Observable

/**
 * Created by sbaechle on 25.02.2018.
 */
class StationRepository (private val api: RestAPI = RestAPI()) {
    fun getStationByName(stationName: String): Observable<List<Station>> {
        return api.getStations(stationName).switchMap { it: Stops -> Observable.just(it.stops) }
    }
}