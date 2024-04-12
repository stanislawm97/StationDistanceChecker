package org.mothdigital.stationdistancechecker.stationspan.domain.usecase

import org.mothdigital.stationdistancechecker.stationspan.domain.StationSpanRepository
import org.mothdigital.stationdistancechecker.stationspan.domain.model.Station
import org.mothdigital.stationdistancechecker.stationspan.domain.model.StationKeyword
import kotlin.coroutines.cancellation.CancellationException

/**
 * Use case responsible for fetching station keywords based on a query string.
 *
 * This use case encapsulates the logic for querying station keywords from the repository layer,
 * handling potential errors gracefully. It provides a simplified interface for UI components
 * to retrieve data related to station keywords, abstracting the underlying data sources.
 *
 * The operation is safe in terms of exception handling. Specifically, if a [CancellationException]
 * is thrown during the operation, it will be rethrown to properly handle coroutine cancellation.
 * Other exceptions will be caught, and an empty list will be returned as a fallback, ensuring
 * the stability of the application.
 *
 * @param query The search query string used to filter station keywords. The query is used to
 *              perform a partial or full match against the station keywords available.
 *
 * @return A list of [StationKeyword] objects that match the query string. If no matches are found
 *         or an exception occurs during the operation (except for [CancellationException]),
 *         an empty list is returned.
 */
class FetchStationKeywordsUseCase(
    private val stationSpanRepository: StationSpanRepository,
) {

    suspend operator fun invoke(query: String): List<Station> {
        return runCatching {
            stationSpanRepository.getStationByKeyword(query)
        }.onFailure {
            if(it is CancellationException) {
                throw it
            }
        }.getOrDefault(emptyList())
    }
}