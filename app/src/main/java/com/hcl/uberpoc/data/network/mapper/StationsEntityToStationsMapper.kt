package com.hcl.uberpoc.data.network.mapper

import com.hcl.uberpoc.data.entity.station.StationEntity
import com.hcl.uberpoc.data.entity.station.StationsEntity
import com.hcl.uberpoc.domain.entity.station.Station
import com.hcl.uberpoc.domain.entity.station.Stations
import com.hcl.uberpoc.domain.mapper.Mapper

class StationsEntityToStationsMapper(
    private val stationMapper: Mapper<StationEntity, Station>
): Mapper<StationsEntity, Stations> {

    override fun map(from: StationsEntity): Stations = Stations(
        from.stations?.map { stationMapper.map(it) } ?: listOf()
    )
}