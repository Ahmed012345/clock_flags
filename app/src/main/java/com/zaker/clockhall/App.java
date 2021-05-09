package com.zaker.clockhall;

import android.content.Context;
import com.akexorcist.localizationactivity.ui.LocalizationApplication;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.zaker.clockhall.easysharedpreferences.EasySharedPreferenceConfig;
import com.zaker.clockhall.startgallry.DiscreteScrollViewOptions;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;

public class App extends LocalizationApplication {

    public boolean lockScreenShow=false;
    public int notificationId=1989;
    private static App instance;

    public static App getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DiscreteScrollViewOptions.init(this);
        EasySharedPreferenceConfig.Companion.initDefault(new EasySharedPreferenceConfig.Builder()
                .inputFileName("easy_preference")
                .inputMode(Context.MODE_PRIVATE)
                .build());

        DiscreteScrollViewOptions.init(this);
        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

    }


    @Override
    public @NotNull Locale getDefaultLanguage() {
        return new Locale("ar", "EG");
    }
}
