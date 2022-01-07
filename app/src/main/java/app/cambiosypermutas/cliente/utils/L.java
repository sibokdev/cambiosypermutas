package app.cambiosypermutas.cliente.utils;

import android.util.Log;

import app.cambiosypermutas.cliente.BuildConfig;


/**
 * Created by NandoVelazquez on 8/1/16.
 */
public class L {

    private static final String TAG = "Boveda DOX";

    public static void info(String msg) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, msg);
    }

    public static void warning(String msg) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, msg);
    }

    public static void error(String msg) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, msg);
    }

}
