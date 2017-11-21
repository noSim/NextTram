package com.sbaechle.nexttram.display.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.sbaechle.nexttram.common.Bundleable
import com.sbaechle.nexttram.common.adapter.AdapterConstants
import com.sbaechle.nexttram.common.adapter.ViewType
import com.sbaechle.nexttram.common.createParcel

/**
 * Created by sbaechle on 19.11.2017.
 */

data class DepartureItem(var route: String, var destination: String, var direction: String, var time: String): ViewType, Parcelable, Bundleable {
    override fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString("route", route)
        bundle.putString("destination", destination)
        bundle.putString("direction", direction)
        bundle.putString("time", time)
        return bundle
    }

    override fun fromBundle(bundle: Bundle) {

    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<DepartureItem> = object : Parcelable.Creator<DepartureItem> {
            override fun createFromParcel(source: Parcel): DepartureItem = DepartureItem(source)
            override fun newArray(size: Int): Array<DepartureItem?> = arrayOfNulls(size)
        }
    }

    constructor(bundle: Bundle) : this(
            bundle.getString("route"),
            bundle.getString("destination"),
            bundle.getString("direction"),
            bundle.getString("time")
    )


    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun getViewType(): Int {
       return AdapterConstants.TRAIN_ARRIVAL
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(route)
        parcel.writeString(destination)
        parcel.writeString(direction)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }
}