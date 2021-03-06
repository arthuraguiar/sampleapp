package com.example.sampleproject.api

import retrofit2.Response
import retrofit2.http.GET

interface EventosApi {

    @GET(".")
    suspend fun getEventos(): Response<List<EventoResponse>>

    companion object {
        const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/events/"
    }
}