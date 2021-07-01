package com.hcl.uberpoc.domain.mapper

/**
 * Used to map objects
 */
interface Mapper<FROM, TO> {
    /**
     * Map object between chosen types
     * @param from - source object
     * @return target object
     */
    fun map(from: FROM): TO
}