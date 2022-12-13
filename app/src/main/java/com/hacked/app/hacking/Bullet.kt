package com.hacked.app.hacking

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class Bullet(
    bitmap: Bitmap?,
    private var x: Int,
    var y: Int,
    var speed: Int,
    var width: Int,
    var height: Int
) {
    private var bitmap: Bitmap

    init {
        this.bitmap = Bitmap.createScaledBitmap(bitmap!!, width, height, false)
    }

    fun draw(canvas: Canvas) {
        val p = Paint()
        canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), p)
    }

    fun getX(): Int {
        return x + width / 2
    }

    fun setX(x: Int) {
        this.x = x
    }
}