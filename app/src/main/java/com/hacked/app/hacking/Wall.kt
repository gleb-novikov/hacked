package com.hacked.app.hacking

import android.graphics.*

class Wall(
    bitmapTarget: Bitmap?,
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
    canvasWidth: Int,
    speed: Int,
    bitmapW: Int,
    bitmapH: Int,
    indent: Int
) {
    private var bitmapTarget: Bitmap
    var speed: Int
    private val canvasWidth: Int
    private val indent: Int
    private var rightBlock: Rect? = null
    private var leftBlock: Rect? = null
    private var rightBlockBottom: Rect? = null
    private var leftBlockBottom: Rect? = null

    init {
        this.bitmapTarget = Bitmap.createScaledBitmap(bitmapTarget!!, bitmapW, bitmapH, false)
        this.speed = speed
        this.canvasWidth = canvasWidth
        this.indent = indent
    }

    fun draw(canvas: Canvas) {
        val p = Paint()
        rightBlock = Rect(0, y + height / 3, x, y + height)
        leftBlock = Rect(x + width, y + height / 3, canvasWidth, y + height)
        rightBlockBottom = Rect(0, y + height / 4 + height / 2, x, y + height)
        leftBlockBottom = Rect(x + width, y + height / 4 + height / 2, canvasWidth, y + height)
        p.color = Color.parseColor("#EF5411")
        canvas.drawRect(rightBlock!!, p)
        canvas.drawRect(leftBlock!!, p)
        p.color = Color.parseColor("#B53802")
        canvas.drawRect(rightBlockBottom!!, p)
        canvas.drawRect(leftBlockBottom!!, p)
        canvas.drawBitmap(bitmapTarget, (x + indent).toFloat(), y.toFloat(), p)
    }
}