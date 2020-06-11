package com.kroegerama.kaiteki.bcode

interface BarcodeResultListener {

    /**
     * @return return true to dismiss the dialog/fragment
     */
    fun onBarcodeResult(result: Result): Boolean

    fun onBarcodeScanCancelled()

}