package com.hcl.uberpoc.domain.use_case

import com.hcl.uberpoc.domain.entity.station.Stations
import com.hcl.uberpoc.domain.repository.StationRepository
import com.hcl.uberpoc.domain.use_case.base.SingleUseCase
import io.reactivex.Single

class GetStationsUseCase(
    private val stationsRepository: StationRepository
) : SingleUseCase<Unit, Stations> {
    override fun execute(data: Unit?): Single<Stations> = stationsRepository.getStations()
}