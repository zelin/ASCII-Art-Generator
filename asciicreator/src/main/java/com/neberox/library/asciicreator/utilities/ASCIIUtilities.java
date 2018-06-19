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

package com.neberox.library.asciicreator.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;

import com.neberox.library.asciicreator.models.PixelBlock;

/**
 * Utility functions
 * @author Muhammad Umar (ee_umar@yahoo.com)
 */

public class ASCIIUtilities
{
    public static Bitmap resizeBitmap(Bitmap bitmap, int scaleFactor)
    {
        int outWidth;
        int outHeight;

        if (scaleFactor <= 1)
            return bitmap;

        if (scaleFactor > Math.min(bitmap.getHeight(), bitmap.getWidth()))
            scaleFactor = Math.min(bitmap.getHeight(), bitmap.getWidth());

        float ratio = (float) scaleFactor / (float) bitmap.getWidth();

        outWidth = scaleFactor;
        outHeight = (int)(ratio * bitmap.getHeight());

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, true);
        return resizedBitmap;
    }

    public static int getColor(PixelBlock block)
    {
        return ((int) (block.a * 255.0f + 0.5f) << 24) |
                ((int) (block.r   * 255.0f + 0.5f) << 16) |
                ((int) (block.g * 255.0f + 0.5f) <<  8) |
                (int) (block.b  * 255.0f + 0.5f);
    }

    public static int spToPx(float sp, Context context)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
