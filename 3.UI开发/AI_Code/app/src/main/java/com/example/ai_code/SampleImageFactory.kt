package com.example.ai_code

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import kotlin.random.Random

object SampleImageFactory {
    data class SampleImage(
        val label: String,
        val bitmap: Bitmap
    )

    fun buildSamples(context: Context): List<SampleImage> {
        val density = context.resources.displayMetrics.density
        val width = (1080 * density / 3f).toInt()
        val height = (720 * density / 3f).toInt()
        val basePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = 24f * density
            style = Paint.Style.FILL
        }

        return listOf(
            createPrintedSample("Invoice #2048\nTotal: $58.40", width, height, basePaint),
            createPrintedSample("Meeting 2026-05-01\nRoom A1", width, height, basePaint),
            createPrintedSample("Shipping Label\nNO: 8F3-552", width, height, basePaint),
            createHandwrittenSample("Todo:\nBuy milk\nCall Alex", width, height, basePaint),
            createHandwrittenSample("Recipe:\n2 eggs\n1 cup flour", width, height, basePaint)
        )
    }

    private fun createPrintedSample(
        text: String,
        width: Int,
        height: Int,
        paint: Paint
    ): SampleImage {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        val bounds = Rect()
        paint.getTextBounds("X", 0, 1, bounds)
        val lineHeight = bounds.height() + 16
        var y = 80f
        for (line in text.split("\n")) {
            canvas.drawText(line, 60f, y, paint)
            y += lineHeight
        }
        return SampleImage("Printed", bitmap)
    }

    private fun createHandwrittenSample(
        text: String,
        width: Int,
        height: Int,
        basePaint: Paint
    ): SampleImage {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        val paint = Paint(basePaint).apply {
            strokeWidth = 3f
            style = Paint.Style.STROKE
        }

        var x = 60f
        var y = 90f
        val random = Random(text.hashCode())
        val maxWidth = width - 60f

        for (char in text) {
            if (char == '\n') {
                x = 60f
                y += 60f
                continue
            }

            val jitterX = random.nextInt(-2, 3).toFloat()
            val jitterY = random.nextInt(-6, 7).toFloat()
            val rotation = random.nextInt(-8, 9).toFloat()
            val charWidth = paint.measureText(char.toString()) + 18f

            if (x + charWidth > maxWidth) {
                x = 60f
                y += 60f
            }

            val path = Path()
            val baseline = y + jitterY
            path.moveTo(x, baseline)
            path.lineTo(x + charWidth / 2f, baseline - 10f)
            path.lineTo(x + charWidth, baseline + 4f)

            canvas.save()
            canvas.rotate(rotation, x, baseline)
            canvas.drawPath(path, paint)
            canvas.drawText(char.toString(), x + jitterX, baseline, basePaint)
            canvas.restore()

            x += charWidth
        }

        return SampleImage("Handwritten", bitmap)
    }
}
