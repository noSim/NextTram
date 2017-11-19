package com.sbaechle.nexttram.display

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.sbaechle.nexttram.R
import com.sbaechle.nexttram.data.ArrivalRepository
import com.sbaechle.nexttram.display.adapter.TrainArrivalAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_overview.*

class OverviewActivity : AppCompatActivity() {

    private val maxSubscriptions: Int = 10

    private val arrivalList by lazy {
        arrivingTrainList.setHasFixedSize(true);
        arrivingTrainList.layoutManager = LinearLayoutManager(this);
        arrivingTrainList
    }

    private val arrivalRepo by lazy { ArrivalRepository() }

    private var subscriptions = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        initAdapter();

        val subscription = arrivalRepo.getArrivals("id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {retrieveArrivals ->
                    (arrivalList.adapter as TrainArrivalAdapter).addArrivals(retrieveArrivals.toMutableList())
                },
                { e -> Log.d("OverviewActivity", e.message)}
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
        if (arrivalList.adapter == null) {
            arrivalList.adapter = TrainArrivalAdapter()
        }
    }

}
