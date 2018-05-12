package com.example.caroleenanwar.movie.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 12-May-18.
 */

public class PrefUtils {

    private static PrefUtils prefUtils;
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static String CURRENTPAGE="currentPage";
    public static String TOTALPAGE="TOTALPage";
    private PrefUtils(Context context, String namePreferences, int mode) {
        this.context = context;
        if (namePreferences == null || namePreferences.equals("")) {
            namePreferences = "complex_preferences";
        }
        preferences = context.getSharedPreferences(namePreferences, mode);
        editor = preferences.edit();
    }

    public static PrefUtils getComplexPreferences(Context context,
                                                           String namePreferences, int mode) {

		if (prefUtils == null) {
        prefUtils = new PrefUtils(context,
                namePreferences, mode);
		}

        return prefUtils;
    }
    public void putNumber(String key,int number) {


        editor.putInt(key, number);
    }

    public void commit() {
        editor.commit();
    }

    public void clearObject() {
        editor.clear();
    }

    public int getNumber(String key) {

        int number = preferences.getInt(key, 0);
       return number;

    }
}
