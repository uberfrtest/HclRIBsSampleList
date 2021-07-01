package com.hcl.uberpoc.data.network

import retrofit2.CallAdapter

/**
 * Used for call adapter factory creation
 */
interface CallAdapterFactory {
    /**
     * Create call adapter factory
     *
     * @return CallAdapter.Factory
     */
    fun create(): CallAdapter.Factory
}