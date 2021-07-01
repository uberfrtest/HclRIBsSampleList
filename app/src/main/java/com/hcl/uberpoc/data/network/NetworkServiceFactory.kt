package com.hcl.uberpoc.data.network

/**
 * Used for network services creation
 */
interface NetworkServiceFactory {
    /**
     * Create network service implementation for T type
     *
     * @param serviceType - class of needed service type
     * @return network service implementation
     */
    fun <T> create(serviceType: Class<out T>): T
}