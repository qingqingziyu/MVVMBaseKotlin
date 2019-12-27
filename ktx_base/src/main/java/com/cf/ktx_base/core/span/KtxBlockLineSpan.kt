package com.cf.ktx_base.core.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan

class KtxBlockLineSpan(private val mHeight: Int = 0) : ReplacementSpan() {
    override fun getSize(
        p0: Paint,
        p1: CharSequence?,
        p2: Int,
        p3: Int,
        p4: Paint.FontMetricsInt?
    ): Int {
        p4?.let {
            it.ascent = -mHeight
            it.top = it.ascent
            it.descent = -it.ascent
            it.bottom = -it.ascent
        }
        return 0;
    }

    override fun draw(
        p0: Canvas,
        p1: CharSequence?,
        p2: Int,
        p3: Int,
        p4: Float,
        p5: Int,
        p6: Int,
        p7: Int,
        p8: Paint
    ) {
    }

}