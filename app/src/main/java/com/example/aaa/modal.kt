package com.example.aaa

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment
import android.content.Intent

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [modal.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.modal_success) // Custom layout

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Transparent background


        // Find the button inside the dialog
        val modalContinueButton = dialog.findViewById<Button>(R.id.ModalContinue)
        modalContinueButton?.setOnClickListener {
            // Open CategorizationActivity
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)

            // Close the dialog
            dismiss()
        }

        return dialog
    }
}

