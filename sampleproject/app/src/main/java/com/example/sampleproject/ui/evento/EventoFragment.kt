package com.example.sampleproject.ui.evento

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.sampleproject.R
import com.example.sampleproject.api.EventoResponse
import com.example.sampleproject.databinding.CheckinDialogBinding
import com.example.sampleproject.databinding.FragmentEventoBinding
import com.example.sampleproject.utils.formatToDate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EventoFragment : Fragment(R.layout.fragment_evento) {

    private val viewModel: EventoViewModel by viewModels()

    private lateinit var binding: FragmentEventoBinding

    private var dialog: AlertDialog? = null

    private var dialogBinding: CheckinDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEventoBinding.bind(view)

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

        binding.apply {
            checkInButton.setOnClickListener {
                viewModel.onCheckinButtonClicked()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.eventoAction.collect { eventoAction ->
                when (eventoAction) {
                    is EventoViewModel.EventoAction.InflateCheckinDialog -> {
                        inflateFragment()
                    }
                    is EventoViewModel.EventoAction.OnCheckInSucess ->{
                        dialogBinding?.checkinLoading?.visibility = View.GONE
                        showSnackBarMsg(eventoAction.msg)
                        dialog?.dismiss()
                    }
                    is EventoViewModel.EventoAction.OnCheckInError ->{
                        dialogBinding?.checkinLoading?.visibility = View.GONE
                        showSnackBarMsg(eventoAction.msg)
                    }
                    is EventoViewModel.EventoAction.OnEmailInvalid ->{
                        dialogBinding?.checkinLoading?.visibility = View.GONE
                        showSnackBarMsg(eventoAction.msg)
                    }
                }
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

    private fun inflateFragment(){
        dialogBinding = CheckinDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(dialogBinding?.root)

        dialogBinding?.apply {
            checkinButton.setOnClickListener {
                checkinLoading.visibility = View.VISIBLE
                viewModel.checkIn(
                    nomeEditTextInput.text.toString(),
                    emailEditTextInput.text.toString()
                )
            }
            cancelCheckinButton.setOnClickListener {
                dialog?.dismiss()
                dialog = null
            }
        }
        dialog = builder.create()
        dialog?.show()
    }

    private fun showSnackBarMsg(msg:String){
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
    }
}