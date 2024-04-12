package org.mothdigital.stationdistancechecker.stationspan.domain.usecase

import org.mothdigital.stationdistancechecker.stationspan.domain.model.Meters
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToLong
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Use case for calculating the great-circle distance between two points on the Earth's surface.
 *
 * Utilizes the Haversine formula to calculate the distance, taking into account the spherical shape of the Earth.
 * The distance is calculated based on the latitude and longitude of the two points, given in degrees.
 *
 * @param lat1 Latitude of the first point in degrees.
 * @param lon1 Longitude of the first point in degrees.
 * @param lat2 Latitude of the second point in degrees.
 * @param lon2 Longitude of the second point in degrees.
 *
 * @return The distance between the two points in meters, encapsulated in a [Meters] object.
 *         Returns [Meters] with value 0 if any of the input coordinates is null.
 */
class CalculateDistanceUseCase {

    operator fun invoke(
        lat1: Double?,
        lon1: Double?,
        lat2: Double?,
        lon2: Double?
    ): Meters {
        if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
            return Meters(0)
        }

        val earthRadius = 6371e3

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return Meters((earthRadius * c).roundToLong())
    }
}