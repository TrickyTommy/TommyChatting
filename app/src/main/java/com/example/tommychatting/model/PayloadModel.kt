package com.example.tommychatting.model


    data class PayloadModel(
        val data: DataModel,
        val to: String
    )

    data class DataModel(
        val title: String,
        val body: String,
        val email: String
    )




