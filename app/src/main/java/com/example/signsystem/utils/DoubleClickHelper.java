package com.example.signsystem.utils;

import android.os.SystemClock;

public final class DoubleClickHelper {

    private static final long[] TIME_ARRAY = new long[2];

    public static boolean isOnDoubleClick() {

        return isOnDoubleClick(1500);
    }

    public static boolean isOnDoubleClick(int time) {
        System.arraycopy(TIME_ARRAY, 1, TIME_ARRAY, 0, TIME_ARRAY.length - 1);
        TIME_ARRAY[TIME_ARRAY.length - 1] = SystemClock.uptimeMillis();
        return TIME_ARRAY[0] >= (SystemClock.uptimeMillis() - time);
    }
}