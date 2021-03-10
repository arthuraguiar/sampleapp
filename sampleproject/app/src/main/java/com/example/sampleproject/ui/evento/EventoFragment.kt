package com.example.sampleproject.ui.evento

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sampleproject.R
import com.example.sampleproject.databinding.FragmentEventoBinding
import com.example.sampleproject.utils.formatToDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventoFragment : Fragment(R.layout.fragment_evento) {

    private val viewModel: EventoViewModel by viewModels()

    private var _binding: FragmentEventoBinding? = null

    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentEventoBinding.bind(view)

        viewModel.getEvento()

        binding.apply {
            this.eventoResumoTextview.movementMethod = ScrollingMovementMethod()
        }

        viewModel.evento.observe(viewLifecycleOwner) { evento ->
            binding.apply {
                this.dataEventoTextview.text = evento.date.formatToDate()
                this.eventoResumoTextview.text = evento.description
                this.eventoTitleTextview.text = evento.title
                Glide.with(this.root)
                    .load(evento.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_error_24)
                    .into(this.eventoImageview)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.apply {
                this.eventoProgressbarHolder.visibility = if (it) View.VISIBLE else View.INVISIBLE
            }
        }

    }

}