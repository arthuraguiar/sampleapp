package com.example.sampleproject.ui.listaeventos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleproject.api.EventoResponse
import com.example.sampleproject.data.EventosRepository
import com.example.sampleproject.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListaEventosViewModel @ViewModelInject constructor(
    private val eventosRepository: EventosRepository
) : ViewModel() {

    private val _eventos = MutableLiveData<List<EventoResponse>>()

    val eventos: LiveData<List<EventoResponse>>
        get() = _eventos

    val loading = MutableLiveData(false)

    fun fetchEventos() {
        viewModelScope.launch(Dispatchers.Main) {
            loading.value = true
            when (val response = eventosRepository.getEventos()) {
                is ResultWrapper.NetworkError ->{}
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.Success -> {
                    response.value.run { _eventos.value  = this}
                }
            }
            loading.value = false
        }
    }
}