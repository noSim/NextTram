package com.sbaechle.nexttram.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by sbaechle on 19.11.2017.
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot);
}