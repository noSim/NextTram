package com.sbaechle.nexttram.data.restApi

/**
 * Created by sbaechle on 19.11.2017.
 */
data class DeparturesResponse (
        val timestamp: String,
        val stopName: String,
        val departures: List<Departures>
)

data class Departures (
        val route: String,
        val destination: String,
        val direction: String,
        val time: String,
        val realtime: Boolean
)