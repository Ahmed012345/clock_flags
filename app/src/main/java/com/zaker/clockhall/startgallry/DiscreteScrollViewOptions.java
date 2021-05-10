package com.zaker.clockhall.startgallry;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.zaker.clockhall.App;
import com.zaker.clockhall.R;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class DiscreteScrollViewOptions {


    private static DiscreteScrollViewOptions instance;

    private final String KEY_TRANSITION_TIME;

    public static void init(Context context) {
        instance = new DiscreteScrollViewOptions(context);
    }

    private DiscreteScrollViewOptions(Context context) {
        KEY_TRANSITION_TIME = context.getString(R.string.pref_key_transition_time);
    }

    public static int getTransitionTime() {
        return defaultPrefs().getInt(instance.KEY_TRANSITION_TIME, 150);
    }

    @SuppressWarnings("deprecation")
    private static SharedPreferences defaultPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance());
    }

}
