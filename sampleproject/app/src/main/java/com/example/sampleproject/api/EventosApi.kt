package com.example.sampleproject.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventosApi {

    @GET("events/")
    suspend fun getEventos(): List<EventoResponse>

    @GET("events/{id}")
    suspend fun getEvento(@Path("id") eventoId: Int): EventoResponse

    @POST("checkin")
    suspend fun checkInEvento(@Body eventoCheckinPost: EventoCheckinPost):Any

    companion object {
        const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"
    }

}