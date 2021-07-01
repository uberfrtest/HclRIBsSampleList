package com.hcl.uberpoc.data.network.mapper

import com.hcl.uberpoc.data.entity.station.StationEntity
import com.hcl.uberpoc.domain.entity.station.Station
import com.hcl.uberpoc.domain.entity.station.StationDescription
import com.hcl.uberpoc.domain.mapper.Mapper
import com.hcl.uberpoc.domain.utils.toAlphaNumeric
import com.hcl.uberpoc.domain.utils.toLowerCaseDefault

class StationEntityToStationMapper : Mapper<StationEntity, Station> {

    override fun map(from: StationEntity): Station {
        val stationDescription = StationDescription(
            from.code ?: "",
            from.name ?: "",
            from.countryName ?: "",
            from.timeZoneCode ?: ""
        )

        return Station(
            provideKeyWords(from.alias, stationDescription),
            stationDescription
        )
    }

    private fun provideKeyWords(
        alias: List<String>?,
        stationDescription: StationDescription
    ): Set<String> {
        return mutableSetOf<String>().apply {
            addAll(alias ?: listOf())
            add(stationDescription.code.toLowerCaseDefault())
            add(stationDescription.name.toLowerCaseDefault().toAlphaNumeric())
            add(stationDescription.countryName.toLowerCaseDefault())
        }
    }
}