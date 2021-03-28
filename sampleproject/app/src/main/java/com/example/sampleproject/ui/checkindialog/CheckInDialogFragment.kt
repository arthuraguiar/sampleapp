package com.example.sampleproject.ui.checkindialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sampleproject.databinding.CheckinDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class CheckInDialogFragment : DialogFragment(){

    private val viewModel: CheckInDialogViewModel by viewModels()

    private lateinit var binding: CheckinDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = CheckinDialogBinding
            .inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)

        binding.apply {
            checkinButton.setOnClickListener {
                viewModel.checkIn(
                    nomeEditTextInput.text.toString(),
                    emailEditTextInput.text.toString()
                )
            }
            cancelCheckinButton.setOnClickListener {
                dismissDialog()
            }
        }
        return builder.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.checkInTtrigger.collect {checkInResponse ->
                when(checkInResponse){
                    is CheckInDialogViewModel.CheckInResponse.OnCheckInSucess ->{
                        dismissDialog()
                    }
                    is CheckInDialogViewModel.CheckInResponse.OnCheckInError ->{
                        val teste = ""
                    }
                }
            }
        }

    }

    private fun dismissDialog() = this.dismiss()

}