package com.example;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class todoboom extends Application {
    String TAG = "Applicaion";

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String string = sharedPreferences.getString("tasks_list", null);
        Log.d(TAG, "string - before:" + string);

        if (string != null)
        {
            Log.d(TAG, "string - after:" + string);
            String[] cur_S = string.split(";");
            Log.d(TAG,  "number of tasks:" + (cur_S.length/2));
        }
    }
}
