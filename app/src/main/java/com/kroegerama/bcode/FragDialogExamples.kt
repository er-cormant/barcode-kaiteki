package com.kroegerama.bcode

import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.kroegerama.kaiteki.baseui.BaseFragment
import com.kroegerama.kaiteki.bcode.BarcodeResultListener
import com.kroegerama.kaiteki.bcode.Result
import com.kroegerama.kaiteki.bcode.ui.BarcodeBottomSheet
import com.kroegerama.kaiteki.bcode.ui.BarcodeDialog
import com.kroegerama.kaiteki.bcode.ui.showBarcodeAlertDialog
import com.kroegerama.kaiteki.snackBar
import kotlinx.android.synthetic.main.frag_dialog_examples.*

class FragDialogExamples : BaseFragment(
    layout = R.layout.frag_dialog_examples
), BarcodeResultListener {

    override fun setupGUI() {
        btnDialogFragment.setOnClickListener {
            BarcodeDialog.show(
                childFragmentManager,
                formats = listOf(BarcodeFormat.QR_CODE),
                barcodeInverted = cbInverted.isChecked
            )
        }
        btnBottomSheet.setOnClickListener {
            BarcodeBottomSheet.show(
                childFragmentManager,
                formats = listOf(BarcodeFormat.QR_CODE),
                barcodeInverted = cbInverted.isChecked
            )
        }
        btnAlertDialog.setOnClickListener {
            requireContext().showBarcodeAlertDialog(
                owner = this,
                listener = this,
                formats = listOf(BarcodeFormat.QR_CODE),
                barcodeInverted = cbInverted.isChecked
            )
        }
    }

    override fun onBarcodeScanCancelled() {
        snackBar("Scanning cancelled", Snackbar.LENGTH_LONG) {
            setAction(android.R.string.ok) {
                dismiss()
            }
        }
    }

    override fun onBarcodeResult(result: Result): Boolean {
        Log.d(TAG, "Result: $result")

        //return false to not automatically close the dialog
        return false
    }

    companion object {
        private const val TAG = "FragDialogExamples"
    }
}