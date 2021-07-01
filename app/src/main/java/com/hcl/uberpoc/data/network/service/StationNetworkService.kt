package com.hcl.uberpoc.data.network.service

import com.hcl.uberpoc.data.entity.station.StationsEntity
import io.reactivex.Single
import retrofit2.http.GET

interface StationNetworkService {
    @GET("static/stations.json")
    fun getStations(): Single<StationsEntity>
}