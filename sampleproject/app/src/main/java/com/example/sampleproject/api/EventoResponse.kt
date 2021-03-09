package com.example.sampleproject.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class EventoResponse(
     val people: List<Pessoa>,
     val date: Long,
     val description: String,
     val image: String,

     /*Longitude e Latitude mantidos como String pois servem apenas para visualizacao*/
     val longitude: String,
     val latitude: String,

     val price: BigDecimal,
     val title: String,
     val id: Int
):Parcelable
