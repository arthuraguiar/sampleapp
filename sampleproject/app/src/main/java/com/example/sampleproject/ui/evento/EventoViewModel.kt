package com.example.sampleproject.ui.evento

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.sampleproject.api.EventoResponse
import com.example.sampleproject.data.EventosRepository
import com.example.sampleproject.utils.Constantes.EVENTO
import com.example.sampleproject.utils.ResultWrapper
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
                }
                is ResultWrapper.GenericError -> {
                }
                is ResultWrapper.Success -> {
                    response.value.run { _evento.value = this }
                }
            }
        }
    }

    fun onCheckinButtonClicked() = viewModelScope.launch {
        evento.value?.let {eventoResponse ->
            eventoChannel.send(EventoAction.NavigateToCheckinDialog(eventoResponse))
        }
    }

    sealed class EventoAction(){
        data class NavigateToCheckinDialog(val evento:EventoResponse): EventoAction()
    }
}