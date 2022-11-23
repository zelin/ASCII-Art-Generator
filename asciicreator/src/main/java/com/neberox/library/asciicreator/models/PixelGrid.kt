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
package com.neberox.library.asciicreator.models

/**
 * Created by Muhammad Umar on 17/06/2018.
 * @author Muhammad Umar (ee_umar@yahoo.com)
 */
class PixelGrid(width: Int, height: Int, length: Int) {

    var width: Int = width
        private set

    var height: Int = height
        private set

    var pixelCount: Int = length
        private set

    var blocks: Array<Array<PixelBlock?>> = Array(width) { arrayOfNulls<PixelBlock>(height) }

    fun add(r: Float, g: Float, b: Float, a: Float, row: Int, column: Int) {
        val block = PixelBlock(r, g, b, a)
        blocks[row][column] = block
    }

    init {
        require(width > 0 && height > 0) { "Width and height must be positive values." }
        blocks = Array(width) { arrayOfNulls<PixelBlock>(height) }
        pixelCount = length
    }
}