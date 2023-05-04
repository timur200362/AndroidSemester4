package com.example.androidsemester4.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

const val API_KEY = "1c7f89ac71daa4aa8bb081c93922ec64"

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newUrl = original.url.newBuilder()
            .addQueryParameter("appid", API_KEY)
            .build()

        return chain.proceed(
            original.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}
