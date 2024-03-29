package org.mothdigital.stationdistancechecker.api

import okhttp3.Interceptor
import okhttp3.Response
import org.mothdigital.stationdistancechecker.BuildConfig

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        return proceed(
            request()
                .newBuilder()
                .addHeader(HEADER_NAME, BuildConfig.X_KOLEO_VERSION).build()
        )
    }

    companion object {
        private const val HEADER_NAME = "X-KOLEO-Version"
    }
}