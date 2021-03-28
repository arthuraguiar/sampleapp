package com.example.sampleproject.ui.checkindialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.sampleproject.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.checkin_dialog.view.*


@AndroidEntryPoint
class CheckInDialogFragment : DialogFragment(){

    private val viewModel: CheckInDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val viewInflated: View = LayoutInflater.from(context)
            .inflate(R.layout.checkin_dialog, view as ViewGroup?, false)

        /*Adicionadr validacao de email e resposta de request*/

        val editNome = viewInflated.nomeEditTextInput
        val editEmail = viewInflated.emailEditTextInput
        return AlertDialog
            .Builder(requireContext())
            .setTitle("Check In")
            .setView(viewInflated)
            .setMessage("Deseja Realizar checkin no evento?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Confirm"){ _, _ ->
                viewModel.checkIn(editNome.text.toString(), editEmail.text.toString())
            }
            .create()
    }

}