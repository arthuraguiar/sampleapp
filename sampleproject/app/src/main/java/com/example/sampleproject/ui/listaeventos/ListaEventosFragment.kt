package com.example.sampleproject.ui.listaeventos

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleproject.R
import com.example.sampleproject.api.EventoResponse
import com.example.sampleproject.databinding.FragmentListaEventosBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_lista_eventos.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListaEventosFragment : Fragment(R.layout.fragment_lista_eventos),
    EventosAdapter.EventoOnClickListener {

    private val viewModel: ListaEventosViewModel by viewModels()

    private var _binding: FragmentListaEventosBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListaEventosBinding.bind(view)
        val evensosAdapter = EventosAdapter(this)

        viewModel.fetchEventos()

        binding.apply {
            lista_eventos_recyclerview.apply {
                adapter = evensosAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.eventos.observe(viewLifecycleOwner) {
            evensosAdapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            loading_event_layout.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.eventoTrigger.collect { eventTrigger ->
                when (eventTrigger) {
                    is ListaEventosViewModel.EventoTrigger.NavigateToEventoScreen -> {
                        val extras = FragmentNavigatorExtras(
                            eventTrigger.imageView to "imageView"
                        )
                        val action = ListaEventosFragmentDirections
                            .actionListaEventosFragmentToEventoFragment(eventTrigger.evento)
                        findNavController().navigate(action, extras)
                    }
                }
            }

        }
    }

    override fun onItemClick(evento: EventoResponse, imageView: ImageView) {
        viewModel.onEventoClicked(evento, imageView)
    }


}