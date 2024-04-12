package org.mothdigital.stationdistancechecker.stationspan.domain.model

import kotlin.math.roundToLong

@JvmInline
@Suppress("MemberVisibilityCanBePrivate")
value class Kilometers(val value: Long) {
    override fun toString(): String {
        return value.toString()
    }
}

fun Meters.roundToKm(): Kilometers =
    Kilometers((value / 1000.0).roundToLong())