package com.example.sampleproject.data


import com.example.sampleproject.api.EventosApi
import com.example.sampleproject.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventosRepository @Inject constructor(private val eventosApi: EventosApi) {
    suspend fun getEventos(dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        safeApiCall(dispatcher) { eventosApi.getEventos() }
}