package com.hcl.uberpoc.data.network

import retrofit2.CallAdapter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class CallAdapterFactoryImpl: CallAdapterFactory {
    override fun create(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()
}