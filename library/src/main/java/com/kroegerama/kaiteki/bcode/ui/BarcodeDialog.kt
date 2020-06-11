package com.kroegerama.kaiteki.bcode.ui

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.core.os.postDelayed
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.zxing.BarcodeFormat
import com.kroegerama.kaiteki.bcode.*
import kotlinx.android.synthetic.main.dlg_barcode.*

open class BarcodeDialog : DialogFragment(), BarcodeResultListener {

    private val barcodeInverted by lazy {
        arguments?.getBoolean(KEY_INVERTED, false) ?: false
    }

    private val handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.dlg_barcode, container, false).also {
            dialog?.window?.run {
                requestFeature(Window.FEATURE_NO_TITLE)
                requestFeature(Window.FEATURE_SWIPE_TO_DISMISS)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bcode.setBarcodeInverted(barcodeInverted)
        bcode.setBarcodeResultListener(this)

        if (requireContext().hasCameraPermission) {
            bcode.bindToLifecycle(this)
        } else {
            requestCameraPermission(REQUEST_CAMERA)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CAMERA ->
                if (grantResults.isPermissionGranted)
                    bcode.bindToLifecycle(this)
                else
                    dismissAllowingStateLoss()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBarcodeScanCancelled() {
        //Ignore: BarcodeView will never emit this event
    }

    override fun onStop() {
        super.onStop()
        bcode.unbind()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onBarcodeResult(result: Result): Boolean {
        if ((parentFragment as? BarcodeResultListener)?.onBarcodeResult(result) == true) {
            handler.postDelayed(500) {
                dismiss()
            }
            return true
        } else if ((activity as? BarcodeResultListener)?.onBarcodeResult(result) == true) {
            handler.postDelayed(500) {
                dismiss()
            }
            return true
        }
        return false
    }

    override fun onCancel(dialog: DialogInterface) {
        (parentFragment as? BarcodeResultListener)?.onBarcodeScanCancelled()
        (activity as? BarcodeResultListener)?.onBarcodeScanCancelled()
        super.onCancel(dialog)
    }

    companion object {

        private const val KEY_FORMATS = "formats"
        private const val KEY_INVERTED = "inverted"

        private const val REQUEST_CAMERA = 0xbd_ca

        fun show(
            fm: FragmentManager,
            barcodeInverted: Boolean = false,
            tag: String? = null
        ) = BarcodeDialog().apply {
            arguments = bundleOf(
                KEY_FORMATS to BarcodeFormat.values().toList(),
                KEY_INVERTED to barcodeInverted
            )

            show(fm, tag)
        }
    }
}