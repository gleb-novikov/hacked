package com.hacked.app.hacking

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class Player(
    bitmap: Bitmap?,
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
    speed: Int
) {
    private var bitmap: Bitmap
    var speed: Int

    init {
        this.bitmap = Bitmap.createScaledBitmap(bitmap!!, width, height, false)
        this.speed = speed
    }

    fun draw(canvas: Canvas) {
        val p = Paint()
        canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), p)
    }
}