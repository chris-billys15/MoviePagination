package com.example.movielistapp.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public PreferenceHelper(Context context, String preferenceName){
        sharedPreferences = context.getApplicationContext().getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putBoolean(String key, boolean value){
        editor.putBoolean(key,value);
        editor.commit();
    }

    public boolean getBoolean(String movieID) {
        return sharedPreferences.getBoolean(movieID,false);
    }
}
