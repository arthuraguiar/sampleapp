package com.example.sampleproject.api

import com.example.sampleproject.utils.ResultWrapper
import retrofit2.http.GET
import retrofit2.http.Path

interface EventosApi {

    @GET(".")
    suspend fun getEventos(): List<EventoResponse>

    @GET("{id}")
    suspend fun getEvento(@Path("id") eventoId: Int): EventoResponse

    companion object {
        const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/events/"
    }

}