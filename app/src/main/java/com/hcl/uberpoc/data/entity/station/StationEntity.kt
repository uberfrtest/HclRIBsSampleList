package com.hcl.uberpoc.data.entity.station

data class StationEntity(
    val code: String? = null,
    val name: String? = null,
    val alias: List<String>? = null,
    val countryName: String? = null,
    val timeZoneCode: String? = null
)