package com.kroegerama.kaiteki.bcode

import com.google.zxing.Result

/**
 * @author eremo
 */
class Result(
    val text: String,
    val barcodeFormat: String,
    val resultPoints: List<ResultPoint>
) {
    constructor(zxingResult: Result) : this(
        zxingResult.text,
        zxingResult.barcodeFormat.name,
        zxingResult.resultPoints.map { ResultPoint(it) }
    )

    override fun toString(): String {
        return "Result(text='$text')"
    }
}