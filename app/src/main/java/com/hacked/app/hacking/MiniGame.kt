package com.hacked.app.hacking

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hacked.app.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt

@SuppressLint("ViewConstructor")
class MiniGame(
    context: Context, playerLvl: Int, private val gameCallback: GameCallback,
    private val viewLifecycleOwner: LifecycleOwner
) : View(context) {
    private var isGameWin = false
    private var isGameLose: Boolean
    private var paint: Paint = Paint()
    private var bitmapPlayer = BitmapFactory.decodeResource(resources, R.drawable.image_game_player)
    private var bitmapWall = BitmapFactory.decodeResource(resources, R.drawable.image_game_shield)
    private var bitmapTarget = BitmapFactory.decodeResource(resources, R.drawable.image_game_folder)
    private var bitmapBullet = BitmapFactory.decodeResource(resources, R.drawable.image_game_exploit)
    private var bitmapBackgroundLines = BitmapFactory.decodeResource(resources, R.drawable.image_game_line)
    private var bitmapBackgroundCircle = BitmapFactory.decodeResource(resources, R.drawable.image_game_score)
    private var bitmapBackground = BitmapFactory.decodeResource(resources, R.drawable.image_game_background)
    private var player: Player
    private var target: Target
    private var canvasH: Int
    private var canvasW: Int
    private var scale: Double
    private var targetCoefficient = 2500.0
    private var coefficient: Double
    private var countBullet = 100
    private var countWall = 3
    private var speedWall = 0
    private var bulletList = ArrayList<Bullet>()
    private var wallList = ArrayList<Wall>()
    private var isFirstDraw = true
    private var job: Job? = null

    init {
        startUpdates()
        val displayMetrics = context.resources.displayMetrics
        canvasW = displayMetrics.widthPixels
        canvasH = displayMetrics.heightPixels
        scale = sqrt((canvasH * canvasH + canvasW * canvasW).toDouble())
        coefficient = scale / targetCoefficient
        player = Player(
            bitmapPlayer,
            0,
            0,
            (130 * coefficient).toInt(),
            (130 * coefficient).toInt(),
            (25 * coefficient).toInt()
        )
        target = Target(
            bitmapTarget,
            0,
            (100 * coefficient).toInt(),
            (150 * coefficient).toInt(),
            (150 * coefficient).toInt()
        )
        isGameLose = false
        if (playerLvl == 1) {
            countWall = 1
            countBullet = 5
            speedWall = 0
        } else if (playerLvl <= 3) {
            countWall = 2
            countBullet = 6
            speedWall = (5 * coefficient).toInt()
        } else if (playerLvl <= 7) {
            countWall = 4
            countBullet = 7
            speedWall = (8 * coefficient).toInt()
        } else if (playerLvl <= 10) {
            countWall = 6
            countBullet = 8
            speedWall = (12 * coefficient).toInt()
        } else if (playerLvl <= 15) {
            countWall = 7
            countBullet = 11
            speedWall = (15 * coefficient).toInt()
        } else {
            countWall = 9
            countBullet = 12
            speedWall = (playerLvl * coefficient).toInt()
        }
        for (i in 0 until countWall) {
            wallList.add(
                Wall(
                    bitmapWall,
                    (Math.random() * (canvasW - (100 * coefficient).toInt())).toInt(),
                    canvasH / 5 + (150 * i * coefficient).toInt(),
                    (120 * coefficient).toInt(),
                    (70 * coefficient).toInt(),
                    canvasW,
                    speedWall,
                    (100 * coefficient).toInt(),
                    (100 * coefficient).toInt(),
                    (11 * coefficient).toInt()
                )
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvasH = height
        canvasW = width
        if (isFirstDraw) {
            player.y = canvasH - player.height - (200 * coefficient).toInt()
            player.x = canvasW / 2 - target.width / 2
            target.x = canvasW / 2 - target.width / 2
            bitmapBackground = Bitmap.createScaledBitmap(bitmapBackground, canvasW, canvasH, false)
            bitmapBackgroundLines = Bitmap.createScaledBitmap(
                bitmapBackgroundLines,
                canvasW,
                (15 * coefficient).toInt(),
                false
            )
            bitmapBackgroundCircle = Bitmap.createScaledBitmap(
                bitmapBackgroundCircle,
                (150 * coefficient).toInt(),
                (150 * coefficient).toInt(),
                false
            )
            isFirstDraw = false
        }
        // Фон
        canvas.drawBitmap(bitmapBackground, 0f, 0f, paint)
        // 2 линии (для красоты) рядом с игроком
        canvas.drawBitmap(bitmapBackgroundLines, 0f, (canvasH - (200 * coefficient).toInt()).toFloat(), paint)
        canvas.drawBitmap(
            bitmapBackgroundLines,
            0f,
            (canvasH - (215 * coefficient + player.height).toInt()).toFloat(),
            paint
        )
        // Кружок для счётчика выстрелов
        canvas.drawBitmap(
            bitmapBackgroundCircle,
            0.44f * canvasW,
            canvasH - 1.1f * bitmapBackgroundCircle.height,
            paint
        )
        player.draw(canvas)
        target.draw(canvas)
        if (wallList.isNotEmpty()) {
            for (w in wallList) {
                w.draw(canvas)
            }
        }
        if (bulletList.isNotEmpty()) {
            for (b in bulletList) {
                b.draw(canvas)
            }
        }
        paint.textSize = (100 * coefficient).toInt().toFloat()
        paint.color = Color.parseColor("#633FDA")
        if (countBullet < 10) {
            canvas.drawText(
                countBullet.toString() + "",
                0.48f * canvasW,
                canvasH - .35f * bitmapBackgroundCircle.height,
                paint
            )
        } else {
            canvas.drawText(
                countBullet.toString() + "",
                0.865f * canvasW,
                canvasH - .35f * bitmapBackgroundCircle.height,
                paint
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventAction = event.action
        if (eventAction == MotionEvent.ACTION_DOWN) {
            if (countBullet > 0) {
                bulletList.add(
                    Bullet(
                        bitmapBullet,
                        player.x + player.width / 2,
                        player.y, (-50 * coefficient).toInt(), (50 * coefficient).toInt(), (70 * coefficient).toInt()
                    )
                )
                countBullet--
            }
        }
        return true
    }

    private fun update() {
        if (player.x + player.width > canvasW || player.x < 0) {
            player.speed = -player.speed
        }
        player.x = player.x + player.speed
        for (w in wallList) {
            if (w.x + w.width > canvasW || w.x < 0) {
                w.speed = -w.speed
            }
            w.x = w.x + w.speed
        }
        val bulletRemoveList = ArrayList<Bullet>()
        val wallRemoveList = ArrayList<Wall>()
        if (bulletList.isNotEmpty()) {
            for (b in bulletList) {
                b.y = b.y + b.speed
                if (b.y < 0) {
                    bulletRemoveList.add(b)
                }
                if (b.y <= target.y + target.height
                    && (target.x < b.getX()
                            && b.getX() < target.x + target.width)
                ) {
                    // Если коллбек не вызывался
                    if (!isGameWin) {
                        // Вызвать коллбек
                        gameCallback.onWin()
                        isGameWin = true
                        stopUpdates()
                    }
                    break
                }
                if (wallList.isNotEmpty()) {
                    for (w in wallList) {
                        if (!(w.x < b.getX()
                                    && b.getX() < w.width + w.x)
                            && b.y <= w.y + w.height
                        ) {
                            if (!bulletRemoveList.contains(b)) bulletRemoveList.add(b)
                        } else {
                            if ((w.x < b.getX()
                                        && b.getX() < w.width + w.x)
                                && w.y + w.height > b.y
                            ) {
                                if (!bulletRemoveList.contains(b)) bulletRemoveList.add(b)
                                if (!wallRemoveList.contains(w)) wallRemoveList.add(w)
                            }
                        }
                    }
                }
            }
        } else {
            if (countBullet <= 0) {
                loseGame()
            }
        }
        for (w in wallRemoveList) {
            wallList.remove(w)
        }
        wallRemoveList.clear()
        for (bu in bulletRemoveList) {
            bulletList.remove(bu)
        }
        bulletRemoveList.clear()
        invalidate()
    }

    private fun loseGame() {
        gameCallback.onLose()
        stopUpdates()
        isGameLose = true
    }

    private fun startUpdates() {
        val lifecycle = viewLifecycleOwner // in Fragment
        stopUpdates()
        job = lifecycle.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    update()
                    delay(17)
                }
            }
        }
    }

    private fun stopUpdates() {
        job?.cancel()
        job = null
    }
}