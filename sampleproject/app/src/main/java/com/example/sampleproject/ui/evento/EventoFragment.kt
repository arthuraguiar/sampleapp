package com.example.sampleproject.ui.evento

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sampleproject.R
import com.example.sampleproject.api.EventoResponse
import com.example.sampleproject.databinding.FragmentEventoBinding
import com.example.sampleproject.utils.formatToDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventoFragment : Fragment(R.layout.fragment_evento) {

    private val viewModel: EventoViewModel by viewModels()

    private var _binding: FragmentEventoBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentEventoBinding.bind(view)

        viewModel.getEvento()

        viewModel.eventoArgs?.let { evento ->
            binding.apply {
                populateBinding(this, evento)
            }
        }

        viewModel.evento.observe(viewLifecycleOwner) { evento ->
            binding.apply {
                populateBinding(this, evento)
            }
        }

    }

    private fun populateBinding(
        fragmentEventoBinding: FragmentEventoBinding,
        evento: EventoResponse
    ) {
        fragmentEventoBinding.dataEventoTextview.text = evento.date.formatToDate()
        fragmentEventoBinding.eventoResumoTextview.text = evento.description
        fragmentEventoBinding.eventoTitleTextview.text = evento.title

        fragmentEventoBinding.imageview.apply {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                this.transitionName = evento.title
            }
            Glide.with(this)
                .load(evento.image)
                .error(R.drawable.ic_baseline_error_24)
                .into(this)
        }
    }


}