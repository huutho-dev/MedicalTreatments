package com.edu.gvn.medicaltreatments.common;

import android.util.Log;

/**
 * Created by hnc on 21/10/2016.
 */

public class LogUtils {
    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }
    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

}
