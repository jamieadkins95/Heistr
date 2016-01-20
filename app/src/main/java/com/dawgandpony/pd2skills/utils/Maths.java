package com.dawgandpony.pd2skills.utils;

/**
 * Created by Jamie on 20/01/2016.
 */
public class Maths {
    public static float round (double value) {
        int scale = (int) Math.pow(10, 2);
        return (float) Math.round(value * scale) / scale;
    }
}
