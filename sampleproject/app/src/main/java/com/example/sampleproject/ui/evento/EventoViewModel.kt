package com.example.sampleproject.ui.evento

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.sampleproject.api.EventoCheckinPost
import com.example.sampleproject.api.EventoResponse
import com.example.sampleproject.data.EventosRepository
import com.example.sampleproject.utils.Constantes.CHECK_IN_SUCESS
import com.example.sampleproject.utils.Constantes.EMAIL_INVALIDO
import com.example.sampleproject.utils.Constantes.EVENTO
import com.example.sampleproject.utils.Constantes.NETWORK_ERROR
import com.example.sampleproject.utils.ResultWrapper
import com.example.sampleproject.utils.validateEmailFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EventoViewModel @ViewModelInject constructor(
    private val eventosRepository: EventosRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val eventoArgs = state.get<EventoResponse>(EVENTO)

    private val _evento = MutableLiveData<EventoResponse>()

    val evento: LiveData<EventoResponse>
        get() = _evento

    private val eventoChannel = Channel<EventoAction>()

    val eventoAction = eventoChannel.receiveAsFlow()

    fun getEvento() {
        viewModelScope.launch(Dispatchers.Main) {
            when (val response =
                eventosRepository.getEvento(eventoArgs?.id ?: 0, Dispatchers.Main)) {
                is ResultWrapper.NetworkError -> {
                    eventoChannel.send(EventoAction.OnFetchEventoError(NETWORK_ERROR))
                }
                is ResultWrapper.GenericError -> {
                    eventoChannel.send(EventoAction.OnFetchEventoError(response.error))
                }
                is ResultWrapper.Success -> {
                    response.value.run { _evento.value = this }
                }
            }
        }
    }

    fun onCheckinButtonClicked() = viewModelScope.launch {
        evento.value?.let {eventoResponse ->
            eventoChannel.send(EventoAction.InflateCheckinDialog(eventoResponse))
        }
    }

    fun checkIn(nome: String, email: String) {
        viewModelScope.launch {
            if(!email.validateEmailFormat()){
                eventoChannel.send(EventoAction.OnEmailInvalid(EMAIL_INVALIDO))
                return@launch
            }

            val response = eventosRepository.checkInEvento(
                EventoCheckinPost(
                    eventId = eventoArgs?.id ?: 0,
                    name = nome,
                    email = email
                )
            )

            when(response){
                is ResultWrapper.NetworkError ->{
                    eventoChannel.send(EventoAction.OnCheckInError(NETWORK_ERROR))
                }
                is ResultWrapper.GenericError -> {
                    eventoChannel.send(EventoAction.OnCheckInError(response.error))
                }
                is ResultWrapper.Success ->{
                    eventoChannel.send(EventoAction.OnCheckInSucess(CHECK_IN_SUCESS))
                }
            }
        }
    }

    sealed class EventoAction{
        data class OnFetchEventoError(val msg: String): EventoAction()
        data class InflateCheckinDialog(val evento:EventoResponse): EventoAction()
        data class OnCheckInSucess(val msg: String): EventoAction()
        data class OnCheckInError(val msg: String): EventoAction()
        data class OnEmailInvalid(val msg: String): EventoAction()
    }


}