package com.example.sampleproject.api

import java.util.*

data class EventoResponse(
    private val people: List<Pessoa>,
    private val date: Date,
    private val description: String,
    private val image: String,

    /*Longitude, Latitude e preco mantidos como String pois servem apenas para visualizacao*/
    private val longitude: String,
    private val latitude: String,
    private val price: String,

    private val title: String,
    private val id: Int
)

data class Pessoa(
    private val nome: String
)
