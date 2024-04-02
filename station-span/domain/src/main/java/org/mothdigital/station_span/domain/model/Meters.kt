package org.mothdigital.station_span.domain.model

@JvmInline
value class Meters(val value: Long) {
    override fun toString(): String {
        return value.toString()
    }
}

fun Long.toMeters() = Meters(this)