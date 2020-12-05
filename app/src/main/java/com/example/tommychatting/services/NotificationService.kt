package com.example.tommychatting.services


import com.example.tommychatting.ConstantUtil
import com.example.tommychatting.model.PayloadModel
import com.example.tommychatting.model.ResponseModel
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationService {
    @POST("send")
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=${ConstantUtil.API_KEY}"
    )
    suspend fun sendNotification(@Body body: PayloadModel): ResponseModel
}