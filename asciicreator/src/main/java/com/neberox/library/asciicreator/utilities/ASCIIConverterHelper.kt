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

import com.neberox.library.asciicreator.models.ASCIIMetrics
import com.neberox.library.asciicreator.models.PixelBlock

/**
 * A class to store ASCII and their luminance metrics
 * @author Muhammad Umar (ee_umar@yahoo.com)
 */
class ASCIIConverterHelper(mappingData: Map<String, Float> = createDefaultMap()) {

    private val mMetrices: List<ASCIIMetrics> = buildDataForMapping(mappingData)

    companion object {
        private fun createDefaultMap(): Map<String, Float> {
            return mapOf(
                " " to 1.0f,
                "`" to 0.95f,
                "." to 0.92f,
                "," to 0.9f,
                "-" to 0.8f,
                "~" to 0.75f,
                "+" to 0.7f,
                "<" to 0.65f,
                ">" to 0.6f,
                "o" to 0.55f,
                "=" to 0.5f,
                "*" to 0.35f,
                "%" to 0.3f,
                "X" to 0.1f,
                "@" to 0.0f
            )
        }

        fun getLuminance(block: PixelBlock, reversed: Boolean): Float {
            var luminance = 0.2126 * block.r + 0.7152 * block.g + 0.0722 * block.b
            if (reversed) {
                luminance = 1.0 - luminance
            }
            return luminance.toFloat()
        }
    }

    private fun buildDataForMapping(mappingData: Map<String, Float>): List<ASCIIMetrics> {
        val metrics = ArrayList<ASCIIMetrics>()
        for ((key, value) in mappingData) {
            val metric = ASCIIMetrics(key, value)
            metrics.add(metric)
        }

        metrics.sortByDescending { it.luminance }

        return metrics
    }

    fun asciiFromLuminance(luminance: Float): String {
        var searchedIndex = 0

        for ((index, metric) in mMetrices.withIndex()) {
            if (luminance >= metric.luminance) {
                searchedIndex = index
                break
            }
        }

        return mMetrices[searchedIndex].ascii ?: ""
    }
}
