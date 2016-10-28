package com.edu.gvn.medicaltreatments.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hnc on 21/10/2016.
 */

public class ReferencesUtils {

    private final String PATH_DB_MEDICAL = "path.db.medical";
    private final String SHAREPREFERENCE = "mSharepreference";

    private static ReferencesUtils mInstance;
    private SharedPreferences preferences;
    private Context context;

    public static synchronized ReferencesUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ReferencesUtils(context);
        }
        return mInstance;
    }


    private ReferencesUtils(Context context) {
        this.context = context;
    }

    public String getPartMedical() {
        preferences = context.getSharedPreferences(SHAREPREFERENCE, Context.MODE_PRIVATE);
        String dbPath =   preferences.getString(PATH_DB_MEDICAL, "null");
        LogUtils.v("huutho","get path : " + dbPath);
        return dbPath;
    }

    public void savePartMedical(String path) {
        if (path != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(PATH_DB_MEDICAL, path);
            editor.commit();

            LogUtils.v("huutho","save path : " + path);
        }
    }
}
