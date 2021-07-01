package com.hcl.uberpoc.data.repository

import com.hcl.uberpoc.data.entity.station.StationsEntity
import com.hcl.uberpoc.data.network.service.StationNetworkService
import com.hcl.uberpoc.domain.entity.station.Stations
import com.hcl.uberpoc.domain.mapper.Mapper
import com.hcl.uberpoc.domain.repository.StationRepository
import io.reactivex.Single

class StationRepositoryImpl(
    private val stationNetworkService: StationNetworkService,
    private val stationsMapper: Mapper<StationsEntity, Stations>
) : StationRepository {
    override fun getStations(): Single<Stations> =
        stationNetworkService.getStations().map { stationsMapper.map(it) }
}