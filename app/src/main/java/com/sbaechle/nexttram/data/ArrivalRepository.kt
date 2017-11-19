package com.sbaechle.nexttram.data

import com.sbaechle.nexttram.display.model.ArrivalItem
import io.reactivex.Observable


/**
 * Created by sbaechle on 19.11.2017.
 */

class ArrivalRepository {
    fun getArrivals(stationId: String): Observable<List<ArrivalItem>> {
        return Observable.create {
            subscriber ->
            //init dummy values
            val arrivals = mutableListOf<ArrivalItem>()
            for (i in 1..10) {
                arrivals.add(ArrivalItem(
                        "S8",
                        "Hagsfeld",
                        "1",
                        "5 min"
                ))
            }
            subscriber.onNext(arrivals);
        }
    }
}