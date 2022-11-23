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
package com.neberox.library.asciicreator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.neberox.library.asciicreator.models.PixelGrid
import com.neberox.library.asciicreator.utilities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.InvalidParameterException
import java.util.concurrent.ExecutionException

/**
 * Convert Bitmap into its ASCII equivalent bitmap or String.
 * <p>
 * API endpoints are defined as methods on an interface with annotations providing metadata about
 * the form in which the HTTP call should be made.
 * <p>
 *
 * @author Muhammad Umar (ee_umar@yahoo.com)
 */

class ASCIIConverter(private val mContext: Context) {

    /* Grid columns count calculated based on the font size */
    private var mColumns: Float = 0.0f

    /* Font size of the ASCII text in pixels. */
    private var mFontSize: Float = 72.0f

    /* Background color of the bitmap. Default is transparent */
    private var mBackgroundColor: Int = Color.TRANSPARENT

    /* Luminance of the text */
    private var mReversedLuminance: Boolean = false

    /* Enable to disable gray scale of the bitmap */
    private var mGrayScale: Boolean = false

    /* Helper class to get ASCII-Luminance dictionary map */
    private var mHelper: ASCIIConverterHelper

    private var mStringTaskListener: OnStringTaskListener? = null
    private var mBitmapTaskListener: OnBitmapTaskListener? = null

    /**
     * @param mContext Context of the app
     */
    init {
        mHelper = ASCIIConverterHelper()
        initDefaults()
    }

    /**
     *
     * @param mContext Context of the app
     * @param mHelper  Customized ASCIIConverterHelper Class
     */
    constructor(mContext: Context, mHelper: ASCIIConverterHelper) : this(mContext) {
        this.mHelper = mHelper
    }

    /**
     * @param mContext  Context of the app
     * @param mFontSize font size of the ascii text in bitmap
     */
    constructor(mContext: Context, mFontSize: Float) : this(mContext) {
        this.mFontSize = ASCIIUtilities.spToPx(mFontSize, mContext)
    }

    /**
     *
     * @param mContext  Context of the app
     * @param mHelper   Customized ASCIIConverterHelper Class
     * @param mFontSize font size of the ascii text in bitmap
     */
    constructor(mContext: Context, mHelper: ASCIIConverterHelper, mFontSize: Int) : this(mContext) {
        this.mFontSize = ASCIIUtilities.spToPx(mFontSize.toFloat(), mContext)
        this.mHelper = mHelper
    }

    /**
     * @param dictionary Accepts key pair value of character and its luminance
     */
    constructor(mContext: Context, dictionary: Map<String, Float>) : this(mContext) {
        this.mHelper = ASCIIConverterHelper(dictionary)
        initDefaults()
    }

    private fun initDefaults() {
        setFontSize(18.0f)
        setReversedLuminance(false)
        setGrayScale(false)
        mColumns = 0.0f
    }

    /**
     * @param mGrayScale Enable to disable gray scale of the bitmap. Default is false to create colored ASCII Bitmap
     */
    fun setGrayScale(mGrayScale: Boolean) {
        this.mGrayScale = mGrayScale
    }

    /**
     * @param mReversedLuminance Reverses the luminance by subtracting from 1. Default is false
     */
    fun setReversedLuminance(mReversedLuminance: Boolean) {
        this.mReversedLuminance = mReversedLuminance
    }

    /**
     * @param mFontSize Set the font size in sp
     */
    fun setFontSize(mFontSize: Float) {
        this.mFontSize = ASCIIUtilities.spToPx(mFontSize, mContext)
    }

    /**
     * @param mBackgroundColor Adds a background color to ASCII Bitmap. Default is transparent
     */
    fun setBackgroundColor(mBackgroundColor: Int) {
        this.mBackgroundColor = mBackgroundColor
    }

    /******************************************************************************************
     *
     *
     *
     *
     ******************************************************************************************/

    /**
     * Creates an ASCII String of a bitmap
     * @param originalBitmap bitmap to create ASCII Bitmap
     * @return returns ASCII String
     */
    suspend fun createASCIIString(originalBitmap: Bitmap): String {
        return createASCIIString(originalBitmap, null)
    }

