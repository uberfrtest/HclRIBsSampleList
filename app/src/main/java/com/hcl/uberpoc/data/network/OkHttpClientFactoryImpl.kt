package com.hcl.uberpoc.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY

class OkHttpClientFactoryImpl : OkHttpClientFactory {

    companion object {
        private const val TIMEOUT_DURATION = 20L
        private val TIMEOUT_UNIT = TimeUnit.SECONDS
    }

    override fun create(): OkHttpClient = configureOkHttpClient(OkHttpClient.Builder()).build()

    private fun configureOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder.apply {
            setupInterceptors(this)
            setupTimeout(this)
        }
    }

    private fun setupTimeout(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder
            .connectTimeout(TIMEOUT_DURATION, TIMEOUT_UNIT)
            .writeTimeout(TIMEOUT_DURATION, TIMEOUT_UNIT)
            .readTimeout(TIMEOUT_DURATION, TIMEOUT_UNIT)
    }

    private fun setupInterceptors(builder: OkHttpClient.Builder): OkHttpClient.Builder =
        builder.apply { provideInterceptors().forEach { addInterceptor(it) } }

    private fun provideInterceptors(): List<Interceptor> =
        listOf(provideLoggerInterceptor(), provideRequestHeadersInterceptor())

    private fun provideLoggerInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = BODY
        return interceptor
    }

    private fun provideRequestHeadersInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            chain.proceed(requestBuilder.build())
        }
    }
}