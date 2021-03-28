package com.example.sampleproject.ui.checkindialog

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.sampleproject.api.EventoCheckinPost
import com.example.sampleproject.api.EventoResponse
import com.example.sampleproject.data.EventosRepository
import com.example.sampleproject.di.ApplicationScope
import com.example.sampleproject.utils.Constantes.EVENTO
import com.example.sampleproject.utils.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckInDialogViewModel @ViewModelInject constructor(
    private val eventosRepository: EventosRepository,
    @ApplicationScope private val applicationScope: CoroutineScope,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val eventoArgs = state.get<EventoResponse>(EVENTO)

    fun checkIn(nome: String, email: String) {
        applicationScope.launch(Dispatchers.Main) {
            val response = eventosRepository.checkInEvento(
                EventoCheckinPost(
                    eventId = eventoArgs?.id ?: 0,
                    name = nome,
                    email = email
                )
            )

            when(response){
                is ResultWrapper.NetworkError ->{}
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.Success ->{
                }
            }
        }
    }

}