    suspend fun createASCIIString(originalBitmap: Bitmap, mTask: OnStringTaskListener?): String {
        mColumns = getGridWidth(originalBitmap.width)
        if (mColumns < 5) {
            throw InvalidParameterException("Columns count is very small. Font size needs to be reduced")
        }

        mStringTaskListener = mTask
        val result = createASCIIStringFromBitmap(originalBitmap)
        mStringTaskListener?.onTaskCompleted(result)
        return result
    }

    private suspend fun createASCIIStringFromBitmap(bitmap: Bitmap): String {
        val scaledImage = withContext(Dispatchers.Default) {
            ASCIIUtilities.resizeBitmap(bitmap, mColumns.toInt())
        }

        val grid = getGridData(scaledImage)

        return buildString {
            grid.blocks.forEach { row ->
                append(row.joinToString(" ") { block ->
                    block?.let {
                        val luminance = ASCIIConverterHelper.getLuminance(block, mReversedLuminance)
                        mHelper.asciiFromLuminance(luminance)
                    } ?: ""
                })
                appendLine()
            }
        }
    }

    /******************************************************************************************
     *
     *
     *
     *
     ******************************************************************************************/

    /**
     * Creates an ASCII String of a bitmap
     * @param bitmap bitmap to create ASCII Bitmap
     * @return returns ASCII Bitmap
     */

    @Throws(ExecutionException::class, InterruptedException::class, InvalidParameterException::class)
    suspend fun createASCIIImage(bitmap: Bitmap): Bitmap {
        return createASCIIImage(bitmap, null)
    }

    suspend fun createASCIIImage(originalBitmap: Bitmap, mTask: OnBitmapTaskListener?): Bitmap {
        mColumns = getGridWidth(originalBitmap.width)

        if (mColumns < 5) {
            throw InvalidParameterException("Columns count is very small. Font size needs to be reduced")
        }

        mBitmapTaskListener = mTask

        val result = createASCIIBitmap(originalBitmap)
        mBitmapTaskListener?.onTaskCompleted(result)
        return result
    }

    private suspend fun createASCIIBitmap(originalBitmap: Bitmap): Bitmap {
            val scaledImage = withContext(Dispatchers.Default) {
                ASCIIUtilities.resizeBitmap(originalBitmap, mColumns.toInt())
            }
            val grid = getGridData(scaledImage)

            val newBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)
            val canvas = Canvas(newBitmap)

            if (mBackgroundColor != Color.TRANSPARENT) {
                val mPaint = Paint().apply {
                    color = mBackgroundColor
                    style = Paint.Style.FILL
                }
                canvas.drawPaint(mPaint)
            }

            val paint = Paint().apply {
                color = Color.WHITE
                textSize = mFontSize
            }

            canvas.drawBitmap(newBitmap, 0.0f, 0.0f, paint)

            grid.blocks.forEachIndexed { row, blocks ->
                blocks.forEachIndexed { col, block ->
                    block?.let {
                        val luminance = ASCIIConverterHelper.getLuminance(block, mReversedLuminance)
                        val ascii = mHelper.asciiFromLuminance(luminance)

                        val color = ASCIIUtilities.getColor(block)
                        if (!mGrayScale) {
                            paint.color = color
                        } else {
                            paint.alpha = (luminance * 255.0f).toInt()
                            paint.color = Color.GRAY
                        }

                        canvas.drawText(ascii, row * mFontSize, col * mFontSize, paint)
                    }
                }
            }

            return newBitmap
        }


    /**
     *
     * @param width width of the image
     * @return returns the columns count
     */
    private fun getGridWidth(width: Int): Float {
        return if (mColumns == 0.0f) {
            width / mFontSize
        } else {
            mColumns
        }
    }

    /**
     *
     * @param bitmap Bitmap for fetching rgba data
     * @return returns grid containing rgba data of bitmap
     */
    private fun getGridData(bitmap: Bitmap): PixelGrid {
        val width = bitmap.width
        val height = bitmap.height

        val grid = PixelGrid(width, height, bitmap.width * bitmap.height)
        for (row in 0 until grid.width) {
            for (col in 0 until grid.height) {
                val pixel = bitmap.getPixel(row, col)

                val r = ((pixel shr 16) and 0xff) / 255.0f
                val g = ((pixel shr 8) and 0xff) / 255.0f
                val b = (pixel and 0xff) / 255.0f
                val a = ((pixel shr 24) and 0xff) / 255.0f

                grid.add(r, g, b, a, row, col)
            }
        }

        return grid
    }
}