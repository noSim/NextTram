package com.sbaechle.nexttram.settings

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.util.Log
import com.jakewharton.rxrelay2.PublishRelay
import com.sbaechle.nexttram.data.StationRepository
import com.sbaechle.nexttram.data.restApi.Station
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.InterruptedIOException
import java.util.concurrent.TimeUnit

/**
 * Created by sbaechle on 25.02.2018.
 */
class SettingsViewModel : ViewModel() {

    val TAG: String = "SettingsViewModel"
    private val stationRepo by lazy { StationRepository() }
    private val autoCompletePublishSubject = PublishRelay.create<String>()

    val searchResult: MutableLiveData<List<Station>> = MutableLiveData()
    val selectedStation: MutableLiveData<String> = MutableLiveData()

    companion object{
        fun create(activity: SettingsActivity): SettingsViewModel{
            var settingsViewModel = ViewModelProviders.of(activity).get(SettingsViewModel::class.java)
            settingsViewModel.configureAutoComplete()
            return settingsViewModel
        }
    }


    private fun configureAutoComplete() {
        autoCompletePublishSubject
                .debounce(300, TimeUnit.MILLISECONDS)
                .switchMap {stationRepo.getStationByName(it)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    onAutoCompleteResultReceived(result)
                }, {t: Throwable? -> run {
                    Log.d(TAG, "Failed to get search result: " + t?.message + t?.printStackTrace())
                    if (t is InterruptedIOException) configureAutoComplete() //TODO totally ugly workaround, has to be fixed! (Exception is thrown when letters are typed in fast!)
                }})

    }

    private fun  onAutoCompleteResultReceived(result: List<Station>) {
        searchResult.value = result
    }


    fun  onSearchTextChanged(name: String) {
        if (name.isNotEmpty()) {
            autoCompletePublishSubject.accept(name.trim())
        }
    }

    fun  selectStation(stationId: String) {
        selectedStation.value = stationId
        //TODO persist selection
    }
}