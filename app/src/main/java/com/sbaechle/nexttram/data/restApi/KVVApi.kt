package com.sbaechle.nexttram.data.restApi

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
    ): Call<DeparturesResponse>
}