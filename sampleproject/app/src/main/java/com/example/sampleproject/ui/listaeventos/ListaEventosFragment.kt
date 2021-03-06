package com.example.sampleproject.ui.listaeventos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.sampleproject.R
import com.example.sampleproject.databinding.FragmentListaEventosBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaEventosFragment : Fragment(R.layout.fragment_lista_eventos) {


    private var _binding: FragmentListaEventosBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListaEventosBinding.bind(view)

        binding.apply {
            
        }
    }


}