/*
 * Copyright (c) 2018 Muhammad Umar (https://github.com/zelin)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.neberox.library.asciicreator.utilities

import android.content.Context
import android.graphics.Bitmap
import android.util.TypedValue
import com.neberox.library.asciicreator.models.PixelBlock

/**
 * Utility functions
 * @author Muhammad Umar (ee_umar@yahoo.com)
 */
object ASCIIUtilities {

    fun resizeBitmap(bitmap: Bitmap, scaleFactor: Int): Bitmap {
        var outWidth: Int
        var outHeight: Int
        var scaleFactor = scaleFactor

        if (scaleFactor <= 1) return bitmap

        if (scaleFactor > kotlin.math.min(bitmap.height, bitmap.width))
            scaleFactor = kotlin.math.min(bitmap.height, bitmap.width)

        val ratio = scaleFactor.toFloat() / bitmap.width.toFloat()

        outWidth = scaleFactor
        outHeight = (ratio * bitmap.height).toInt()

        return Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, true)
    }

    fun getColor(block: PixelBlock): Int {
        return ((block.a * 255.0f + 0.5f).toInt() shl 24) or
                ((block.r * 255.0f + 0.5f).toInt() shl 16) or
                ((block.g * 255.0f + 0.5f).toInt() shl 8) or
                (block.b * 255.0f + 0.5f).toInt()
    }

    fun spToPx(sp: Float, context: Context): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
    }
}