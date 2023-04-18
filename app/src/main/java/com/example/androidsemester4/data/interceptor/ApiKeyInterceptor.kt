package com.example.androidsemester4.data.interceptor

import com.example.androidsemester4.data.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original=chain.request()
        val newUrl=original.url.newBuilder()
            .addQueryParameter("appid", API_KEY)
            .build()

        return chain.proceed(
            original.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}
