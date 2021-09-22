package app.oficiodigital.cliente.settings;

import android.content.Context;
import android.preference.PreferenceActivity;

/**
 * Created by NandoVelazquez on 8/18/16.
 */
public class Preferences extends PreferenceActivity {

    private final static String SETTINGS_FILE_NAME = "dox_settings";
    private final static String KEY_LOGIN = "login";
    private final static String KEY_LATITUDE = "latitude";
    private final static String KEY_LONGITUDE = "longitude";

    public static void setLogin(Context ctx, boolean login) {
        ctx.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE).edit().putBoolean(KEY_LOGIN, login).commit();
    }

    public static boolean getLogin(Context ctx) {
        return ctx.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE).getBoolean(KEY_LOGIN, false);
    }

    public static void setLatitude(Context ctx, String lat) {
        ctx.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE).edit().putString(KEY_LATITUDE, lat).commit();
    }

    public static String getLatitude(Context ctx) {
        return ctx.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE).getString(KEY_LATITUDE, "");
    }

    public static void setLongitude(Context ctx, String lon) {
        ctx.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE).edit().putString(KEY_LONGITUDE, lon).commit();
    }

    public static String getLongitude(Context ctx) {
        return ctx.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE).getString(KEY_LONGITUDE, "");
    }


}
