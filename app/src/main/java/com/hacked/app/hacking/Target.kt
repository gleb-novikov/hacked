package com.hacked.app.hacking

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class Target(bitmap: Bitmap?, var x: Int, var y: Int, var width: Int, var height: Int) {
    private var bitmap: Bitmap

    init {
        this.bitmap = Bitmap.createScaledBitmap(bitmap!!, width, height, false)
    }

    fun draw(canvas: Canvas) {
        val p = Paint()
        canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), p)
    }
}