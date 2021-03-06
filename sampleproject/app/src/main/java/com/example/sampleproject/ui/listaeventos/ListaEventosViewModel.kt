package com.example.sampleproject.ui.listaeventos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleproject.api.EventoResponse
import com.example.sampleproject.data.EventosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaEventosViewModel @ViewModelInject constructor(
    private val eventosRepository: EventosRepository
) : ViewModel() {

    private val _eventos = MutableLiveData<List<EventoResponse>>()

    val eventos: LiveData<List<EventoResponse>>
        get() = _eventos

    fun fetchEventos() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = eventosRepository.getEventos()

            if (res.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _eventos.value = res.body()
                }
            }
        }
    }
}