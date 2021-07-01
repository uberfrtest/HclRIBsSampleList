package com.hcl.uberpoc.sample.di

import com.hcl.uberpoc.data.network.OkHttpClientFactory
import com.hcl.uberpoc.data.network.OkHttpClientFactoryImpl
import com.hcl.uberpoc.data.network.ConverterFactory
import com.hcl.uberpoc.data.network.ConverterFactoryImpl
import com.hcl.uberpoc.data.network.CallAdapterFactory
import com.hcl.uberpoc.data.network.CallAdapterFactoryImpl
import com.hcl.uberpoc.data.network.NetworkServiceFactory
import com.hcl.uberpoc.data.network.NetworkServiceFactoryImpl
import com.hcl.uberpoc.sample.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class NetworkModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        internal fun interceptorsFactory(): OkHttpClientFactory = OkHttpClientFactoryImpl()

        @Provides
        @JvmStatic
        internal fun converterFactory(): ConverterFactory = ConverterFactoryImpl()

        @Provides
        @JvmStatic
        internal fun callAdapterFactory(): CallAdapterFactory = CallAdapterFactoryImpl()

        @Provides
        @JvmStatic
        @Named("test")
        internal fun testNetworkServiceFactory(
            okHttpClientFactory: OkHttpClientFactory,
            converterFactory: ConverterFactory,
            callAdapterFactory: CallAdapterFactory,
        ): NetworkServiceFactory = NetworkServiceFactoryImpl(
            BuildConfig.BASE_TEST_URL,
            okHttpClientFactory,
            converterFactory,
            callAdapterFactory
        )

        @Provides
        @JvmStatic
        @Named("prod")
        internal fun mainNetworkServiceFactory(
            okHttpClientFactory: OkHttpClientFactory,
            converterFactory: ConverterFactory,
            callAdapterFactory: CallAdapterFactory,
        ): NetworkServiceFactory = NetworkServiceFactoryImpl(
            BuildConfig.BASE_URL,
            okHttpClientFactory,
            converterFactory,
            callAdapterFactory
        )
    }
}