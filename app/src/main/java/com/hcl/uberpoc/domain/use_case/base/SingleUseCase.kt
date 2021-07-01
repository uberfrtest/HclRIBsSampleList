package com.hcl.uberpoc.domain.use_case.base

import io.reactivex.Single

/**
 * Used for Single use case creation
 */
interface SingleUseCase<in DATA, T> {
    /**
     * Execute Single use case
     *
     * @param data - input data, nullable
     * @return Single with specified data type
     */
    fun execute(data: DATA? = null): Single<T>
}