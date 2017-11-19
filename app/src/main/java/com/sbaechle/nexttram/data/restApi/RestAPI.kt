package com.sbaechle.nexttram.data.restApi

import retrofit2.Call
import retrofit2.Retrofit
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
                .build()
        kvvAPI = retrofit.create(KVVApi::class.java)
    }

    fun getDepartures(stationId: String): Call<DeparturesResponse> {
        return kvvAPI.getDepartures(stationId)
    }
}