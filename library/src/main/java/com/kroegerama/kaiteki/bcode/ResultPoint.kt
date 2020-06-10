package com.kroegerama.kaiteki.bcode

import com.google.zxing.ResultPoint

/**
 * @author eremo
 */
class ResultPoint(
    val x: Float,
    val y: Float
) {
    constructor(zxingResultPoint: ResultPoint): this(
        zxingResultPoint.x,
        zxingResultPoint.y
    )
}