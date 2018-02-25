package com.sbaechle.nexttram.data.restApi

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by sbaechle on 19.11.2017.
 */

class RestAPI {
    private val kvvAPI: KVVApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://live.kvv.de/webapp/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        kvvAPI = retrofit.create(KVVApi::class.java)
    }

    fun getDepartures(stationId: String): Observable<DeparturesResponse> {
        return kvvAPI.getDepartures(stationId)
    }

    fun getStations(name: String): Observable<Stops> {
        return kvvAPI.getStationByName(name)
    }
}