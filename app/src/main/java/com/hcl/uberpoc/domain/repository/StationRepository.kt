package com.hcl.uberpoc.domain.repository

import com.hcl.uberpoc.domain.entity.station.Stations
import io.reactivex.Single

/**
 * Used for interaction with stations data
 */
interface StationRepository {
    /**
     * Get all stations
     *
     * @return Stations
     */
    fun getStations(): Single<Stations>
}