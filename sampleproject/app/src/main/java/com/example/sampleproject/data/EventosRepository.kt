package com.example.sampleproject.data


import com.example.sampleproject.api.EventosApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventosRepository @Inject constructor(private val eventosApi: EventosApi) {
    suspend fun getEventos() = eventosApi.getEventos()
}