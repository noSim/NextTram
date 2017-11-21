package com.sbaechle.nexttram.common

import android.os.Bundle

/**
 * Created by sbaechle on 21.11.2017.
 */
interface Bundleable {
    fun toBundle (): Bundle
    fun fromBundle(bundle: Bundle)
}