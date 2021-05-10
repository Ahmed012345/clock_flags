package com.zaker.clockhall.startgallry;

import android.content.Context;
import com.marcoscg.licenser.Library;
import com.marcoscg.licenser.License;
import com.marcoscg.licenser.LicenserDialog;
import com.zaker.clockhall.R;


public class DemoUtils  {

    public static void showLicensesDialog(Context context) {
      new LicenserDialog(context, R.style.DialogStyle)
              .setTitle("Licenses")
              .setLibrary(new Library("Android Support Libraries",
                        "https://developer.android.com/topic/libraries/support-library/index.html",
                        License.Companion.getAPACHE1()))
              .setLibrary(new Library("Dynamic Sizes",
                        "https://github.com/MrNouri/DynamicSizes",
                        License.Companion.getAPACHE2()))
              .setLibrary(new Library("Easy-SharedPreferences",
                        "https://github.com/AmanpreetYatin/Easy-SharedPreferences",
                        License.Companion.getAPACHE2()))
              .setLibrary(new Library("FloatingActionButton",
                      "https://github.com/Clans/FloatingActionButton",
                      License.Companion.getAPACHE2()))
              .setLibrary(new Library("Glide",
                      "https://github.com/bumptech/glide",
                      License.Companion.getAPACHE2()))
              .setLibrary(new Library("Discrete ScrollView",
                      "https://github.com/yarolegovich/DiscreteScrollView",
                      License.Companion.getAPACHE2()))
              .setLibrary(new Library("Nine Old Androids",
                      "https://github.com/JakeWharton/NineOldAndroids",
                      License.Companion.getAPACHE2()))
              .setLibrary(new Library("FAB Reveal Layout",
                      "https://github.com/truizlop/FABRevealLayout",
                      License.Companion.getAPACHE2()))
              .setLibrary(new Library("Boom Menu",
                      "https://github.com/Nightonke/BoomMenu",
                      License.Companion.getAPACHE2()))
              .setLibrary(new Library("Wifi Utils",
                      "https://github.com/ThanosFisherman/WifiUtils",
                      License.Companion.getAPACHE2()))
              .setLibrary(new Library("Finest WebView",
                      "https://github.com/TheFinestArtist/FinestWebView-Android",
                      License.Companion.getMIT()))
              .setLibrary(new Library("Cycle Menu",
                      "https://github.com/Cleveroad/CycleMenu",
                      License.Companion.getMIT()))
                .setLibrary(new Library("Shareable",
                        "https://github.com/robertsimoes/Shareable",
                        License.Companion.getMIT()))
                .setLibrary(new Library("Licenser",
                        "https://github.com/marcoscgdev/Licenser",
                        License.Companion.getMIT()))
                .setLibrary(new Library("Click Shrink Effect",
                        "https://github.com/realpacific/click-shrink-effect",
                        License.Companion.getMIT()))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

}

