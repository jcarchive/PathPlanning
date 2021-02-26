package com.mictlan.math.utils;

import static java.lang.Math.abs;

public class ComparatorsUtils {
    private static final float EPSILON = 0.01f;

    public static boolean eq(float a, float b){
        if(a == b) return true;

        final float absA = abs(a);
        final float absB = abs(b);
        final float absDiff = abs(a - b);

        if( a == 0 || b == 0 || (absA + absB) < Float.MIN_NORMAL){
            return absDiff < EPSILON;
        }

        return absDiff/(absA + absB) < EPSILON;
    }

    public static boolean gte(float a, float b) {
        return eq(a,b) || a > b;
    }

    public static boolean gt(float a, float b){
        return a > b;
    }

    public static boolean lte(float a, float b){
        return a < b || eq(a,b);
    }

    public static boolean lt(float a, float b){
        return a < b;
    }
}
