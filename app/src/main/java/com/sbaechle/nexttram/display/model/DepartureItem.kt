package com.sbaechle.nexttram.display.model

import android.os.Parcel
import android.os.Parcelable
import com.sbaechle.nexttram.common.adapter.AdapterConstants
import com.sbaechle.nexttram.common.adapter.ViewType

/**
 * Created by sbaechle on 19.11.2017.
 */

data class DepartureItem(var route: String, var destination: String, var direction: String, var time: String): ViewType, Parcelable {
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

    companion object {
        @JvmField final val CREATOR : Parcelable.Creator<DepartureItem> = object : Parcelable.Creator<DepartureItem>
        {
            override fun createFromParcel(parcel: Parcel): DepartureItem {
                return DepartureItem(parcel)
            }

            override fun newArray(size: Int): Array<DepartureItem?> {
                return arrayOfNulls(size)
            }
        }
    }
}