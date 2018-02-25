package com.sbaechle.nexttram.data.restApi

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by sbaechle on 19.11.2017.
 */

interface KVVApi {
    @GET("departures/bystop/{stationId}")
    fun getDepartures(@Path("stationId") stationId: String,
                      @Query("maxinfo") maxinfo: String = "10",
                      @Query("key") key: String = "377d840e54b59adbe53608ba1aad70e8"
    ): Observable<DeparturesResponse>

    @GET("stops/byname/{stopName}")
    fun getStationByName(@Path("stopName") stopName: String,
                         @Query("key") key: String = "377d840e54b59adbe53608ba1aad70e8"
    ): Observable<Stops>
}