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

data class Stops (
        val stops: List<Station>
)

data class Station (
        val id: String,
        val name: String,
        val lat: String,
        val lon: String
)