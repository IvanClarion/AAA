package com.example.aaa

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment

class SignUpDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.modal_success) // Custom layout
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Transparent background

        // Find the continue button
        val modalContinueButton = dialog.findViewById<Button>(R.id.ModalContinue)
        modalContinueButton?.setOnClickListener {
            // Navigate to MainActivity
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

            // Close the current activity
            activity?.finish()

            // Dismiss the dialog
            dismiss()
        }

        return dialog
    }
}
