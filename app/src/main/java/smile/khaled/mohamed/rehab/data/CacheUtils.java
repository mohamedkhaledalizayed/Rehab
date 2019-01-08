package smile.khaled.mohamed.rehab.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import asia.fivejuly.securepreferences.SecurePreferences;

public class CacheUtils {

    private static final String ENCRYPT_KEY = "rehab@742mhfs9$", PREFS_FILE = "rehab_prefs";

    @NonNull
    public static SharedPreferences getSharedPreferences(Context context) {
        return new SecurePreferences.Builder(context)
                .password(ENCRYPT_KEY)
                .filename(PREFS_FILE)
                .build();
    }

    public static String checkUserState(Context context,String key){
        String state = getSharedPreferences(context).getString(key, "false");
        return state;
    }

    public static String checkUserType(Context context,String key){
        String state = getSharedPreferences(context).getString(key, null);
        return state;
    }

    public static String getPatientData(Context context,String key){
        String state = getSharedPreferences(context).getString(key, null);
        return state;
    }

    public static String getUserLanguage(Context context,String key){
        Configuration config = context.getResources().getConfiguration();
        String lang = getSharedPreferences(context).getString(key, config.locale.getLanguage());
        return lang;
    }

    public static void clearCache(Context context) {
        getSharedPreferences(context).edit().clear().apply();
    }


}
