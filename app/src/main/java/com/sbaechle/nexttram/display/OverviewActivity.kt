package com.sbaechle.nexttram.display

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.sbaechle.nexttram.R
import com.sbaechle.nexttram.data.DepartureRepository
import com.sbaechle.nexttram.display.adapter.TrainArrivalAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_overview.*

class OverviewActivity : AppCompatActivity() {

    private val maxSubscriptions: Int = 10

    private val departureList by lazy {
        departureTrainList.setHasFixedSize(true);
        departureTrainList.layoutManager = LinearLayoutManager(this);
        departureTrainList
    }

    private val departureRepo by lazy { DepartureRepository() }

    private var subscriptions = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        initAdapter();

        val subscription = departureRepo.getArrivals("id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {retrieveArrivals ->
                    (departureList.adapter as TrainArrivalAdapter).clearAndAddDepartures(retrieveArrivals.toMutableList())
                },
                {
                    e -> Log.d("OverviewActivity", e.message)}
        )
        subscriptions.add(subscription)
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    private fun initAdapter() {
        if (departureList.adapter == null) {
            departureList.adapter = TrainArrivalAdapter()
        }
    }

}
