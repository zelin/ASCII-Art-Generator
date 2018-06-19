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

package com.neberox.library.asciicreator.models;

/**
 * Created by Muhammad Umar on 17/06/2018.
 * @author Muhammad Umar (ee_umar@yahoo.com)
 */

public class ASCIIMetrics
{
    private String ascii = null;
    private float  luminance = 0;

    public ASCIIMetrics(String characters, float luminance)
    {
        this.ascii = characters;
        this.luminance = luminance;
    }

    public float getLuminance()
    {
        return luminance;
    }

    public void setLuminance(float luminance)
    {
        this.luminance = luminance;
    }

    public String getAscii()
    {
        return ascii;
    }

    public void setAscii(String ascii)
    {
        this.ascii = ascii;
    }
}
