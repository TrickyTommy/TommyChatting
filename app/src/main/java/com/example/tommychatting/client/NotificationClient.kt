package com.example.tommychatting.client

import android.util.Log
import com.example.tommychatting.BuildConfig
import com.example.tommychatting.services.NotificationService
import com.google.gson.GsonBuilder


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationClient {
    companion object {
        val service: NotificationService by lazy {
            val httpLoggingInterceptor = HttpLoggingInterceptor { Log.e("LOG_API", it) }
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient()
                .newBuilder()
                .apply {
                    if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
                }
                .build()

            val retrofit = Retrofit
                .Builder()
                .baseUrl("https://fcm.googleapis.com/fcm/")
                .client(okHttpClient)
                .addConverterFactory(
                    GsonConverterFactory.create(GsonBuilder().setLenient().create())
                )
                .build()

            retrofit.create(NotificationService::class.java)
        }
    }
}