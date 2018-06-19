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

import com.neberox.library.asciicreator.models.ASCIIMetrics;
import com.neberox.library.asciicreator.models.PixelBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to store ASCII and their luminance metrics
 * @author Muhammad Umar (ee_umar@yahoo.com)
 */

public class ASCIIConverterHelper
{
    private ArrayList<ASCIIMetrics> mMetrices;

    private static Map<String, Float> createDefaultMap()
    {
        Map<String, Float> map = new HashMap<String, Float>();
        map.put(" ", 1.0f);
        map.put("`", 0.95f);
        map.put(".", 0.92f);
        map.put(",", 0.9f);
        map.put("-", 0.8f);
        map.put("~", 0.75f);
        map.put("+", 0.7f);
        map.put("<", 0.65f);
        map.put(">", 0.6f);
        map.put("o", 0.55f);
        map.put("=", 0.5f);
        map.put("*", 0.35f);
        map.put("%", 0.3f);
        map.put("X", 0.1f);
        map.put("@", 0.0f);

        return map;
    }

    public ASCIIConverterHelper()
    {
        this(createDefaultMap());
    }

    public ASCIIConverterHelper(Map<String, Float> mappingData)
    {
        mMetrices = buildDataForMapping(mappingData);
    }

    private ArrayList<ASCIIMetrics> buildDataForMapping(Map<String, Float> mappingData)
    {
        ArrayList<ASCIIMetrics> metrics = new ArrayList<>();
        for (Map.Entry<String, Float> entry : mappingData.entrySet())
        {
            ASCIIMetrics metric = new ASCIIMetrics(entry.getKey(), entry.getValue());
            metrics.add(metric);
        }

        Collections.sort(metrics, new Comparator<ASCIIMetrics>()
        {
            @Override
            public int compare(ASCIIMetrics o1, ASCIIMetrics o2)
            {
                Float lum1 = o1.getLuminance();
                Float lum2 = o2.getLuminance();
                return lum2.compareTo(lum1);//  In DESC Order
            }
        });

        return metrics;
    }

    public String asciiFromLuminance(float luminance)
    {
        int searchedIndex = 0;

        for (int i = 0; i < mMetrices.size(); i++)
        {
            ASCIIMetrics mMetrice = mMetrices.get(i);
            if (luminance >= mMetrice.getLuminance())
            {
                searchedIndex = i;
                break;
            }
        }

        return mMetrices.get(searchedIndex).getAscii();
    }

    public static float getLuminance(PixelBlock block, boolean reversed)
    {
        float r = block.r;
        float g = block.g;
        float b = block.b;

        // See wikipedia article on grayscale for an explanation of this formula.
        // http://en.wikipedia.org/wiki/Grayscale
        double luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;
        if (reversed)
        {
            luminance = (1.0 - luminance);
        }
        return (float) luminance;
    }
}
