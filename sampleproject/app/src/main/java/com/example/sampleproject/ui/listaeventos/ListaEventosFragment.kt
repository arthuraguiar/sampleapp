package com.example.sampleproject.ui.listaeventos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleproject.R
import com.example.sampleproject.databinding.FragmentListaEventosBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_lista_eventos.*

@AndroidEntryPoint
class ListaEventosFragment : Fragment(R.layout.fragment_lista_eventos) {

    private val viewModel: ListaEventosViewModel by viewModels()

    private var _binding: FragmentListaEventosBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListaEventosBinding.bind(view)
        val evensosAdapter = EventosAdapter()

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
    }


}