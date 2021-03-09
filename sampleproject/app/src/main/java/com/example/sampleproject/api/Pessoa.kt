package com.example.sampleproject.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pessoa(
    private val nome: String
): Parcelable

