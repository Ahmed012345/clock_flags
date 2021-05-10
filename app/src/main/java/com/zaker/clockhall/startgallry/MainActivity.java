package com.zaker.clockhall.startgallry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.cleveroad.sy.cyclemenuwidget.CycleMenuWidget;
import com.cleveroad.sy.cyclemenuwidget.OnMenuItemClickListener;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.marcoscg.materialtoast.MaterialToast;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.OnBoomListenerAdapter;
import com.nightonke.boommenu.Util;
import com.thanosfisherman.wifiutils.WifiUtils;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.zaker.android.sapeh.app.main.activitymain.NetworkChecker;
import com.zaker.clockhall.BuildConfig;
import com.zaker.clockhall.R;
import com.zaker.clockhall.clockview.Clock;
import com.zaker.clockhall.easysharedpreferences.EasySharedPreference;
import com.zaker.clockhall.finestwebview.FinestWebView;
import com.zaker.clockhall.lockscreen.LockScreen;
import com.zaker.clockhall.small_library.FontManger;
import com.zaker.clockhall.small_library.LocaleHelper;
import com.zaker.clockhall.small_library.Shareable;
import com.zaker.clockhall.startgallry.receiver.AlarmReceiver1;
import com.zaker.clockhall.widget.AppUpdateDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.zaker.clockhall.lockscreen.LockScreenActivity.KEY_IMAGE;
import static com.zaker.clockhall.lockscreen.LockScreenActivity.KEY_MARQUEE;

public class MainActivity extends LocalizationActivity implements
        DiscreteScrollView.ScrollStateChangeListener<ForecastAdapter.ViewHolder>,
        DiscreteScrollView.OnItemChangedListener<ForecastAdapter.ViewHolder>,
        View.OnClickListener{

    private List<Forecast> forecasts;

    private ForecastView forecastView;
    private DiscreteScrollView cityPicker;

    private ImageButton btnShare;
    private FloatingActionMenu menuGreen;
    private FloatingActionButton fabGreen1, fabGreen2, fabGreen3;
    private TextView nashed, nashed1, nashed2, nashed3, nashed4, nashed5, nashed6, nashed7, nashed8, nashed9, nashed10, nashed11,
            nashed12, nashed13, nashed14, nashed15, nashed16, nashed17, nashed18;
    String egyptLanguageCode = "ar";
    String londonLanguagwCode = "en";
    Clock clock;
    Context context = this;
    com.google.android.material.floatingactionbutton.FloatingActionButton fab;
    ImageButton Revel_close;
    private FABRevealLayout fabRevealLayout;
    final String KEY_WEB = "WEB";
    public final static String KEY_CLOCK_H = "IMAGE_H";
    public final static String KEY_CLOCK_M = "IMAGE_M";
    public final static String KEY_CLOCK_S = "IMAGE_S";
    CycleMenuWidget cycleMenuWidget;
    boolean doubleBackToExitPressedOnce = false;
    String message = "رابط التطبيق App Link";
    String url = "https://play.google.com/store/apps/details?id=com.zaker.android.sapeh";
    BoomMenuButton bmb;
    MediaPlayer b1;

    private FirebaseCrashlytics crashlytics;
    private static final String FB_RC_KEY_TITLE="update_title";
    private static final String FB_RC_KEY_DESCRIPTION="update_description";
    private static final String FB_RC_KEY_FORCE_UPDATE_VERSION="force_update_version";
    private static final String FB_RC_KEY_LATEST_VERSION="latest_version";
    AppUpdateDialog appUpdateDialog;
    FirebaseRemoteConfig mFirebaseRemoteConfig;

    private static final String TAG = "MainActivity";
    private AdView mAdView1, mAdView2, mAdView3;
    MediaPlayer mMediaPlayer;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logUser();
        setContentView(R.layout.activity_main);

        checkAppUpdate();
        FontManger.getInstance(getApplicationContext().getAssets());
        bindview();
        configureFABReveal();
        LockScreen.getInstance().init(this,true);

        forecasts = WeatherStation.get().getForecasts();
        cityPicker.setSlideOnFling(true);
        cityPicker.setAdapter(new ForecastAdapter(forecasts));
        cityPicker.addOnItemChangedListener(this);
        cityPicker.addScrollStateChangeListener(this);
        cityPicker.scrollToPosition(1);
        cityPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
        cityPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        forecastView.setForecast(forecasts.get(0));

        btnShare.setOnClickListener(this);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);

            }
        });

        menuGreen.setClosedOnTouchOutside(true);
        menuGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fabGreen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show on lock screen
                LockScreen.getInstance().active();


            }
        });

           fabGreen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // open activity about
                LockScreen.getInstance().deactivate();

            }
        });

         fabGreen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // open activity about
                startActivity(new Intent(context, AboutActivity2.class));
                menuGreen.close(true);
                // show on notifction

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkChecker.isNetworkConnected(MainActivity.this)) {
                    new FinestWebView.Builder(context)
                            .theme(R.style.FinestWebViewTheme).showUrl(false).webViewBuiltInZoomControls(true)
                            .webViewDisplayZoomControls(true).statusBarColorRes(R.color.blueDark).toolbarColorRes(R.color.blue)
                            .titleColorRes(R.color.finestWhite).urlColorRes(R.color.settings_title_bg)
                            .iconDefaultColorRes(R.color.finestWhite).progressBarColorRes(R.color.finestWhite)
                            .stringResCopiedToClipboard(R.string.copied_to_clipboard).swipeRefreshColorRes(R.color.blueDark)
                            .menuSelector(R.drawable.selector_light_theme).menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.END)
                            .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft).dividerHeight(0).gradientDivider(false)
                            .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold, R.anim.slide_right_out)
                            .disableIconBack(false).disableIconClose(false).disableIconForward(false).disableIconMenu(false)
                            .show(EasySharedPreference.Companion.getString(KEY_WEB, "https://ar.wikipedia.org/wiki/Egypt"));
                }  else {
                    new MaterialToast(context)
                            .setMessage(getResources().getString(R.string.fetchs))
                            .setIcon(R.drawable.nowifi)
                            .setDuration(Toast.LENGTH_LONG)
                            .setBackgroundColor(Color.parseColor("#EE4035"))
                            .show();
        }
            }
        });

        Revel_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    fabRevealLayout.revealMainView();
            }
        });
        cycleMenuWidget.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view, int itemPosition) {
                switch (itemPosition){
                    case 0:
                        setLanguage(new Locale("ar", "EG"));
                        LocaleHelper.setLocale(MainActivity.this,egyptLanguageCode);
                        finish();
                        Intent f1 = new Intent(MainActivity.this, MainActivity.class);
                        f1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(f1);
                        new MaterialToast(context)
                                .setMessage("العربية")
                                .setIcon(R.drawable.egypt)
                                .setDuration(Toast.LENGTH_LONG)
                                .setBackgroundColor(Color.parseColor("#607D8B"))
                                .show();
                        break;
                    case 1:
                        setLanguage(new Locale("en", "GB"));
                        LocaleHelper.setLocale(MainActivity.this,londonLanguagwCode);
                        finish();
                        Intent f2 = new Intent(MainActivity.this, MainActivity.class);
                        f2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(f2);
                        new MaterialToast(context)
                                .setMessage("english")
                                .setIcon(R.drawable.england)
                                .setDuration(Toast.LENGTH_LONG)
                                .setBackgroundColor(Color.parseColor("#607D8B"))
                                .show();
                        break;
                }
            }

            @Override
            public void onMenuItemLongClick(View view, int itemPosition) {

            }
        });

        bmb = findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setShareLineLength(30);
        bmb.setShareLineWidth(4);
        bmb.setDotRadius(9);

        float w = Util.dp2px(80);
        float h = Util.dp2px(96);
        float h_0_5 = h / 2;
        float h_1_5 = h * 1.5f;

        float hm = bmb.getButtonHorizontalMargin();
        float vm = bmb.getButtonVerticalMargin();
        float vm_0_5 = vm / 2;
        float vm_1_5 = vm * 1.5f;

        bmb.getCustomButtonPlacePositions().add(new PointF(-w - hm, -h_1_5 - vm_1_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(      0, -h_1_5 - vm_1_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(+w + hm, -h_1_5 - vm_1_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(-w - hm, -h_0_5 - vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(      0, -h_0_5 - vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(+w + hm, -h_0_5 - vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(-w - hm, +h_0_5 + vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(      0, +h_0_5 + vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(+w + hm, +h_0_5 + vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(-w - hm, +h_1_5 + vm_1_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(      0, +h_1_5 + vm_1_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(+w + hm, +h_1_5 + vm_1_5));

        TextOutsideCircleButton.Builder facebook = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.facebook)
            .normalTextRes(R.string.sharefacebook)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {


                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.FACEBOOK)
                        .url(url)
                        .build();
                    shareInstance.share();

                }
            });
        bmb.addBuilder(facebook);

        TextOutsideCircleButton.Builder twitter = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.twitter)
            .normalTextRes(R.string.sharetwitter)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.TWITTER)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(twitter);

        TextOutsideCircleButton.Builder google = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.gmail)
            .normalTextRes(R.string.sharegmail)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.GOOGLE)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(google);

        TextOutsideCircleButton.Builder whatsapp = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.whatsapp)
            .normalTextRes(R.string.sharewhatapp)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.WHATSAPP)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(whatsapp);

        TextOutsideCircleButton.Builder instagram = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.instagram)
            .normalTextRes(R.string.shareinstagram)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.INSTAGRAM)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(instagram);

        TextOutsideCircleButton.Builder tumblr = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.tumblr)
            .normalTextRes(R.string.sharetumblr)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.TUMBLR)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(tumblr);

        TextOutsideCircleButton.Builder linkedin = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.linkedin)
            .normalTextRes(R.string.sharelinkedin)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.LINKED_IN)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(linkedin);

        TextOutsideCircleButton.Builder mail = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.viber)
            .normalTextRes(R.string.shareviber)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.VIBER)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(mail);

        TextOutsideCircleButton.Builder snapchat = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.snapchat)
            .normalTextRes(R.string.sharesnapchat)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.SNAPCHAT)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(snapchat);

        TextOutsideCircleButton.Builder skype = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.skype)
            .normalTextRes(R.string.shareskype)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.SKYPE)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(skype);

        TextOutsideCircleButton.Builder telegram = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.telegram)
            .normalTextRes(R.string.sharetelegram)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.TELEGRAM)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(telegram);

        TextOutsideCircleButton.Builder reddit = new TextOutsideCircleButton.Builder()
            .normalImageRes(R.drawable.reddit)
            .normalTextRes(R.string.sharereddit)
            .listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    Shareable shareInstance = new Shareable.Builder(context)
                        .message(message)
                        .socialChannel(Shareable.Builder.REDDIT)
                        .url(url)
                        .build();
                    shareInstance.share();
                }
            });
        bmb.addBuilder(reddit);

        b1 = MediaPlayer.create(this, R.raw.bmbm);

// Use OnBoomListenerAdapter to listen part of methods
        bmb.setOnBoomListener(new OnBoomListenerAdapter() {
            @Override
            public void onBoomWillShow() {
                super.onBoomWillShow();
                // logic here

            }
        });

        // Use OnBoomListener to listen all methods
        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                // If you have implement listeners for boom-buttons in builders,
                // then you shouldn't add any listener here for duplicate callbacks.
            }

            /**
             * When the background of boom-buttons is clicked.
             */
            @Override
            public void onBackgroundClick() {
                b1.start();

            }

            /**
             * When the BMB is going to hide its boom-buttons.
             */
            @Override
            public void onBoomWillHide() {
                b1.start();
            }

            /**
             * When the BMB finishes hide animations.
             */
            @Override
            public void onBoomDidHide() {

            }

            /**
             * When the BMB is going to show its boom-buttons.
             */
            @Override
            public void onBoomWillShow() {
                WifiUtils.withContext(getApplicationContext()).scanWifi(this::getScanResults).start();

                b1.start();
            }

            private void getScanResults(@NonNull final List<ScanResult> results) {
                if (results.isEmpty())
                {
                    Log.i(TAG, "SCAN RESULTS IT'S EMPTY");
                    return;
                }
                Log.i(TAG,"GOT SCAN RESULTS " + results);
            }

            /**
             * When the BMB finished boom animations.
             */
            @Override
            public void onBoomDidShow() {

            }
        });

        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);

        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);

        AdRequest adRequest3 = new AdRequest.Builder().build();
        mAdView3.loadAd(adRequest3);

    }

    public void Alert1(int hour, int minute, int second){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR, hour);
        calendar1.set(Calendar.MINUTE, minute);
        calendar1.set(Calendar.SECOND, second);
        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver1.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(MainActivity.this, 0, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am1 = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
        assert am1 != null;
        am1.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
    }

    public void Alert2(int hour, int minute, int second){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR, hour);
        calendar1.set(Calendar.MINUTE, minute);
        calendar1.set(Calendar.SECOND, second);
        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver1.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(MainActivity.this, 0, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am1 = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
        assert am1 != null;
        am1.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
    }


    private void logUser() {
             // TODO: Use the current user's information
             // You can call any combination of these three methods
        crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.setCrashlyticsCollectionEnabled(true);
        crashlytics.checkForUnsentReports();
        crashlytics.sendUnsentReports();
        crashlytics.setUserId("id-99999");
        crashlytics.setCustomKey("Name", "Clocks");
        crashlytics.setCustomKey("Email", "asmmya7@gmail.com");

         }

        private void checkAppUpdate() {
        final int versionCode = BuildConfig.VERSION_CODE;

        final HashMap<String, Object> defaultMap = new HashMap<>();
        defaultMap.put(FB_RC_KEY_TITLE, getResources().getString(R.string.dialog_title));
        defaultMap.put(FB_RC_KEY_DESCRIPTION, getResources().getString(R.string.dialog_message));
        defaultMap.put(FB_RC_KEY_FORCE_UPDATE_VERSION, ""+versionCode);
        defaultMap.put(FB_RC_KEY_LATEST_VERSION, ""+versionCode);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mFirebaseRemoteConfig.setConfigSettingsAsync(new FirebaseRemoteConfigSettings.Builder().build());

        mFirebaseRemoteConfig.setDefaultsAsync(defaultMap);

        Task<Void> fetchTask=mFirebaseRemoteConfig.fetch(BuildConfig.DEBUG?0: TimeUnit.HOURS.toSeconds(4));

        fetchTask.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // After config data is successfully fetched, it must be activated before newly fetched
                    // values are returned.
                    new MaterialToast(context)
                        .setMessage(getResources().getString(R.string.fetchs1))
                        .setIcon(R.drawable.wifi)
                        .setDuration(Toast.LENGTH_LONG)
                        .setBackgroundColor(Color.parseColor("#55b859"))
                        .show();
                    mFirebaseRemoteConfig.fetchAndActivate();

                    String title=getValue(FB_RC_KEY_TITLE,defaultMap);
                    String description=getValue(FB_RC_KEY_DESCRIPTION,defaultMap);
                    int forceUpdateVersion= Integer.parseInt(getValue(FB_RC_KEY_FORCE_UPDATE_VERSION,defaultMap));
                    int latestAppVersion= Integer.parseInt(getValue(FB_RC_KEY_LATEST_VERSION,defaultMap));

                    boolean isCancelable=true;

                    if(latestAppVersion>versionCode)
                    {
                        if(forceUpdateVersion>versionCode)
                            isCancelable=false;

                        appUpdateDialog = new AppUpdateDialog(MainActivity.this, title, description, isCancelable);
                        appUpdateDialog.setCancelable(false);
                        appUpdateDialog.show();

                        Window window = appUpdateDialog.getWindow();
                        assert window != null;
                        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    }

                } else {

                    new MaterialToast(context)
                        .setMessage(getResources().getString(R.string.fetchs))
                        .setIcon(R.drawable.nowifi)
                        .setDuration(Toast.LENGTH_LONG)
                        .setBackgroundColor(Color.parseColor("#EE4035"))
                        .show();
                }
            }
        });
    }
    public String getValue(String parameterKey, HashMap<String, Object> defaultMap)
    {
        String value=mFirebaseRemoteConfig.getString(parameterKey);
        if(TextUtils.isEmpty(value))
            value= (String) defaultMap.get(parameterKey);

        return value;
    }


    private void configureFABReveal() {
        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
                showMainViewItems();

            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                showSecondaryViewItems();
            }
        });
    }

    private void showMainViewItems() {
        scale(cityPicker, 50);
        scale(menuGreen, 150);
        scale(btnShare, 250);

    }

    private void showSecondaryViewItems() {
        scale(nashed, 0); scale(nashed1, 100); scale(nashed2, 150); scale(nashed3, 100);
        scale(nashed4, 200); scale(nashed5, 30); scale(nashed6, 130); scale(nashed7, 180);
        scale(nashed8, 130); scale(nashed9, 230); scale(nashed10, 50); scale(nashed11, 150);
        scale(nashed12, 200); scale(nashed13, 150); scale(nashed14, 250); scale(nashed15, 80);
        scale(nashed16, 180); scale(nashed17, 280); scale(nashed18, 300); scale(Revel_close, 350);
    }

    private void scale(View view, long delay){
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate()
                .scaleX(1)
                .scaleY(1)
                .setDuration(500)
                .setStartDelay(delay)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }


    private void bindview() {
        menuGreen = findViewById(R.id.menu_green); fabGreen1 = findViewById(R.id.fabGreen1);
        cityPicker = findViewById(R.id.forecast_city_picker); btnShare = findViewById(R.id.btn_share);
        clock = findViewById(R.id.clock); fab = findViewById(R.id.fab); nashed = findViewById(R.id.nashed);
        nashed1 = findViewById(R.id.nashed1); nashed2 = findViewById(R.id.nashed2); nashed3 = findViewById(R.id.nashed3);
        nashed4 = findViewById(R.id.nashed4); nashed5 = findViewById(R.id.nashed5); nashed6 = findViewById(R.id.nashed6);
        nashed7 = findViewById(R.id.nashed7); nashed8 = findViewById(R.id.nashed8); nashed9 = findViewById(R.id.nashed9);
        nashed10 = findViewById(R.id.nashed10); nashed11 = findViewById(R.id.nashed11); nashed12 = findViewById(R.id.nashed12);
        nashed13 = findViewById(R.id.nashed13); nashed14 = findViewById(R.id.nashed14); nashed15 = findViewById(R.id.nashed15);
        nashed16 = findViewById(R.id.nashed16); nashed17 = findViewById(R.id.nashed17); nashed18 = findViewById(R.id.nashed18);
        fabRevealLayout = findViewById(R.id.fab_reveal_layout); Revel_close = findViewById(R.id.Revel_close);
        cycleMenuWidget = findViewById(R.id.itemCycleMenuWidget); cycleMenuWidget.setMenuRes(R.menu.main);
        mAdView1 = findViewById(R.id.adView1); mAdView2 = findViewById(R.id.adView2); mAdView3 = findViewById(R.id.adView3);
        forecastView = findViewById(R.id.forecast_view); fabGreen2 = findViewById(R.id.fabGreen2);
        fabGreen3 = findViewById(R.id.fabGreen3);
    }

    private void showMenu(View v) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.meddil_arab:
                        forecastView.setForecast(forecasts.get(0));
                        cityPicker.smoothScrollToPosition(0);
                        break;
                    case R.id.east_arab:
                        forecastView.setForecast(forecasts.get(6));
                        cityPicker.smoothScrollToPosition(6);
                        break;
                    case R.id.west_arab:
                        forecastView.setForecast(forecasts.get(11));
                        cityPicker.smoothScrollToPosition(11);
                        break;
                    case R.id.algazera_arab:
                        forecastView.setForecast(forecasts.get(16));
                        cityPicker.smoothScrollToPosition(16);
                        break;
                    case R.id.east_africa:
                        forecastView.setForecast(forecasts.get(23));
                        cityPicker.smoothScrollToPosition(23);
                        break;
                    case R.id.middel_africa:
                        forecastView.setForecast(forecasts.get(36));
                        cityPicker.smoothScrollToPosition(36);
                        break;
                    case R.id.south_africa:
                        forecastView.setForecast(forecasts.get(45));
                        cityPicker.smoothScrollToPosition(45);
                        break;
                    case R.id.west_africa:
                        forecastView.setForecast(forecasts.get(50));
                        cityPicker.smoothScrollToPosition(50);
                        break;
                    case R.id.middel_asia:
                        forecastView.setForecast(forecasts.get(64));
                        cityPicker.smoothScrollToPosition(64);
                        break;
                    case R.id.east_asia:
                        forecastView.setForecast(forecasts.get(70));
                        cityPicker.smoothScrollToPosition(70);
                        break;
                    case R.id.southeast_asia:
                        forecastView.setForecast(forecasts.get(75));
                        cityPicker.smoothScrollToPosition(75);
                        break;
                    case R.id.south_asia:
                        forecastView.setForecast(forecasts.get(84));
                        cityPicker.smoothScrollToPosition(84);
                        break;
                    case R.id.west_asia:
                        forecastView.setForecast(forecasts.get(90));
                        cityPicker.smoothScrollToPosition(90);
                        break;
                    case R.id.north_europe:
                        forecastView.setForecast(forecasts.get(95));
                        cityPicker.smoothScrollToPosition(95);
                        break;
                    case R.id.south_europe:
                        forecastView.setForecast(forecasts.get(105));
                        cityPicker.smoothScrollToPosition(105);
                        break;
                    case R.id.west_europe:
                        forecastView.setForecast(forecasts.get(115));
                        cityPicker.smoothScrollToPosition(115);
                        break;
                    case R.id.east_europe:
                        forecastView.setForecast(forecasts.get(121));
                        cityPicker.smoothScrollToPosition(121);
                        break;
                    case R.id.north_amirca:
                        forecastView.setForecast(forecasts.get(130));
                        cityPicker.smoothScrollToPosition(130);
                        break;
                    case R.id.north_amirca_gzr:
                        forecastView.setForecast(forecasts.get(133));
                        cityPicker.smoothScrollToPosition(133);
                        break;
                    case R.id.meddil_amrica:
                        forecastView.setForecast(forecasts.get(146));
                        cityPicker.smoothScrollToPosition(146);
                        break;
                    case R.id.south_amrica:
                        forecastView.setForecast(forecasts.get(153));
                        cityPicker.smoothScrollToPosition(153);
                        break;
                    case R.id.australia:
                        forecastView.setForecast(forecasts.get(163));
                        cityPicker.smoothScrollToPosition(163);
                        break;
                }
                return false;
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_more, popup.getMenu());
        AndroidUtils.insertMenuItemIcons(v.getContext(), popup);
        popup.show();
    }





    @Override
    public void onCurrentItemChanged(@Nullable ForecastAdapter.ViewHolder holder, int position) {
        //viewHolder will never be null, because we never remove items from adapter's list
  if (holder != null) {
          forecastView.setForecast(forecasts.get(position));
          holder.showText();
     switch (position){
         case 0:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.egypt);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Egypt_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Egypt");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Egypt");
             }
             nashed.setText(R.string.nashed_Egypt); nashed1.setText(R.string.nashed_Egypt_1);
             nashed2.setText(R.string.nashed_Egypt_2); nashed3.setText(R.string.nashed_Egypt_3);
             nashed4.setText(R.string.nashed_Egypt_4); nashed5.setText(R.string.nashed_Egypt_5);
             nashed6.setText(R.string.nashed_Egypt_6); nashed7.setText(R.string.nashed_Egypt_7);
             nashed8.setText(R.string.nashed_Egypt_8); nashed9.setText(R.string.nashed_Egypt_9);
             nashed10.setText(R.string.nashed_Egypt_10); nashed11.setText(R.string.nashed_Egypt_11);
             nashed12.setText(R.string.nashed_Egypt_12); nashed13.setText(R.string.nashed_Egypt_13);
             nashed14.setText(R.string.nashed_Egypt_14); nashed15.setText(R.string.nashed_Egypt_15);
             nashed16.setText(R.string.nashed_Egypt_16); nashed17.setText(R.string.nashed_Egypt_17);
             nashed18.setText(R.string.nashed_Egypt_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
          break;
         case 1:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.sudan);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Sudan_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Sudan");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Sudan");
             }
             nashed.setText(R.string.nashed_Sudan); nashed1.setText(R.string.nashed_Sudan_1);
             nashed2.setText(R.string.nashed_Sudan_2); nashed3.setText(R.string.nashed_Sudan_3);
             nashed4.setText(R.string.nashed_Sudan_4); nashed5.setText(R.string.nashed_Sudan_5);
             nashed6.setText(R.string.nashed_Sudan_6); nashed7.setText(R.string.nashed_Sudan_7);
             nashed8.setText(R.string.nashed_Sudan_8); nashed9.setText(R.string.nashed_Sudan_9);
             nashed10.setText(R.string.nashed_Sudan_10); nashed11.setText(R.string.nashed_Sudan_11);
             nashed12.setText(R.string.nashed_Sudan_12); nashed13.setText(R.string.nashed_Sudan_13);
             nashed14.setText(R.string.nashed_Sudan_14); nashed15.setText(R.string.nashed_Sudan_15);
             nashed16.setText(R.string.nashed_Sudan_16); nashed17.setText(R.string.nashed_Sudan_17);
             nashed18.setText(R.string.nashed_Sudan_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 2:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.djibouti);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Djibouti_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Djibouti");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Djibouti");
             }
             nashed.setText(R.string.nashed_Djibouti); nashed1.setText(R.string.nashed_Djibouti_1);
             nashed2.setText(R.string.nashed_Djibouti_2); nashed3.setText(R.string.nashed_Djibouti_3);
             nashed4.setText(R.string.nashed_Djibouti_4); nashed5.setText(R.string.nashed_Djibouti_5);
             nashed6.setText(R.string.nashed_Djibouti_6); nashed7.setText(R.string.nashed_Djibouti_7);
             nashed8.setText(R.string.nashed_Djibouti_8); nashed9.setText(R.string.nashed_Djibouti_9);
             nashed10.setText(R.string.nashed_Djibouti_10); nashed11.setText(R.string.nashed_Djibouti_11);
             nashed12.setText(R.string.nashed_Djibouti_12); nashed13.setText(R.string.nashed_Djibouti_13);
             nashed14.setText(R.string.nashed_Djibouti_14); nashed15.setText(R.string.nashed_Djibouti_15);
             nashed16.setText(R.string.nashed_Djibouti_16); nashed17.setText(R.string.nashed_Djibouti_17);
             nashed18.setText(R.string.nashed_Djibouti_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 3:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.comoros);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Comoros_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Comoros");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Comoros");
             }
             nashed.setText(R.string.nashed_Comoros); nashed1.setText(R.string.nashed_Comoros_1);
             nashed2.setText(R.string.nashed_Comoros_2); nashed3.setText(R.string.nashed_Comoros_3);
             nashed4.setText(R.string.nashed_Comoros_4); nashed5.setText(R.string.nashed_Comoros_5);
             nashed6.setText(R.string.nashed_Comoros_6); nashed7.setText(R.string.nashed_Comoros_7);
             nashed8.setText(R.string.nashed_Comoros_8); nashed9.setText(R.string.nashed_Comoros_9);
             nashed10.setText(R.string.nashed_Comoros_10); nashed11.setText(R.string.nashed_Comoros_11);
             nashed12.setText(R.string.nashed_Comoros_12); nashed13.setText(R.string.nashed_Comoros_13);
             nashed14.setText(R.string.nashed_Comoros_14); nashed15.setText(R.string.nashed_Comoros_15);
             nashed16.setText(R.string.nashed_Comoros_16); nashed17.setText(R.string.nashed_Comoros_17);
             nashed18.setText(R.string.nashed_Comoros_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 4:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.somalia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Somali_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Somalia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Somalia");
             }
             nashed.setText(R.string.nashed_Somali); nashed1.setText(R.string.nashed_Somali_1);
             nashed2.setText(R.string.nashed_Somali_2); nashed3.setText(R.string.nashed_Somali_3);
             nashed4.setText(R.string.nashed_Somali_4); nashed5.setText(R.string.nashed_Somali_5);
             nashed6.setText(R.string.nashed_Somali_6); nashed7.setText(R.string.nashed_Somali_7);
             nashed8.setText(R.string.nashed_Somali_8); nashed9.setText(R.string.nashed_Somali_9);
             nashed10.setText(R.string.nashed_Somali_10); nashed11.setText(R.string.nashed_Somali_11);
             nashed12.setText(R.string.nashed_Somali_12); nashed13.setText(R.string.nashed_Somali_13);
             nashed14.setText(R.string.nashed_Somali_14); nashed15.setText(R.string.nashed_Somali_15);
             nashed16.setText(R.string.nashed_Somali_16); nashed17.setText(R.string.nashed_Somali_17);
             nashed18.setText(R.string.nashed_Somali_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 5:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.eritrea);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Eritrea_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Eritrea");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Eritrea");
             }
             nashed.setText(R.string.nashed_Eritrea); nashed1.setText(R.string.nashed_Eritrea_1);
             nashed2.setText(R.string.nashed_Eritrea_2); nashed3.setText(R.string.nashed_Eritrea_3);
             nashed4.setText(R.string.nashed_Eritrea_4); nashed5.setText(R.string.nashed_Eritrea_5);
             nashed6.setText(R.string.nashed_Eritrea_6); nashed7.setText(R.string.nashed_Eritrea_7);
             nashed8.setText(R.string.nashed_Eritrea_8); nashed9.setText(R.string.nashed_Eritrea_9);
             nashed10.setText(R.string.nashed_Eritrea_10); nashed11.setText(R.string.nashed_Eritrea_11);
             nashed12.setText(R.string.nashed_Eritrea_12); nashed13.setText(R.string.nashed_Eritrea_13);
             nashed14.setText(R.string.nashed_Eritrea_14); nashed15.setText(R.string.nashed_Eritrea_15);
             nashed16.setText(R.string.nashed_Eritrea_16); nashed17.setText(R.string.nashed_Eritrea_17);
             nashed18.setText(R.string.nashed_Eritrea_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 6:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.palestine);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Palestine_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Palestine");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Palestine");
             }
             nashed.setText(R.string.nashed_Palestine); nashed1.setText(R.string.nashed_Palestine_1);
             nashed2.setText(R.string.nashed_Palestine_2); nashed3.setText(R.string.nashed_Palestine_3);
             nashed4.setText(R.string.nashed_Palestine_4); nashed5.setText(R.string.nashed_Palestine_5);
             nashed6.setText(R.string.nashed_Palestine_6); nashed7.setText(R.string.nashed_Palestine_7);
             nashed8.setText(R.string.nashed_Palestine_8); nashed9.setText(R.string.nashed_Palestine_9);
             nashed10.setText(R.string.nashed_Palestine_10); nashed11.setText(R.string.nashed_Palestine_11);
             nashed12.setText(R.string.nashed_Palestine_12); nashed13.setText(R.string.nashed_Palestine_13);
             nashed14.setText(R.string.nashed_Palestine_14); nashed15.setText(R.string.nashed_Palestine_15);
             nashed16.setText(R.string.nashed_Palestine_16); nashed17.setText(R.string.nashed_Palestine_17);
             nashed18.setText(R.string.nashed_Palestine_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#C51162"))
                     .show();
             break;
         case 7:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.lebanon);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Lebanon_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Lebanon");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Lebanon");
             }
             nashed.setText(R.string.nashed_Lebanon); nashed1.setText(R.string.nashed_Lebanon_1);
             nashed2.setText(R.string.nashed_Lebanon_2); nashed3.setText(R.string.nashed_Lebanon_3);
             nashed4.setText(R.string.nashed_Lebanon_4); nashed5.setText(R.string.nashed_Lebanon_5);
             nashed6.setText(R.string.nashed_Lebanon_6); nashed7.setText(R.string.nashed_Lebanon_7);
             nashed8.setText(R.string.nashed_Lebanon_8); nashed9.setText(R.string.nashed_Lebanon_9);
             nashed10.setText(R.string.nashed_Lebanon_10); nashed11.setText(R.string.nashed_Lebanon_11);
             nashed12.setText(R.string.nashed_Lebanon_12); nashed13.setText(R.string.nashed_Lebanon_13);
             nashed14.setText(R.string.nashed_Lebanon_14); nashed15.setText(R.string.nashed_Lebanon_15);
             nashed16.setText(R.string.nashed_Lebanon_16); nashed17.setText(R.string.nashed_Lebanon_17);
             nashed18.setText(R.string.nashed_Lebanon_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#C51162"))
                     .show();
             break;
         case 8:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.jordan);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Jordon_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Jordan");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Jordan");
             }
             nashed.setText(R.string.nashed_Jordon); nashed1.setText(R.string.nashed_Jordon_1);
             nashed2.setText(R.string.nashed_Jordon_2); nashed3.setText(R.string.nashed_Jordon_3);
             nashed4.setText(R.string.nashed_Jordon_4); nashed5.setText(R.string.nashed_Jordon_5);
             nashed6.setText(R.string.nashed_Jordon_6); nashed7.setText(R.string.nashed_Jordon_7);
             nashed8.setText(R.string.nashed_Jordon_8); nashed9.setText(R.string.nashed_Jordon_9);
             nashed10.setText(R.string.nashed_Jordon_10); nashed11.setText(R.string.nashed_Jordon_11);
             nashed12.setText(R.string.nashed_Jordon_12); nashed13.setText(R.string.nashed_Jordon_13);
             nashed14.setText(R.string.nashed_Jordon_14); nashed15.setText(R.string.nashed_Jordon_15);
             nashed16.setText(R.string.nashed_Jordon_16); nashed17.setText(R.string.nashed_Jordon_17);
             nashed18.setText(R.string.nashed_Jordon_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#C51162"))
                     .show();
             break;
         case 9:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.iraq);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Iraq_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Iraq");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Iraq");
             }
             nashed.setText(R.string.nashed_Iraq); nashed1.setText(R.string.nashed_Iraq_1);
             nashed2.setText(R.string.nashed_Iraq_2); nashed3.setText(R.string.nashed_Iraq_3);
             nashed4.setText(R.string.nashed_Iraq_4); nashed5.setText(R.string.nashed_Iraq_5);
             nashed6.setText(R.string.nashed_Iraq_6); nashed7.setText(R.string.nashed_Iraq_7);
             nashed8.setText(R.string.nashed_Iraq_8); nashed9.setText(R.string.nashed_Iraq_9);
             nashed10.setText(R.string.nashed_Iraq_10); nashed11.setText(R.string.nashed_Iraq_11);
             nashed12.setText(R.string.nashed_Iraq_12); nashed13.setText(R.string.nashed_Iraq_13);
             nashed14.setText(R.string.nashed_Iraq_14); nashed15.setText(R.string.nashed_Iraq_15);
             nashed16.setText(R.string.nashed_Iraq_16); nashed17.setText(R.string.nashed_Iraq_17);
             nashed18.setText(R.string.nashed_Iraq_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#C51162"))
                     .show();
             break;
         case 10:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.syria);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Syria_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Syria");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Syria");
             }
             nashed.setText(R.string.nashed_Syria); nashed1.setText(R.string.nashed_Syria_1);
             nashed2.setText(R.string.nashed_Syria_2); nashed3.setText(R.string.nashed_Syria_3);
             nashed4.setText(R.string.nashed_Syria_4); nashed5.setText(R.string.nashed_Syria_5);
             nashed6.setText(R.string.nashed_Syria_6); nashed7.setText(R.string.nashed_Syria_7);
             nashed8.setText(R.string.nashed_Syria_8); nashed9.setText(R.string.nashed_Syria_9);
             nashed10.setText(R.string.nashed_Syria_10); nashed11.setText(R.string.nashed_Syria_11);
             nashed12.setText(R.string.nashed_Syria_12); nashed13.setText(R.string.nashed_Syria_13);
             nashed14.setText(R.string.nashed_Syria_14); nashed15.setText(R.string.nashed_Syria_15);
             nashed16.setText(R.string.nashed_Syria_16); nashed17.setText(R.string.nashed_Syria_17);
             nashed18.setText(R.string.nashed_Syria_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#C51162"))
                     .show();
             break;
         case 11:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.mauritania);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Moritania_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Mauritania");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Mauritania");
             }
             nashed.setText(R.string.nashed_Moritania); nashed1.setText(R.string.nashed_Moritania_1);
             nashed2.setText(R.string.nashed_Moritania_2); nashed3.setText(R.string.nashed_Moritania_3);
             nashed4.setText(R.string.nashed_Moritania_4); nashed5.setText(R.string.nashed_Moritania_5);
             nashed6.setText(R.string.nashed_Moritania_6); nashed7.setText(R.string.nashed_Moritania_7);
             nashed8.setText(R.string.nashed_Moritania_8); nashed9.setText(R.string.nashed_Moritania_9);
             nashed10.setText(R.string.nashed_Moritania_10); nashed11.setText(R.string.nashed_Moritania_11);
             nashed12.setText(R.string.nashed_Moritania_12); nashed13.setText(R.string.nashed_Moritania_13);
             nashed14.setText(R.string.nashed_Moritania_14); nashed15.setText(R.string.nashed_Moritania_15);
             nashed16.setText(R.string.nashed_Moritania_16); nashed17.setText(R.string.nashed_Moritania_17);
             nashed18.setText(R.string.nashed_Moritania_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#AA00FF"))
                     .show();
             break;
         case 12:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.algeria);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Algeria_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Algeria");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Algeria");
             }
             nashed.setText(R.string.nashed_Algeria); nashed1.setText(R.string.nashed_Algeria_1);
             nashed2.setText(R.string.nashed_Algeria_2); nashed3.setText(R.string.nashed_Algeria_3);
             nashed4.setText(R.string.nashed_Algeria_4); nashed5.setText(R.string.nashed_Algeria_5);
             nashed6.setText(R.string.nashed_Algeria_6); nashed7.setText(R.string.nashed_Algeria_7);
             nashed8.setText(R.string.nashed_Algeria_8); nashed9.setText(R.string.nashed_Algeria_9);
             nashed10.setText(R.string.nashed_Algeria_10); nashed11.setText(R.string.nashed_Algeria_11);
             nashed12.setText(R.string.nashed_Algeria_12); nashed13.setText(R.string.nashed_Algeria_13);
             nashed14.setText(R.string.nashed_Algeria_14); nashed15.setText(R.string.nashed_Algeria_15);
             nashed16.setText(R.string.nashed_Algeria_16); nashed17.setText(R.string.nashed_Algeria_17);
             nashed18.setText(R.string.nashed_Algeria_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#AA00FF"))
                     .show();
             break;
         case 13:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.libya);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Lybia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Libya");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Libya");
             }
             nashed.setText(R.string.nashed_Lybia); nashed1.setText(R.string.nashed_Lybia_1);
             nashed2.setText(R.string.nashed_Lybia_2); nashed3.setText(R.string.nashed_Lybia_3);
             nashed4.setText(R.string.nashed_Lybia_4); nashed5.setText(R.string.nashed_Lybia_5);
             nashed6.setText(R.string.nashed_Lybia_6); nashed7.setText(R.string.nashed_Lybia_7);
             nashed8.setText(R.string.nashed_Lybia_8); nashed9.setText(R.string.nashed_Lybia_9);
             nashed10.setText(R.string.nashed_Lybia_10); nashed11.setText(R.string.nashed_Lybia_11);
             nashed12.setText(R.string.nashed_Lybia_12); nashed13.setText(R.string.nashed_Lybia_13);
             nashed14.setText(R.string.nashed_Lybia_14); nashed15.setText(R.string.nashed_Lybia_15);
             nashed16.setText(R.string.nashed_Lybia_16); nashed17.setText(R.string.nashed_Lybia_17);
             nashed18.setText(R.string.nashed_Lybia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#AA00FF"))
                     .show();
             break;
         case 14:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.tunis);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Tunis_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Tunis");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Tunis");
             }
             nashed.setText(R.string.nashed_Tunis); nashed1.setText(R.string.nashed_Tunis_1);
             nashed2.setText(R.string.nashed_Tunis_2); nashed3.setText(R.string.nashed_Tunis_3);
             nashed4.setText(R.string.nashed_Tunis_4); nashed5.setText(R.string.nashed_Tunis_5);
             nashed6.setText(R.string.nashed_Tunis_6); nashed7.setText(R.string.nashed_Tunis_7);
             nashed8.setText(R.string.nashed_Tunis_8); nashed9.setText(R.string.nashed_Tunis_9);
             nashed10.setText(R.string.nashed_Tunis_10); nashed11.setText(R.string.nashed_Tunis_11);
             nashed12.setText(R.string.nashed_Tunis_12); nashed13.setText(R.string.nashed_Tunis_13);
             nashed14.setText(R.string.nashed_Tunis_14); nashed15.setText(R.string.nashed_Tunis_15);
             nashed16.setText(R.string.nashed_Tunis_16); nashed17.setText(R.string.nashed_Tunis_17);
             nashed18.setText(R.string.nashed_Tunis_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#AA00FF"))
                     .show();
             break;
         case 15:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.morocco);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Morocco_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Morocco");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Morocco");
             }
             nashed.setText(R.string.nashed_Morocco); nashed1.setText(R.string.nashed_Morocco_1);
             nashed2.setText(R.string.nashed_Morocco_2); nashed3.setText(R.string.nashed_Morocco_3);
             nashed4.setText(R.string.nashed_Morocco_4); nashed5.setText(R.string.nashed_Morocco_5);
             nashed6.setText(R.string.nashed_Morocco_6); nashed7.setText(R.string.nashed_Morocco_7);
             nashed8.setText(R.string.nashed_Morocco_8); nashed9.setText(R.string.nashed_Morocco_9);
             nashed10.setText(R.string.nashed_Morocco_10); nashed11.setText(R.string.nashed_Morocco_11);
             nashed12.setText(R.string.nashed_Morocco_12); nashed13.setText(R.string.nashed_Morocco_13);
             nashed14.setText(R.string.nashed_Morocco_14); nashed15.setText(R.string.nashed_Morocco_15);
             nashed16.setText(R.string.nashed_Morocco_16); nashed17.setText(R.string.nashed_Morocco_17);
             nashed18.setText(R.string.nashed_Morocco_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#AA00FF"))
                     .show();
             break;
         case 16:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.bahrain);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Bahrain_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Bahrain");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Bahrain");
             }
             nashed.setText(R.string.nashed_Bahrain); nashed1.setText(R.string.nashed_Bahrain_1);
             nashed2.setText(R.string.nashed_Bahrain_2); nashed3.setText(R.string.nashed_Bahrain_3);
             nashed4.setText(R.string.nashed_Bahrain_4); nashed5.setText(R.string.nashed_Bahrain_5);
             nashed6.setText(R.string.nashed_Bahrain_6); nashed7.setText(R.string.nashed_Bahrain_7);
             nashed8.setText(R.string.nashed_Bahrain_8); nashed9.setText(R.string.nashed_Bahrain_9);
             nashed10.setText(R.string.nashed_Bahrain_10); nashed11.setText(R.string.nashed_Bahrain_11);
             nashed12.setText(R.string.nashed_Bahrain_12); nashed13.setText(R.string.nashed_Bahrain_13);
             nashed14.setText(R.string.nashed_Bahrain_14); nashed15.setText(R.string.nashed_Bahrain_15);
             nashed16.setText(R.string.nashed_Bahrain_16); nashed17.setText(R.string.nashed_Bahrain_17);
             nashed18.setText(R.string.nashed_Bahrain_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.algazera_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#6200EA"))
                     .show();
             break;
         case 17:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.kuwait);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Kuwait_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Kuwait");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Kuwait");
             }
             nashed.setText(R.string.nashed_Kuwait); nashed1.setText(R.string.nashed_Kuwait_1);
             nashed2.setText(R.string.nashed_Kuwait_2); nashed3.setText(R.string.nashed_Kuwait_3);
             nashed4.setText(R.string.nashed_Kuwait_4); nashed5.setText(R.string.nashed_Kuwait_5);
             nashed6.setText(R.string.nashed_Kuwait_6); nashed7.setText(R.string.nashed_Kuwait_7);
             nashed8.setText(R.string.nashed_Kuwait_8); nashed9.setText(R.string.nashed_Kuwait_9);
             nashed10.setText(R.string.nashed_Kuwait_10); nashed11.setText(R.string.nashed_Kuwait_11);
             nashed12.setText(R.string.nashed_Kuwait_12); nashed13.setText(R.string.nashed_Kuwait_13);
             nashed14.setText(R.string.nashed_Kuwait_14); nashed15.setText(R.string.nashed_Kuwait_15);
             nashed16.setText(R.string.nashed_Kuwait_16); nashed17.setText(R.string.nashed_Kuwait_17);
             nashed18.setText(R.string.nashed_Kuwait_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.algazera_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#6200EA"))
                     .show();
             break;
         case 18:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.oman);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Oman_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Oman");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Oman");
             }
             nashed.setText(R.string.nashed_Oman); nashed1.setText(R.string.nashed_Oman_1);
             nashed2.setText(R.string.nashed_Oman_2); nashed3.setText(R.string.nashed_Oman_3);
             nashed4.setText(R.string.nashed_Oman_4); nashed5.setText(R.string.nashed_Oman_5);
             nashed6.setText(R.string.nashed_Oman_6); nashed7.setText(R.string.nashed_Oman_7);
             nashed8.setText(R.string.nashed_Oman_8); nashed9.setText(R.string.nashed_Oman_9);
             nashed10.setText(R.string.nashed_Oman_10); nashed11.setText(R.string.nashed_Oman_11);
             nashed12.setText(R.string.nashed_Oman_12); nashed13.setText(R.string.nashed_Oman_13);
             nashed14.setText(R.string.nashed_Oman_14); nashed15.setText(R.string.nashed_Oman_15);
             nashed16.setText(R.string.nashed_Oman_16); nashed17.setText(R.string.nashed_Oman_17);
             nashed18.setText(R.string.nashed_Oman_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.algazera_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#6200EA"))
                     .show();
             break;
         case 19:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.qatar);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Qatar_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Qatar");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Qatar");
             }
             nashed.setText(R.string.nashed_Qatar); nashed1.setText(R.string.nashed_Qatar_1);
             nashed2.setText(R.string.nashed_Qatar_2); nashed3.setText(R.string.nashed_Qatar_3);
             nashed4.setText(R.string.nashed_Qatar_4); nashed5.setText(R.string.nashed_Qatar_5);
             nashed6.setText(R.string.nashed_Qatar_6); nashed7.setText(R.string.nashed_Qatar_7);
             nashed8.setText(R.string.nashed_Qatar_8); nashed9.setText(R.string.nashed_Qatar_9);
             nashed10.setText(R.string.nashed_Qatar_10); nashed11.setText(R.string.nashed_Qatar_11);
             nashed12.setText(R.string.nashed_Qatar_12); nashed13.setText(R.string.nashed_Qatar_13);
             nashed14.setText(R.string.nashed_Qatar_14); nashed15.setText(R.string.nashed_Qatar_15);
             nashed16.setText(R.string.nashed_Qatar_16); nashed17.setText(R.string.nashed_Qatar_17);
             nashed18.setText(R.string.nashed_Qatar_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.algazera_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#6200EA"))
                     .show();
             break;
         case 20:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.saudi_arabia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Saudi_Arabia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Saudi_Arabia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Saudi_Arabia");
             }
             nashed.setText(R.string.nashed_Saudi_Arabia); nashed1.setText(R.string.nashed_Saudi_Arabia_1);
             nashed2.setText(R.string.nashed_Saudi_Arabia_2); nashed3.setText(R.string.nashed_Saudi_Arabia_3);
             nashed4.setText(R.string.nashed_Saudi_Arabia_4); nashed5.setText(R.string.nashed_Saudi_Arabia_5);
             nashed6.setText(R.string.nashed_Saudi_Arabia_6); nashed7.setText(R.string.nashed_Saudi_Arabia_7);
             nashed8.setText(R.string.nashed_Saudi_Arabia_8); nashed9.setText(R.string.nashed_Saudi_Arabia_9);
             nashed10.setText(R.string.nashed_Saudi_Arabia_10); nashed11.setText(R.string.nashed_Saudi_Arabia_11);
             nashed12.setText(R.string.nashed_Saudi_Arabia_12); nashed13.setText(R.string.nashed_Saudi_Arabia_13);
             nashed14.setText(R.string.nashed_Saudi_Arabia_14); nashed15.setText(R.string.nashed_Saudi_Arabia_15);
             nashed16.setText(R.string.nashed_Saudi_Arabia_16); nashed17.setText(R.string.nashed_Saudi_Arabia_17);
             nashed18.setText(R.string.nashed_Saudi_Arabia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.algazera_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#6200EA"))
                     .show();
             break;
         case 21:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.uae);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Uea_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/United_Arab_Emirates");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/United_Arab_Emirates");
             }
             nashed.setText(R.string.nashed_Uea); nashed1.setText(R.string.nashed_Uea_1);
             nashed2.setText(R.string.nashed_Uea_2); nashed3.setText(R.string.nashed_Uea_3);
             nashed4.setText(R.string.nashed_Uea_4); nashed5.setText(R.string.nashed_Uea_5);
             nashed6.setText(R.string.nashed_Uea_6); nashed7.setText(R.string.nashed_Uea_7);
             nashed8.setText(R.string.nashed_Uea_8); nashed9.setText(R.string.nashed_Uea_9);
             nashed10.setText(R.string.nashed_Uea_10); nashed11.setText(R.string.nashed_Uea_11);
             nashed12.setText(R.string.nashed_Uea_12); nashed13.setText(R.string.nashed_Uea_13);
             nashed14.setText(R.string.nashed_Uea_14); nashed15.setText(R.string.nashed_Uea_15);
             nashed16.setText(R.string.nashed_Uea_16); nashed17.setText(R.string.nashed_Uea_17);
             nashed18.setText(R.string.nashed_Uea_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.algazera_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#6200EA"))
                     .show();
             break;
         case 22:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.yemen);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Yemen_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Yemen");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Yemen");
             }
             nashed.setText(R.string.nashed_Yemen); nashed1.setText(R.string.nashed_Yemen_1);
             nashed2.setText(R.string.nashed_Yemen_2); nashed3.setText(R.string.nashed_Yemen_3);
             nashed4.setText(R.string.nashed_Yemen_4); nashed5.setText(R.string.nashed_Yemen_5);
             nashed6.setText(R.string.nashed_Yemen_6); nashed7.setText(R.string.nashed_Yemen_7);
             nashed8.setText(R.string.nashed_Yemen_8); nashed9.setText(R.string.nashed_Yemen_9);
             nashed10.setText(R.string.nashed_Yemen_10); nashed11.setText(R.string.nashed_Yemen_11);
             nashed12.setText(R.string.nashed_Yemen_12); nashed13.setText(R.string.nashed_Yemen_13);
             nashed14.setText(R.string.nashed_Yemen_14); nashed15.setText(R.string.nashed_Yemen_15);
             nashed16.setText(R.string.nashed_Yemen_16); nashed17.setText(R.string.nashed_Yemen_17);
             nashed18.setText(R.string.nashed_Yemen_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.algazera_arab))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#6200EA"))
                     .show();
             break;
         case 23:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.burundi);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Burundi_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Burundi");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Burundi");
             }
             nashed.setText(R.string.nashed_Burundi); nashed1.setText(R.string.nashed_Burundi_1);
             nashed2.setText(R.string.nashed_Burundi_2); nashed3.setText(R.string.nashed_Burundi_3);
             nashed4.setText(R.string.nashed_Burundi_4); nashed5.setText(R.string.nashed_Burundi_5);
             nashed6.setText(R.string.nashed_Burundi_6); nashed7.setText(R.string.nashed_Burundi_7);
             nashed8.setText(R.string.nashed_Burundi_8); nashed9.setText(R.string.nashed_Burundi_9);
             nashed10.setText(R.string.nashed_Burundi_10); nashed11.setText(R.string.nashed_Burundi_11);
             nashed12.setText(R.string.nashed_Burundi_12); nashed13.setText(R.string.nashed_Burundi_13);
             nashed14.setText(R.string.nashed_Burundi_14); nashed15.setText(R.string.nashed_Burundi_15);
             nashed16.setText(R.string.nashed_Burundi_16); nashed17.setText(R.string.nashed_Burundi_17);
             nashed18.setText(R.string.nashed_Burundi_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 24:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.ethiopia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Ethiopia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Ethiopia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Ethiopia");
             }
             nashed.setText(R.string.nashed_Ethiopia); nashed1.setText(R.string.nashed_Ethiopia_1);
             nashed2.setText(R.string.nashed_Ethiopia_2); nashed3.setText(R.string.nashed_Ethiopia_3);
             nashed4.setText(R.string.nashed_Ethiopia_4); nashed5.setText(R.string.nashed_Ethiopia_5);
             nashed6.setText(R.string.nashed_Ethiopia_6); nashed7.setText(R.string.nashed_Ethiopia_7);
             nashed8.setText(R.string.nashed_Ethiopia_8); nashed9.setText(R.string.nashed_Ethiopia_9);
             nashed10.setText(R.string.nashed_Ethiopia_10); nashed11.setText(R.string.nashed_Ethiopia_11);
             nashed12.setText(R.string.nashed_Ethiopia_12); nashed13.setText(R.string.nashed_Ethiopia_13);
             nashed14.setText(R.string.nashed_Ethiopia_14); nashed15.setText(R.string.nashed_Ethiopia_15);
             nashed16.setText(R.string.nashed_Ethiopia_16); nashed17.setText(R.string.nashed_Ethiopia_17);
             nashed18.setText(R.string.nashed_Ethiopia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 25:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.kenya);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Kenya_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Kenya");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Kenya");
             }
             nashed.setText(R.string.nashed_Kenya); nashed1.setText(R.string.nashed_Kenya_1);
             nashed2.setText(R.string.nashed_Kenya_2); nashed3.setText(R.string.nashed_Kenya_3);
             nashed4.setText(R.string.nashed_Kenya_4); nashed5.setText(R.string.nashed_Kenya_5);
             nashed6.setText(R.string.nashed_Kenya_6); nashed7.setText(R.string.nashed_Kenya_7);
             nashed8.setText(R.string.nashed_Kenya_8); nashed9.setText(R.string.nashed_Kenya_9);
             nashed10.setText(R.string.nashed_Kenya_10); nashed11.setText(R.string.nashed_Kenya_11);
             nashed12.setText(R.string.nashed_Kenya_12); nashed13.setText(R.string.nashed_Kenya_13);
             nashed14.setText(R.string.nashed_Kenya_14); nashed15.setText(R.string.nashed_Kenya_15);
             nashed16.setText(R.string.nashed_Kenya_16); nashed17.setText(R.string.nashed_Kenya_17);
             nashed18.setText(R.string.nashed_Kenya_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 26:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.madagascar);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Madagascar_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Madagascar");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Madagascar");
             }
             nashed.setText(R.string.nashed_Madagascar); nashed1.setText(R.string.nashed_Madagascar_1);
             nashed2.setText(R.string.nashed_Madagascar_2); nashed3.setText(R.string.nashed_Madagascar_3);
             nashed4.setText(R.string.nashed_Madagascar_4); nashed5.setText(R.string.nashed_Madagascar_5);
             nashed6.setText(R.string.nashed_Madagascar_6); nashed7.setText(R.string.nashed_Madagascar_7);
             nashed8.setText(R.string.nashed_Madagascar_8); nashed9.setText(R.string.nashed_Madagascar_9);
             nashed10.setText(R.string.nashed_Madagascar_10); nashed11.setText(R.string.nashed_Madagascar_11);
             nashed12.setText(R.string.nashed_Madagascar_12); nashed13.setText(R.string.nashed_Madagascar_13);
             nashed14.setText(R.string.nashed_Madagascar_14); nashed15.setText(R.string.nashed_Madagascar_15);
             nashed16.setText(R.string.nashed_Madagascar_16); nashed17.setText(R.string.nashed_Madagascar_17);
             nashed18.setText(R.string.nashed_Madagascar_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 27:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.malawi);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.yellow);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Malawi_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Malawi");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Malawi");
             }
             nashed.setText(R.string.nashed_Malawi); nashed1.setText(R.string.nashed_Malawi_1);
             nashed2.setText(R.string.nashed_Malawi_2); nashed3.setText(R.string.nashed_Malawi_3);
             nashed4.setText(R.string.nashed_Malawi_4); nashed5.setText(R.string.nashed_Malawi_5);
             nashed6.setText(R.string.nashed_Malawi_6); nashed7.setText(R.string.nashed_Malawi_7);
             nashed8.setText(R.string.nashed_Malawi_8); nashed9.setText(R.string.nashed_Malawi_9);
             nashed10.setText(R.string.nashed_Malawi_10); nashed11.setText(R.string.nashed_Malawi_11);
             nashed12.setText(R.string.nashed_Malawi_12); nashed13.setText(R.string.nashed_Malawi_13);
             nashed14.setText(R.string.nashed_Malawi_14); nashed15.setText(R.string.nashed_Malawi_15);
             nashed16.setText(R.string.nashed_Malawi_16); nashed17.setText(R.string.nashed_Malawi_17);
             nashed18.setText(R.string.nashed_Malawi_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 28:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.mauritius);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Mauritius_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Mauritius");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Mauritius");
             }
             nashed.setText(R.string.nashed_Mauritius); nashed1.setText(R.string.nashed_Mauritius_1);
             nashed2.setText(R.string.nashed_Mauritius_2); nashed3.setText(R.string.nashed_Mauritius_3);
             nashed4.setText(R.string.nashed_Mauritius_4); nashed5.setText(R.string.nashed_Mauritius_5);
             nashed6.setText(R.string.nashed_Mauritius_6); nashed7.setText(R.string.nashed_Mauritius_7);
             nashed8.setText(R.string.nashed_Mauritius_8); nashed9.setText(R.string.nashed_Mauritius_9);
             nashed10.setText(R.string.nashed_Mauritius_10); nashed11.setText(R.string.nashed_Mauritius_11);
             nashed12.setText(R.string.nashed_Mauritius_12); nashed13.setText(R.string.nashed_Mauritius_13);
             nashed14.setText(R.string.nashed_Mauritius_14); nashed15.setText(R.string.nashed_Mauritius_15);
             nashed16.setText(R.string.nashed_Mauritius_16); nashed17.setText(R.string.nashed_Mauritius_17);
             nashed18.setText(R.string.nashed_Mauritius_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 29:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.mozambique);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.blue);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.blue);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.blue);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Mozambique_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Mozambique");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Mozambique");
             }
             nashed.setText(R.string.nashed_Mozambique); nashed1.setText(R.string.nashed_Mozambique_1);
             nashed2.setText(R.string.nashed_Mozambique_2); nashed3.setText(R.string.nashed_Mozambique_3);
             nashed4.setText(R.string.nashed_Mozambique_4); nashed5.setText(R.string.nashed_Mozambique_5);
             nashed6.setText(R.string.nashed_Mozambique_6); nashed7.setText(R.string.nashed_Mozambique_7);
             nashed8.setText(R.string.nashed_Mozambique_8); nashed9.setText(R.string.nashed_Mozambique_9);
             nashed10.setText(R.string.nashed_Mozambique_10); nashed11.setText(R.string.nashed_Mozambique_11);
             nashed12.setText(R.string.nashed_Mozambique_12); nashed13.setText(R.string.nashed_Mozambique_13);
             nashed14.setText(R.string.nashed_Mozambique_14); nashed15.setText(R.string.nashed_Mozambique_15);
             nashed16.setText(R.string.nashed_Mozambique_16); nashed17.setText(R.string.nashed_Mozambique_17);
             nashed18.setText(R.string.nashed_Mozambique_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 30:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.rwanda);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Rwanda_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Rwanda");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Rwanda");
             }
             nashed.setText(R.string.nashed_Rwanda); nashed1.setText(R.string.nashed_Rwanda_1);
             nashed2.setText(R.string.nashed_Rwanda_2); nashed3.setText(R.string.nashed_Rwanda_3);
             nashed4.setText(R.string.nashed_Rwanda_4); nashed5.setText(R.string.nashed_Rwanda_5);
             nashed6.setText(R.string.nashed_Rwanda_6); nashed7.setText(R.string.nashed_Rwanda_7);
             nashed8.setText(R.string.nashed_Rwanda_8); nashed9.setText(R.string.nashed_Rwanda_9);
             nashed10.setText(R.string.nashed_Rwanda_10); nashed11.setText(R.string.nashed_Rwanda_11);
             nashed12.setText(R.string.nashed_Rwanda_12); nashed13.setText(R.string.nashed_Rwanda_13);
             nashed14.setText(R.string.nashed_Rwanda_14); nashed15.setText(R.string.nashed_Rwanda_15);
             nashed16.setText(R.string.nashed_Rwanda_16); nashed17.setText(R.string.nashed_Rwanda_17);
             nashed18.setText(R.string.nashed_Rwanda_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 31:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.seychelles);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Seychelles_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Seychelles");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Seychelles");
             }
             nashed.setText(R.string.nashed_Seychelles); nashed1.setText(R.string.nashed_Seychelles_1);
             nashed2.setText(R.string.nashed_Seychelles_2); nashed3.setText(R.string.nashed_Seychelles_3);
             nashed4.setText(R.string.nashed_Seychelles_4); nashed5.setText(R.string.nashed_Seychelles_5);
             nashed6.setText(R.string.nashed_Seychelles_6); nashed7.setText(R.string.nashed_Seychelles_7);
             nashed8.setText(R.string.nashed_Seychelles_8); nashed9.setText(R.string.nashed_Seychelles_9);
             nashed10.setText(R.string.nashed_Seychelles_10); nashed11.setText(R.string.nashed_Seychelles_11);
             nashed12.setText(R.string.nashed_Seychelles_12); nashed13.setText(R.string.nashed_Seychelles_13);
             nashed14.setText(R.string.nashed_Seychelles_14); nashed15.setText(R.string.nashed_Seychelles_15);
             nashed16.setText(R.string.nashed_Seychelles_16); nashed17.setText(R.string.nashed_Seychelles_17);
             nashed18.setText(R.string.nashed_Seychelles_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 32:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.tanzania);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Tanzania_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Tanzania");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Tanzania");
             }
             nashed.setText(R.string.nashed_Tanzania); nashed1.setText(R.string.nashed_Tanzania_1);
             nashed2.setText(R.string.nashed_Tanzania_2); nashed3.setText(R.string.nashed_Tanzania_3);
             nashed4.setText(R.string.nashed_Tanzania_4); nashed5.setText(R.string.nashed_Tanzania_5);
             nashed6.setText(R.string.nashed_Tanzania_6); nashed7.setText(R.string.nashed_Tanzania_7);
             nashed8.setText(R.string.nashed_Tanzania_8); nashed9.setText(R.string.nashed_Tanzania_9);
             nashed10.setText(R.string.nashed_Tanzania_10); nashed11.setText(R.string.nashed_Tanzania_11);
             nashed12.setText(R.string.nashed_Tanzania_12); nashed13.setText(R.string.nashed_Tanzania_13);
             nashed14.setText(R.string.nashed_Tanzania_14); nashed15.setText(R.string.nashed_Tanzania_15);
             nashed16.setText(R.string.nashed_Tanzania_16); nashed17.setText(R.string.nashed_Tanzania_17);
             nashed18.setText(R.string.nashed_Tanzania_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 33:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.uganda);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Uganda_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Uganda");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Uganda");
             }
             nashed.setText(R.string.nashed_Uganda); nashed1.setText(R.string.nashed_Uganda_1);
             nashed2.setText(R.string.nashed_Uganda_2); nashed3.setText(R.string.nashed_Uganda_3);
             nashed4.setText(R.string.nashed_Uganda_4); nashed5.setText(R.string.nashed_Uganda_5);
             nashed6.setText(R.string.nashed_Uganda_6); nashed7.setText(R.string.nashed_Uganda_7);
             nashed8.setText(R.string.nashed_Uganda_8); nashed9.setText(R.string.nashed_Uganda_9);
             nashed10.setText(R.string.nashed_Uganda_10); nashed11.setText(R.string.nashed_Uganda_11);
             nashed12.setText(R.string.nashed_Uganda_12); nashed13.setText(R.string.nashed_Uganda_13);
             nashed14.setText(R.string.nashed_Uganda_14); nashed15.setText(R.string.nashed_Uganda_15);
             nashed16.setText(R.string.nashed_Uganda_16); nashed17.setText(R.string.nashed_Uganda_17);
             nashed18.setText(R.string.nashed_Uganda_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 34:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.zambia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Zambia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Zambia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Zambia");
             }
             nashed.setText(R.string.nashed_Zambia); nashed1.setText(R.string.nashed_Zambia_1);
             nashed2.setText(R.string.nashed_Zambia_2); nashed3.setText(R.string.nashed_Zambia_3);
             nashed4.setText(R.string.nashed_Zambia_4); nashed5.setText(R.string.nashed_Zambia_5);
             nashed6.setText(R.string.nashed_Zambia_6); nashed7.setText(R.string.nashed_Zambia_7);
             nashed8.setText(R.string.nashed_Zambia_8); nashed9.setText(R.string.nashed_Zambia_9);
             nashed10.setText(R.string.nashed_Zambia_10); nashed11.setText(R.string.nashed_Zambia_11);
             nashed12.setText(R.string.nashed_Zambia_12); nashed13.setText(R.string.nashed_Zambia_13);
             nashed14.setText(R.string.nashed_Zambia_14); nashed15.setText(R.string.nashed_Zambia_15);
             nashed16.setText(R.string.nashed_Zambia_16); nashed17.setText(R.string.nashed_Zambia_17);
             nashed18.setText(R.string.nashed_Zambia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 35:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.zimbabwe);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.blue);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.blue);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.blue);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Zimbabwe_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Zimbabwe");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Zimbabwe");
             }
             nashed.setText(R.string.nashed_Zimbabwe); nashed1.setText(R.string.nashed_Zimbabwe_1);
             nashed2.setText(R.string.nashed_Zimbabwe_2); nashed3.setText(R.string.nashed_Zimbabwe_3);
             nashed4.setText(R.string.nashed_Zimbabwe_4); nashed5.setText(R.string.nashed_Zimbabwe_5);
             nashed6.setText(R.string.nashed_Zimbabwe_6); nashed7.setText(R.string.nashed_Zimbabwe_7);
             nashed8.setText(R.string.nashed_Zimbabwe_8); nashed9.setText(R.string.nashed_Zimbabwe_9);
             nashed10.setText(R.string.nashed_Zimbabwe_10); nashed11.setText(R.string.nashed_Zimbabwe_11);
             nashed12.setText(R.string.nashed_Zimbabwe_12); nashed13.setText(R.string.nashed_Zimbabwe_13);
             nashed14.setText(R.string.nashed_Zimbabwe_14); nashed15.setText(R.string.nashed_Zimbabwe_15);
             nashed16.setText(R.string.nashed_Zimbabwe_16); nashed17.setText(R.string.nashed_Zimbabwe_17);
             nashed18.setText(R.string.nashed_Zimbabwe_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#304FFE"))
                     .show();
             break;
         case 36:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.angola);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Angola_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Angola");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Angola");
             }
             nashed.setText(R.string.nashed_Angola); nashed1.setText(R.string.nashed_Angola_1);
             nashed2.setText(R.string.nashed_Angola_2); nashed3.setText(R.string.nashed_Angola_3);
             nashed4.setText(R.string.nashed_Angola_4); nashed5.setText(R.string.nashed_Angola_5);
             nashed6.setText(R.string.nashed_Angola_6); nashed7.setText(R.string.nashed_Angola_7);
             nashed8.setText(R.string.nashed_Angola_8); nashed9.setText(R.string.nashed_Angola_9);
             nashed10.setText(R.string.nashed_Angola_10); nashed11.setText(R.string.nashed_Angola_11);
             nashed12.setText(R.string.nashed_Angola_12); nashed13.setText(R.string.nashed_Angola_13);
             nashed14.setText(R.string.nashed_Angola_14); nashed15.setText(R.string.nashed_Angola_15);
             nashed16.setText(R.string.nashed_Angola_16); nashed17.setText(R.string.nashed_Angola_17);
             nashed18.setText(R.string.nashed_Angola_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2962FF"))
                     .show();
             break;
         case 37:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.cameroon);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Cameroon_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Cameroon");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Cameroon");
             }
             nashed.setText(R.string.nashed_Cameroon); nashed1.setText(R.string.nashed_Cameroon_1);
             nashed2.setText(R.string.nashed_Cameroon_2); nashed3.setText(R.string.nashed_Cameroon_3);
             nashed4.setText(R.string.nashed_Cameroon_4); nashed5.setText(R.string.nashed_Cameroon_5);
             nashed6.setText(R.string.nashed_Cameroon_6); nashed7.setText(R.string.nashed_Cameroon_7);
             nashed8.setText(R.string.nashed_Cameroon_8); nashed9.setText(R.string.nashed_Cameroon_9);
             nashed10.setText(R.string.nashed_Cameroon_10); nashed11.setText(R.string.nashed_Cameroon_11);
             nashed12.setText(R.string.nashed_Cameroon_12); nashed13.setText(R.string.nashed_Cameroon_13);
             nashed14.setText(R.string.nashed_Cameroon_14); nashed15.setText(R.string.nashed_Cameroon_15);
             nashed16.setText(R.string.nashed_Cameroon_16); nashed17.setText(R.string.nashed_Cameroon_17);
             nashed18.setText(R.string.nashed_Cameroon_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2962FF"))
                     .show();
             break;
         case 38:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.central_african);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Central_African_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Central_African_Republic");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Central_African_Republic");
             }
             nashed.setText(R.string.nashed_Central_African); nashed1.setText(R.string.nashed_Central_African_1);
             nashed2.setText(R.string.nashed_Central_African_2); nashed3.setText(R.string.nashed_Central_African_3);
             nashed4.setText(R.string.nashed_Central_African_4); nashed5.setText(R.string.nashed_Central_African_5);
             nashed6.setText(R.string.nashed_Central_African_6); nashed7.setText(R.string.nashed_Central_African_7);
             nashed8.setText(R.string.nashed_Central_African_8); nashed9.setText(R.string.nashed_Central_African_9);
             nashed10.setText(R.string.nashed_Central_African_10); nashed11.setText(R.string.nashed_Central_African_11);
             nashed12.setText(R.string.nashed_Central_African_12); nashed13.setText(R.string.nashed_Central_African_13);
             nashed14.setText(R.string.nashed_Central_African_14); nashed15.setText(R.string.nashed_Central_African_15);
             nashed16.setText(R.string.nashed_Central_African_16); nashed17.setText(R.string.nashed_Central_African_17);
             nashed18.setText(R.string.nashed_Central_African_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2962FF"))
                     .show();
             break;
         case 39:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.chad);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Chad_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Chad");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Chad");
             }
             nashed.setText(R.string.nashed_Chad); nashed1.setText(R.string.nashed_Chad_1);
             nashed2.setText(R.string.nashed_Chad_2); nashed3.setText(R.string.nashed_Chad_3);
             nashed4.setText(R.string.nashed_Chad_4); nashed5.setText(R.string.nashed_Chad_5);
             nashed6.setText(R.string.nashed_Chad_6); nashed7.setText(R.string.nashed_Chad_7);
             nashed8.setText(R.string.nashed_Chad_8); nashed9.setText(R.string.nashed_Chad_9);
             nashed10.setText(R.string.nashed_Chad_10); nashed11.setText(R.string.nashed_Chad_11);
             nashed12.setText(R.string.nashed_Chad_12); nashed13.setText(R.string.nashed_Chad_13);
             nashed14.setText(R.string.nashed_Chad_14); nashed15.setText(R.string.nashed_Chad_15);
             nashed16.setText(R.string.nashed_Chad_16); nashed17.setText(R.string.nashed_Chad_17);
             nashed18.setText(R.string.nashed_Chad_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2962FF"))
                     .show();
             break;
         case 40:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.congo_democratic_republic);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Congo_Democratic_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Democratic_Republic_of_the_Congo");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Democratic_Republic_of_the_Congo");
             }
             nashed.setText(R.string.nashed_Congo_Democratic); nashed1.setText(R.string.nashed_Congo_Democratic_1);
             nashed2.setText(R.string.nashed_Congo_Democratic_2); nashed3.setText(R.string.nashed_Congo_Democratic_3);
             nashed4.setText(R.string.nashed_Congo_Democratic_4); nashed5.setText(R.string.nashed_Congo_Democratic_5);
             nashed6.setText(R.string.nashed_Congo_Democratic_6); nashed7.setText(R.string.nashed_Congo_Democratic_7);
             nashed8.setText(R.string.nashed_Congo_Democratic_8); nashed9.setText(R.string.nashed_Congo_Democratic_9);
             nashed10.setText(R.string.nashed_Congo_Democratic_10); nashed11.setText(R.string.nashed_Congo_Democratic_11);
             nashed12.setText(R.string.nashed_Congo_Democratic_12); nashed13.setText(R.string.nashed_Congo_Democratic_13);
             nashed14.setText(R.string.nashed_Congo_Democratic_14); nashed15.setText(R.string.nashed_Congo_Democratic_15);
             nashed16.setText(R.string.nashed_Congo_Democratic_16); nashed17.setText(R.string.nashed_Congo_Democratic_17);
             nashed18.setText(R.string.nashed_Congo_Democratic_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2962FF"))
                     .show();
             break;
         case 41:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.equatorial_guinea);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Equatorial_Guinea_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Equatorial_Guinea");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Equatorial_Guinea");
             }
             nashed.setText(R.string.nashed_Equatorial_Guinea); nashed1.setText(R.string.nashed_Equatorial_Guinea_1);
             nashed2.setText(R.string.nashed_Equatorial_Guinea_2); nashed3.setText(R.string.nashed_Equatorial_Guinea_3);
             nashed4.setText(R.string.nashed_Equatorial_Guinea_4); nashed5.setText(R.string.nashed_Equatorial_Guinea_5);
             nashed6.setText(R.string.nashed_Equatorial_Guinea_6); nashed7.setText(R.string.nashed_Equatorial_Guinea_7);
             nashed8.setText(R.string.nashed_Equatorial_Guinea_8); nashed9.setText(R.string.nashed_Equatorial_Guinea_9);
             nashed10.setText(R.string.nashed_Equatorial_Guinea_10); nashed11.setText(R.string.nashed_Equatorial_Guinea_11);
             nashed12.setText(R.string.nashed_Equatorial_Guinea_12); nashed13.setText(R.string.nashed_Equatorial_Guinea_13);
             nashed14.setText(R.string.nashed_Equatorial_Guinea_14); nashed15.setText(R.string.nashed_Equatorial_Guinea_15);
             nashed16.setText(R.string.nashed_Equatorial_Guinea_16); nashed17.setText(R.string.nashed_Equatorial_Guinea_17);
             nashed18.setText(R.string.nashed_Equatorial_Guinea_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2962FF"))
                     .show();
             break;
         case 42:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.gabon);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Gabon_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Gabon");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Gabon");
             }
             nashed.setText(R.string.nashed_Gabon); nashed1.setText(R.string.nashed_Gabon_1);
             nashed2.setText(R.string.nashed_Gabon_2); nashed3.setText(R.string.nashed_Gabon_3);
             nashed4.setText(R.string.nashed_Gabon_4); nashed5.setText(R.string.nashed_Gabon_5);
             nashed6.setText(R.string.nashed_Gabon_6); nashed7.setText(R.string.nashed_Gabon_7);
             nashed8.setText(R.string.nashed_Gabon_8); nashed9.setText(R.string.nashed_Gabon_9);
             nashed10.setText(R.string.nashed_Gabon_10); nashed11.setText(R.string.nashed_Gabon_11);
             nashed12.setText(R.string.nashed_Gabon_12); nashed13.setText(R.string.nashed_Gabon_13);
             nashed14.setText(R.string.nashed_Gabon_14); nashed15.setText(R.string.nashed_Gabon_15);
             nashed16.setText(R.string.nashed_Gabon_16); nashed17.setText(R.string.nashed_Gabon_17);
             nashed18.setText(R.string.nashed_Gabon_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2962FF"))
                     .show();
             break;
         case 43:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.republic_of_the_congo);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Congo_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Republic_of_the_Congo");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Republic_of_the_Congo");
             }
             nashed.setText(R.string.nashed_Congo); nashed1.setText(R.string.nashed_Congo_1);
             nashed2.setText(R.string.nashed_Congo_2); nashed3.setText(R.string.nashed_Congo_3);
             nashed4.setText(R.string.nashed_Congo_4); nashed5.setText(R.string.nashed_Congo_5);
             nashed6.setText(R.string.nashed_Congo_6); nashed7.setText(R.string.nashed_Congo_7);
             nashed8.setText(R.string.nashed_Congo_8); nashed9.setText(R.string.nashed_Congo_9);
             nashed10.setText(R.string.nashed_Congo_10); nashed11.setText(R.string.nashed_Congo_11);
             nashed12.setText(R.string.nashed_Congo_12); nashed13.setText(R.string.nashed_Congo_13);
             nashed14.setText(R.string.nashed_Congo_14); nashed15.setText(R.string.nashed_Congo_15);
             nashed16.setText(R.string.nashed_Congo_16); nashed17.setText(R.string.nashed_Congo_17);
             nashed18.setText(R.string.nashed_Congo_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2962FF"))
                     .show();
             break;
         case 44:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.sao_tome_and_prince);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Sao_tome_and_prince_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/São_Tomé_and_Príncipe");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/São_Tomé_and_Príncipe");
             }
             nashed.setText(R.string.nashed_Sao_tome_and_prince); nashed1.setText(R.string.nashed_Sao_tome_and_prince_1);
             nashed2.setText(R.string.nashed_Sao_tome_and_prince_2); nashed3.setText(R.string.nashed_Sao_tome_and_prince_3);
             nashed4.setText(R.string.nashed_Sao_tome_and_prince_4); nashed5.setText(R.string.nashed_Sao_tome_and_prince_5);
             nashed6.setText(R.string.nashed_Sao_tome_and_prince_6); nashed7.setText(R.string.nashed_Sao_tome_and_prince_7);
             nashed8.setText(R.string.nashed_Sao_tome_and_prince_8); nashed9.setText(R.string.nashed_Sao_tome_and_prince_9);
             nashed10.setText(R.string.nashed_Sao_tome_and_prince_10); nashed11.setText(R.string.nashed_Sao_tome_and_prince_11);
             nashed12.setText(R.string.nashed_Sao_tome_and_prince_12); nashed13.setText(R.string.nashed_Sao_tome_and_prince_13);
             nashed14.setText(R.string.nashed_Sao_tome_and_prince_14); nashed15.setText(R.string.nashed_Sao_tome_and_prince_15);
             nashed16.setText(R.string.nashed_Sao_tome_and_prince_16); nashed17.setText(R.string.nashed_Sao_tome_and_prince_17);
             nashed18.setText(R.string.nashed_Sao_tome_and_prince_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2962FF"))
                     .show();
             break;
         case 45:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.botswana);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Botswana_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Botswana");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Botswana");
             }
             nashed.setText(R.string.nashed_Botswana); nashed1.setText(R.string.nashed_Botswana_1);
             nashed2.setText(R.string.nashed_Botswana_2); nashed3.setText(R.string.nashed_Botswana_3);
             nashed4.setText(R.string.nashed_Botswana_4); nashed5.setText(R.string.nashed_Botswana_5);
             nashed6.setText(R.string.nashed_Botswana_6); nashed7.setText(R.string.nashed_Botswana_7);
             nashed8.setText(R.string.nashed_Botswana_8); nashed9.setText(R.string.nashed_Botswana_9);
             nashed10.setText(R.string.nashed_Botswana_10); nashed11.setText(R.string.nashed_Botswana_11);
             nashed12.setText(R.string.nashed_Botswana_12); nashed13.setText(R.string.nashed_Botswana_13);
             nashed14.setText(R.string.nashed_Botswana_14); nashed15.setText(R.string.nashed_Botswana_15);
             nashed16.setText(R.string.nashed_Botswana_16); nashed17.setText(R.string.nashed_Botswana_17);
             nashed18.setText(R.string.nashed_Botswana_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#0091EA"))
                     .show();
             break;
         case 46:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.lesotho);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.red);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.red);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.red);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Lesotho_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Lesotho");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Lesotho");
             }
             nashed.setText(R.string.nashed_Lesotho); nashed1.setText(R.string.nashed_Lesotho_1);
             nashed2.setText(R.string.nashed_Lesotho_2); nashed3.setText(R.string.nashed_Lesotho_3);
             nashed4.setText(R.string.nashed_Lesotho_4); nashed5.setText(R.string.nashed_Lesotho_5);
             nashed6.setText(R.string.nashed_Lesotho_6); nashed7.setText(R.string.nashed_Lesotho_7);
             nashed8.setText(R.string.nashed_Lesotho_8); nashed9.setText(R.string.nashed_Lesotho_9);
             nashed10.setText(R.string.nashed_Lesotho_10); nashed11.setText(R.string.nashed_Lesotho_11);
             nashed12.setText(R.string.nashed_Lesotho_12); nashed13.setText(R.string.nashed_Lesotho_13);
             nashed14.setText(R.string.nashed_Lesotho_14); nashed15.setText(R.string.nashed_Lesotho_15);
             nashed16.setText(R.string.nashed_Lesotho_16); nashed17.setText(R.string.nashed_Lesotho_17);
             nashed18.setText(R.string.nashed_Lesotho_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#0091EA"))
                     .show();
             break;
         case 47:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.namibia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Namibia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Namibia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Namibia");
             }
             nashed.setText(R.string.nashed_Namibia); nashed1.setText(R.string.nashed_Namibia_1);
             nashed2.setText(R.string.nashed_Namibia_2); nashed3.setText(R.string.nashed_Namibia_3);
             nashed4.setText(R.string.nashed_Namibia_4); nashed5.setText(R.string.nashed_Namibia_5);
             nashed6.setText(R.string.nashed_Namibia_6); nashed7.setText(R.string.nashed_Namibia_7);
             nashed8.setText(R.string.nashed_Namibia_8); nashed9.setText(R.string.nashed_Namibia_9);
             nashed10.setText(R.string.nashed_Namibia_10); nashed11.setText(R.string.nashed_Namibia_11);
             nashed12.setText(R.string.nashed_Namibia_12); nashed13.setText(R.string.nashed_Namibia_13);
             nashed14.setText(R.string.nashed_Namibia_14); nashed15.setText(R.string.nashed_Namibia_15);
             nashed16.setText(R.string.nashed_Namibia_16); nashed17.setText(R.string.nashed_Namibia_17);
             nashed18.setText(R.string.nashed_Namibia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#0091EA"))
                     .show();
             break;
         case 48:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.south_africa);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.South_Africa_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/South_Africa");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/South_Africa");
             }
             nashed.setText(R.string.nashed_South_Africa); nashed1.setText(R.string.nashed_South_Africa_1);
             nashed2.setText(R.string.nashed_South_Africa_2); nashed3.setText(R.string.nashed_South_Africa_3);
             nashed4.setText(R.string.nashed_South_Africa_4); nashed5.setText(R.string.nashed_South_Africa_5);
             nashed6.setText(R.string.nashed_South_Africa_6); nashed7.setText(R.string.nashed_South_Africa_7);
             nashed8.setText(R.string.nashed_South_Africa_8); nashed9.setText(R.string.nashed_South_Africa_9);
             nashed10.setText(R.string.nashed_South_Africa_10); nashed11.setText(R.string.nashed_South_Africa_11);
             nashed12.setText(R.string.nashed_South_Africa_12); nashed13.setText(R.string.nashed_South_Africa_13);
             nashed14.setText(R.string.nashed_South_Africa_14); nashed15.setText(R.string.nashed_South_Africa_15);
             nashed16.setText(R.string.nashed_South_Africa_16); nashed17.setText(R.string.nashed_South_Africa_17);
             nashed18.setText(R.string.nashed_South_Africa_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#0091EA"))
                     .show();
             break;
         case 49:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.swaziland);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.green);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Swaziland_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Swaziland");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Swaziland");
             }
             nashed.setText(R.string.nashed_Swaziland); nashed1.setText(R.string.nashed_Swaziland_1);
             nashed2.setText(R.string.nashed_Swaziland_2); nashed3.setText(R.string.nashed_Swaziland_3);
             nashed4.setText(R.string.nashed_Swaziland_4); nashed5.setText(R.string.nashed_Swaziland_5);
             nashed6.setText(R.string.nashed_Swaziland_6); nashed7.setText(R.string.nashed_Swaziland_7);
             nashed8.setText(R.string.nashed_Swaziland_8); nashed9.setText(R.string.nashed_Swaziland_9);
             nashed10.setText(R.string.nashed_Swaziland_10); nashed11.setText(R.string.nashed_Swaziland_11);
             nashed12.setText(R.string.nashed_Swaziland_12); nashed13.setText(R.string.nashed_Swaziland_13);
             nashed14.setText(R.string.nashed_Swaziland_14); nashed15.setText(R.string.nashed_Swaziland_15);
             nashed16.setText(R.string.nashed_Swaziland_16); nashed17.setText(R.string.nashed_Swaziland_17);
             nashed18.setText(R.string.nashed_Swaziland_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#0091EA"))
                     .show();
             break;
             //west_africa
         case 50:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.benin);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.green);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.green);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.green);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Benin_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Benin");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Benin");
            }
            nashed.setText(R.string.nashed_Benin); nashed1.setText(R.string.nashed_Benin_1);
            nashed2.setText(R.string.nashed_Benin_2); nashed3.setText(R.string.nashed_Benin_3);
            nashed4.setText(R.string.nashed_Benin_4); nashed5.setText(R.string.nashed_Benin_5);
            nashed6.setText(R.string.nashed_Benin_6); nashed7.setText(R.string.nashed_Benin_7);
            nashed8.setText(R.string.nashed_Benin_8); nashed9.setText(R.string.nashed_Benin_9);
            nashed10.setText(R.string.nashed_Benin_10); nashed11.setText(R.string.nashed_Benin_11);
            nashed12.setText(R.string.nashed_Benin_12); nashed13.setText(R.string.nashed_Benin_13);
            nashed14.setText(R.string.nashed_Benin_14); nashed15.setText(R.string.nashed_Benin_15);
            nashed16.setText(R.string.nashed_Benin_16); nashed17.setText(R.string.nashed_Benin_17);
            nashed18.setText(R.string.nashed_Benin_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
            break;
         case 51:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.burkina_faso);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Burkina_Faso_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Burkina_Faso");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Burkina_Faso");
            }
            nashed.setText(R.string.nashed_Burkina_Faso); nashed1.setText(R.string.nashed_Burkina_Faso_1);
            nashed2.setText(R.string.nashed_Burkina_Faso_2); nashed3.setText(R.string.nashed_Burkina_Faso_3);
            nashed4.setText(R.string.nashed_Burkina_Faso_4); nashed5.setText(R.string.nashed_Burkina_Faso_5);
            nashed6.setText(R.string.nashed_Burkina_Faso_6); nashed7.setText(R.string.nashed_Burkina_Faso_7);
            nashed8.setText(R.string.nashed_Burkina_Faso_8); nashed9.setText(R.string.nashed_Burkina_Faso_9);
            nashed10.setText(R.string.nashed_Burkina_Faso_10); nashed11.setText(R.string.nashed_Burkina_Faso_11);
            nashed12.setText(R.string.nashed_Burkina_Faso_12); nashed13.setText(R.string.nashed_Burkina_Faso_13);
            nashed14.setText(R.string.nashed_Burkina_Faso_14); nashed15.setText(R.string.nashed_Burkina_Faso_15);
            nashed16.setText(R.string.nashed_Burkina_Faso_16); nashed17.setText(R.string.nashed_Burkina_Faso_17);
            nashed18.setText(R.string.nashed_Burkina_Faso_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
            break;
         case 52:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.cape_verde);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Cape_Verde_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Cape_Verde");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Cape_Verde");
            }
            nashed.setText(R.string.nashed_Cape_Verde); nashed1.setText(R.string.nashed_Cape_Verde_1);
            nashed2.setText(R.string.nashed_Cape_Verde_2); nashed3.setText(R.string.nashed_Cape_Verde_3);
            nashed4.setText(R.string.nashed_Cape_Verde_4); nashed5.setText(R.string.nashed_Cape_Verde_5);
            nashed6.setText(R.string.nashed_Cape_Verde_6); nashed7.setText(R.string.nashed_Cape_Verde_7);
            nashed8.setText(R.string.nashed_Cape_Verde_8); nashed9.setText(R.string.nashed_Cape_Verde_9);
            nashed10.setText(R.string.nashed_Cape_Verde_10); nashed11.setText(R.string.nashed_Cape_Verde_11);
            nashed12.setText(R.string.nashed_Cape_Verde_12); nashed13.setText(R.string.nashed_Cape_Verde_13);
            nashed14.setText(R.string.nashed_Cape_Verde_14); nashed15.setText(R.string.nashed_Cape_Verde_15);
            nashed16.setText(R.string.nashed_Cape_Verde_16); nashed17.setText(R.string.nashed_Cape_Verde_17);
            nashed18.setText(R.string.nashed_Cape_Verde_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
            break;
         case 53:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.gambia);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Gambia_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Gambia");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Gambia");
            }
            nashed.setText(R.string.nashed_Gambia); nashed1.setText(R.string.nashed_Gambia_1);
            nashed2.setText(R.string.nashed_Gambia_2); nashed3.setText(R.string.nashed_Gambia_3);
            nashed4.setText(R.string.nashed_Gambia_4); nashed5.setText(R.string.nashed_Gambia_5);
            nashed6.setText(R.string.nashed_Gambia_6); nashed7.setText(R.string.nashed_Gambia_7);
            nashed8.setText(R.string.nashed_Gambia_8); nashed9.setText(R.string.nashed_Gambia_9);
            nashed10.setText(R.string.nashed_Gambia_10); nashed11.setText(R.string.nashed_Gambia_11);
            nashed12.setText(R.string.nashed_Gambia_12); nashed13.setText(R.string.nashed_Gambia_13);
            nashed14.setText(R.string.nashed_Gambia_14); nashed15.setText(R.string.nashed_Gambia_15);
            nashed16.setText(R.string.nashed_Gambia_16); nashed17.setText(R.string.nashed_Gambia_17);
            nashed18.setText(R.string.nashed_Gambia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
            break;
         case 54:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.ghana);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.grey);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.grey);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.grey);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Ghana_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Ghana");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Ghana");
            }
            nashed.setText(R.string.nashed_Ghana); nashed1.setText(R.string.nashed_Ghana_1);
            nashed2.setText(R.string.nashed_Ghana_2); nashed3.setText(R.string.nashed_Ghana_3);
            nashed4.setText(R.string.nashed_Ghana_4); nashed5.setText(R.string.nashed_Ghana_5);
            nashed6.setText(R.string.nashed_Ghana_6); nashed7.setText(R.string.nashed_Ghana_7);
            nashed8.setText(R.string.nashed_Ghana_8); nashed9.setText(R.string.nashed_Ghana_9);
            nashed10.setText(R.string.nashed_Ghana_10); nashed11.setText(R.string.nashed_Ghana_11);
            nashed12.setText(R.string.nashed_Ghana_12); nashed13.setText(R.string.nashed_Ghana_13);
            nashed14.setText(R.string.nashed_Ghana_14); nashed15.setText(R.string.nashed_Ghana_15);
            nashed16.setText(R.string.nashed_Ghana_16); nashed17.setText(R.string.nashed_Ghana_17);
            nashed18.setText(R.string.nashed_Ghana_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
            break;
         case 55:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.guinea);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Guinea_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Guinea");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Guinea");
            }
            nashed.setText(R.string.nashed_Guinea); nashed1.setText(R.string.nashed_Guinea_1);
            nashed2.setText(R.string.nashed_Guinea_2); nashed3.setText(R.string.nashed_Guinea_3);
            nashed4.setText(R.string.nashed_Guinea_4); nashed5.setText(R.string.nashed_Guinea_5);
            nashed6.setText(R.string.nashed_Guinea_6); nashed7.setText(R.string.nashed_Guinea_7);
            nashed8.setText(R.string.nashed_Guinea_8); nashed9.setText(R.string.nashed_Guinea_9);
            nashed10.setText(R.string.nashed_Guinea_10); nashed11.setText(R.string.nashed_Guinea_11);
            nashed12.setText(R.string.nashed_Guinea_12); nashed13.setText(R.string.nashed_Guinea_13);
            nashed14.setText(R.string.nashed_Guinea_14); nashed15.setText(R.string.nashed_Guinea_15);
            nashed16.setText(R.string.nashed_Guinea_16); nashed17.setText(R.string.nashed_Guinea_17);
            nashed18.setText(R.string.nashed_Guinea_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
            break;
         case 56:
           EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.guinea_bissau);
           EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
           EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
           EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
           EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Guinea_Bissau_lock);
           if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
               EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Guinea-Bissau");
           }
           if (context.getString(R.string.lang).equals(londonLanguagwCode)){
               EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Guinea-Bissau");
           }
           nashed.setText(R.string.nashed_Guinea_Bissau); nashed1.setText(R.string.nashed_Guinea_Bissau_1);
           nashed2.setText(R.string.nashed_Guinea_Bissau_2); nashed3.setText(R.string.nashed_Guinea_Bissau_3);
           nashed4.setText(R.string.nashed_Guinea_Bissau_4); nashed5.setText(R.string.nashed_Guinea_Bissau_5);
           nashed6.setText(R.string.nashed_Guinea_Bissau_6); nashed7.setText(R.string.nashed_Guinea_Bissau_7);
           nashed8.setText(R.string.nashed_Guinea_Bissau_8); nashed9.setText(R.string.nashed_Guinea_Bissau_9);
           nashed10.setText(R.string.nashed_Guinea_Bissau_10); nashed11.setText(R.string.nashed_Guinea_Bissau_11);
           nashed12.setText(R.string.nashed_Guinea_Bissau_12); nashed13.setText(R.string.nashed_Guinea_Bissau_13);
           nashed14.setText(R.string.nashed_Guinea_Bissau_14); nashed15.setText(R.string.nashed_Guinea_Bissau_15);
           nashed16.setText(R.string.nashed_Guinea_Bissau_16); nashed17.setText(R.string.nashed_Guinea_Bissau_17);
           nashed18.setText(R.string.nashed_Guinea_Bissau_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
           break;
         case 57:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.liberia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.white);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Liberia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Liberia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Liberia");
             }
             nashed.setText(R.string.nashed_Liberia); nashed1.setText(R.string.nashed_Liberia_1);
             nashed2.setText(R.string.nashed_Liberia_2); nashed3.setText(R.string.nashed_Liberia_3);
             nashed4.setText(R.string.nashed_Liberia_4); nashed5.setText(R.string.nashed_Liberia_5);
             nashed6.setText(R.string.nashed_Liberia_6); nashed7.setText(R.string.nashed_Liberia_7);
             nashed8.setText(R.string.nashed_Liberia_8); nashed9.setText(R.string.nashed_Liberia_9);
             nashed10.setText(R.string.nashed_Liberia_10); nashed11.setText(R.string.nashed_Liberia_11);
             nashed12.setText(R.string.nashed_Liberia_12); nashed13.setText(R.string.nashed_Liberia_13);
             nashed14.setText(R.string.nashed_Liberia_14); nashed15.setText(R.string.nashed_Liberia_15);
             nashed16.setText(R.string.nashed_Liberia_16); nashed17.setText(R.string.nashed_Liberia_17);
             nashed18.setText(R.string.nashed_Liberia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
             break;
         case 58:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.mali);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.grey);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.grey);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.grey);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Mali_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Mali");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Mali");
            }
            nashed.setText(R.string.nashed_Mali); nashed1.setText(R.string.nashed_Mali_1);
            nashed2.setText(R.string.nashed_Mali_2); nashed3.setText(R.string.nashed_Mali_3);
            nashed4.setText(R.string.nashed_Mali_4); nashed5.setText(R.string.nashed_Mali_5);
            nashed6.setText(R.string.nashed_Mali_6); nashed7.setText(R.string.nashed_Mali_7);
            nashed8.setText(R.string.nashed_Mali_8); nashed9.setText(R.string.nashed_Mali_9);
            nashed10.setText(R.string.nashed_Mali_10); nashed11.setText(R.string.nashed_Mali_11);
            nashed12.setText(R.string.nashed_Mali_12); nashed13.setText(R.string.nashed_Mali_13);
            nashed14.setText(R.string.nashed_Mali_14); nashed15.setText(R.string.nashed_Mali_15);
            nashed16.setText(R.string.nashed_Mali_16); nashed17.setText(R.string.nashed_Mali_17);
            nashed18.setText(R.string.nashed_Mali_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
            break;
         case 59:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.niger);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.red);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.red);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.red);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Niger_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Niger");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Niger");
            }
            nashed.setText(R.string.nashed_Niger); nashed1.setText(R.string.nashed_Niger_1);
            nashed2.setText(R.string.nashed_Niger_2); nashed3.setText(R.string.nashed_Niger_3);
            nashed4.setText(R.string.nashed_Niger_4); nashed5.setText(R.string.nashed_Niger_5);
            nashed6.setText(R.string.nashed_Niger_6); nashed7.setText(R.string.nashed_Niger_7);
            nashed8.setText(R.string.nashed_Niger_8); nashed9.setText(R.string.nashed_Niger_9);
            nashed10.setText(R.string.nashed_Niger_10); nashed11.setText(R.string.nashed_Niger_11);
            nashed12.setText(R.string.nashed_Niger_12); nashed13.setText(R.string.nashed_Niger_13);
            nashed14.setText(R.string.nashed_Niger_14); nashed15.setText(R.string.nashed_Niger_15);
            nashed16.setText(R.string.nashed_Niger_16); nashed17.setText(R.string.nashed_Niger_17);
            nashed18.setText(R.string.nashed_Niger_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
            break;
         case 60:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.nigeria);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Nigeria_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Nigeria");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Nigeria");
             }
             nashed.setText(R.string.nashed_Nigeria); nashed1.setText(R.string.nashed_Nigeria_1);
             nashed2.setText(R.string.nashed_Nigeria_2); nashed3.setText(R.string.nashed_Nigeria_3);
             nashed4.setText(R.string.nashed_Nigeria_4); nashed5.setText(R.string.nashed_Nigeria_5);
             nashed6.setText(R.string.nashed_Nigeria_6); nashed7.setText(R.string.nashed_Nigeria_7);
             nashed8.setText(R.string.nashed_Nigeria_8); nashed9.setText(R.string.nashed_Nigeria_9);
             nashed10.setText(R.string.nashed_Nigeria_10); nashed11.setText(R.string.nashed_Nigeria_11);
             nashed12.setText(R.string.nashed_Nigeria_12); nashed13.setText(R.string.nashed_Nigeria_13);
             nashed14.setText(R.string.nashed_Nigeria_14); nashed15.setText(R.string.nashed_Nigeria_15);
             nashed16.setText(R.string.nashed_Nigeria_16); nashed17.setText(R.string.nashed_Nigeria_17);
             nashed18.setText(R.string.nashed_Nigeria_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
             break;
         case 61:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.senegal);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Senegal_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Senegal");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Senegal");
             }
             nashed.setText(R.string.nashed_Senegal); nashed1.setText(R.string.nashed_Senegal_1);
             nashed2.setText(R.string.nashed_Senegal_2); nashed3.setText(R.string.nashed_Senegal_3);
             nashed4.setText(R.string.nashed_Senegal_4); nashed5.setText(R.string.nashed_Senegal_5);
             nashed6.setText(R.string.nashed_Senegal_6); nashed7.setText(R.string.nashed_Senegal_7);
             nashed8.setText(R.string.nashed_Senegal_8); nashed9.setText(R.string.nashed_Senegal_9);
             nashed10.setText(R.string.nashed_Senegal_10); nashed11.setText(R.string.nashed_Senegal_11);
             nashed12.setText(R.string.nashed_Senegal_12); nashed13.setText(R.string.nashed_Senegal_13);
             nashed14.setText(R.string.nashed_Senegal_14); nashed15.setText(R.string.nashed_Senegal_15);
             nashed16.setText(R.string.nashed_Senegal_16); nashed17.setText(R.string.nashed_Senegal_17);
             nashed18.setText(R.string.nashed_Senegal_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
             break;
         case 62:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.sierra_leone);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.green);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.green);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.green);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Sierra_Leone_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Sierra_Leone");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Sierra_Leone");
            }
            nashed.setText(R.string.nashed_Sierra_Leone); nashed1.setText(R.string.nashed_Sierra_Leone_1);
            nashed2.setText(R.string.nashed_Sierra_Leone_2); nashed3.setText(R.string.nashed_Sierra_Leone_3);
            nashed4.setText(R.string.nashed_Sierra_Leone_4); nashed5.setText(R.string.nashed_Sierra_Leone_5);
            nashed6.setText(R.string.nashed_Sierra_Leone_6); nashed7.setText(R.string.nashed_Sierra_Leone_7);
            nashed8.setText(R.string.nashed_Sierra_Leone_8); nashed9.setText(R.string.nashed_Sierra_Leone_9);
            nashed10.setText(R.string.nashed_Sierra_Leone_10); nashed11.setText(R.string.nashed_Sierra_Leone_11);
            nashed12.setText(R.string.nashed_Sierra_Leone_12); nashed13.setText(R.string.nashed_Sierra_Leone_13);
            nashed14.setText(R.string.nashed_Sierra_Leone_14); nashed15.setText(R.string.nashed_Sierra_Leone_15);
            nashed16.setText(R.string.nashed_Sierra_Leone_16); nashed17.setText(R.string.nashed_Sierra_Leone_17);
            nashed18.setText(R.string.nashed_Sierra_Leone_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
            break;
         case 63:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.togo);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.green);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.green);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.green);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Togo_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Togo");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Togo");
            }
            nashed.setText(R.string.nashed_Togo); nashed1.setText(R.string.nashed_Togo_1);
            nashed2.setText(R.string.nashed_Togo_2); nashed3.setText(R.string.nashed_Togo_3);
            nashed4.setText(R.string.nashed_Togo_4); nashed5.setText(R.string.nashed_Togo_5);
            nashed6.setText(R.string.nashed_Togo_6); nashed7.setText(R.string.nashed_Togo_7);
            nashed8.setText(R.string.nashed_Togo_8); nashed9.setText(R.string.nashed_Togo_9);
            nashed10.setText(R.string.nashed_Togo_10); nashed11.setText(R.string.nashed_Togo_11);
            nashed12.setText(R.string.nashed_Togo_12); nashed13.setText(R.string.nashed_Togo_13);
            nashed14.setText(R.string.nashed_Togo_14); nashed15.setText(R.string.nashed_Togo_15);
            nashed16.setText(R.string.nashed_Togo_16); nashed17.setText(R.string.nashed_Togo_17);
            nashed18.setText(R.string.nashed_Togo_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_africa))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00B8D4"))
                     .show();
            break;
            //middel Asia
         //time scrop
           case 64:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.kazakhstan);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Kazakhstan_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Kazakhstan");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Kazakhstan");
            }
            nashed.setText(R.string.nashed_Kazakhstan); nashed1.setText(R.string.nashed_Kazakhstan_1);
            nashed2.setText(R.string.nashed_Kazakhstan_2); nashed3.setText(R.string.nashed_Kazakhstan_3);
            nashed4.setText(R.string.nashed_Kazakhstan_4); nashed5.setText(R.string.nashed_Kazakhstan_5);
            nashed6.setText(R.string.nashed_Kazakhstan_6); nashed7.setText(R.string.nashed_Kazakhstan_7);
            nashed8.setText(R.string.nashed_Kazakhstan_8); nashed9.setText(R.string.nashed_Kazakhstan_9);
            nashed10.setText(R.string.nashed_Kazakhstan_10); nashed11.setText(R.string.nashed_Kazakhstan_11);
            nashed12.setText(R.string.nashed_Kazakhstan_12); nashed13.setText(R.string.nashed_Kazakhstan_13);
            nashed14.setText(R.string.nashed_Kazakhstan_14); nashed15.setText(R.string.nashed_Kazakhstan_15);
            nashed16.setText(R.string.nashed_Kazakhstan_16); nashed17.setText(R.string.nashed_Kazakhstan_17);
            nashed18.setText(R.string.nashed_Kazakhstan_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00695C"))
                     .show();
            break;
         case 65:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.kyrgyzstan);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Kyrgyzstan_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Kyrgyzstan");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Kyrgyzstan");
            }
            nashed.setText(R.string.nashed_Kyrgyzstan); nashed1.setText(R.string.nashed_Kyrgyzstan_1);
            nashed2.setText(R.string.nashed_Kyrgyzstan_2); nashed3.setText(R.string.nashed_Kyrgyzstan_3);
            nashed4.setText(R.string.nashed_Kyrgyzstan_4); nashed5.setText(R.string.nashed_Kyrgyzstan_5);
            nashed6.setText(R.string.nashed_Kyrgyzstan_6); nashed7.setText(R.string.nashed_Kyrgyzstan_7);
            nashed8.setText(R.string.nashed_Kyrgyzstan_8); nashed9.setText(R.string.nashed_Kyrgyzstan_9);
            nashed10.setText(R.string.nashed_Kyrgyzstan_10); nashed11.setText(R.string.nashed_Kyrgyzstan_11);
            nashed12.setText(R.string.nashed_Kyrgyzstan_12); nashed13.setText(R.string.nashed_Kyrgyzstan_13);
            nashed14.setText(R.string.nashed_Kyrgyzstan_14); nashed15.setText(R.string.nashed_Kyrgyzstan_15);
            nashed16.setText(R.string.nashed_Kyrgyzstan_16); nashed17.setText(R.string.nashed_Kyrgyzstan_17);
            nashed18.setText(R.string.nashed_Kyrgyzstan_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00695C"))
                     .show();
            break;
         case 66:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.tajikistan);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Tajikistan_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Tajikistan");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Tajikistan");
             }
             nashed.setText(R.string.nashed_Tajikistan); nashed1.setText(R.string.nashed_Tajikistan_1);
             nashed2.setText(R.string.nashed_Tajikistan_2); nashed3.setText(R.string.nashed_Tajikistan_3);
             nashed4.setText(R.string.nashed_Tajikistan_4); nashed5.setText(R.string.nashed_Tajikistan_5);
             nashed6.setText(R.string.nashed_Tajikistan_6); nashed7.setText(R.string.nashed_Tajikistan_7);
             nashed8.setText(R.string.nashed_Tajikistan_8); nashed9.setText(R.string.nashed_Tajikistan_9);
             nashed10.setText(R.string.nashed_Tajikistan_10); nashed11.setText(R.string.nashed_Tajikistan_11);
             nashed12.setText(R.string.nashed_Tajikistan_12); nashed13.setText(R.string.nashed_Tajikistan_13);
             nashed14.setText(R.string.nashed_Tajikistan_14); nashed15.setText(R.string.nashed_Tajikistan_15);
             nashed16.setText(R.string.nashed_Tajikistan_16); nashed17.setText(R.string.nashed_Tajikistan_17);
             nashed18.setText(R.string.nashed_Tajikistan_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00695C"))
                     .show();
             break;
         case 67:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.turkmenistan);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Turkmenistan_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Turkmenistan");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Turkmenistan");
             }
             nashed.setText(R.string.nashed_Turkmenistan); nashed1.setText(R.string.nashed_Turkmenistan_1);
             nashed2.setText(R.string.nashed_Turkmenistan_2); nashed3.setText(R.string.nashed_Turkmenistan_3);
             nashed4.setText(R.string.nashed_Turkmenistan_4); nashed5.setText(R.string.nashed_Turkmenistan_5);
             nashed6.setText(R.string.nashed_Turkmenistan_6); nashed7.setText(R.string.nashed_Turkmenistan_7);
             nashed8.setText(R.string.nashed_Turkmenistan_8); nashed9.setText(R.string.nashed_Turkmenistan_9);
             nashed10.setText(R.string.nashed_Turkmenistan_10); nashed11.setText(R.string.nashed_Turkmenistan_11);
             nashed12.setText(R.string.nashed_Turkmenistan_12); nashed13.setText(R.string.nashed_Turkmenistan_13);
             nashed14.setText(R.string.nashed_Turkmenistan_14); nashed15.setText(R.string.nashed_Turkmenistan_15);
             nashed16.setText(R.string.nashed_Turkmenistan_16); nashed17.setText(R.string.nashed_Turkmenistan_17);
             nashed18.setText(R.string.nashed_Turkmenistan_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00695C"))
                     .show();
             break;
             //COLOR AKAREB
         case 68:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.uzbekistan);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Uzbekistan_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Uzbekistan");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Uzbekistan");
            }
            nashed.setText(R.string.nashed_Uzbekistan); nashed1.setText(R.string.nashed_Uzbekistan_1);
            nashed2.setText(R.string.nashed_Uzbekistan_2); nashed3.setText(R.string.nashed_Uzbekistan_3);
            nashed4.setText(R.string.nashed_Uzbekistan_4); nashed5.setText(R.string.nashed_Uzbekistan_5);
            nashed6.setText(R.string.nashed_Uzbekistan_6); nashed7.setText(R.string.nashed_Uzbekistan_7);
            nashed8.setText(R.string.nashed_Uzbekistan_8); nashed9.setText(R.string.nashed_Uzbekistan_9);
            nashed10.setText(R.string.nashed_Uzbekistan_10); nashed11.setText(R.string.nashed_Uzbekistan_11);
            nashed12.setText(R.string.nashed_Uzbekistan_12); nashed13.setText(R.string.nashed_Uzbekistan_13);
            nashed14.setText(R.string.nashed_Uzbekistan_14); nashed15.setText(R.string.nashed_Uzbekistan_15);
            nashed16.setText(R.string.nashed_Uzbekistan_16); nashed17.setText(R.string.nashed_Uzbekistan_17);
            nashed18.setText(R.string.nashed_Uzbekistan_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00695C"))
                     .show();
            break;
         case 69:
            EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.afghanistan);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.grey);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.grey);
            EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.grey);
            EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Afghanistan_lock);
            if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Afghanistan");
            }
            if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Afghanistan");
            }
            nashed.setText(R.string.nashed_Afghanistan); nashed1.setText(R.string.nashed_Afghanistan_1);
            nashed2.setText(R.string.nashed_Afghanistan_2); nashed3.setText(R.string.nashed_Afghanistan_3);
            nashed4.setText(R.string.nashed_Afghanistan_4); nashed5.setText(R.string.nashed_Afghanistan_5);
            nashed6.setText(R.string.nashed_Afghanistan_6); nashed7.setText(R.string.nashed_Afghanistan_7);
            nashed8.setText(R.string.nashed_Afghanistan_8); nashed9.setText(R.string.nashed_Afghanistan_9);
            nashed10.setText(R.string.nashed_Afghanistan_10); nashed11.setText(R.string.nashed_Afghanistan_11);
            nashed12.setText(R.string.nashed_Afghanistan_12); nashed13.setText(R.string.nashed_Afghanistan_13);
            nashed14.setText(R.string.nashed_Afghanistan_14); nashed15.setText(R.string.nashed_Afghanistan_15);
            nashed16.setText(R.string.nashed_Afghanistan_16); nashed17.setText(R.string.nashed_Afghanistan_17);
            nashed18.setText(R.string.nashed_Afghanistan_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.middel_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#00695C"))
                     .show();
            break;
         case 70:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.china);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.China_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/China");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/China");
             }
             nashed.setText(R.string.nashed_China); nashed1.setText(R.string.nashed_China_1);
             nashed2.setText(R.string.nashed_China_2); nashed3.setText(R.string.nashed_China_3);
             nashed4.setText(R.string.nashed_China_4); nashed5.setText(R.string.nashed_China_5);
             nashed6.setText(R.string.nashed_China_6); nashed7.setText(R.string.nashed_China_7);
             nashed8.setText(R.string.nashed_China_8); nashed9.setText(R.string.nashed_China_9);
             nashed10.setText(R.string.nashed_China_10); nashed11.setText(R.string.nashed_China_11);
             nashed12.setText(R.string.nashed_China_12); nashed13.setText(R.string.nashed_China_13);
             nashed14.setText(R.string.nashed_China_14); nashed15.setText(R.string.nashed_China_15);
             nashed16.setText(R.string.nashed_China_16); nashed17.setText(R.string.nashed_China_17);
             nashed18.setText(R.string.nashed_China_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2E7D32"))
                     .show();
             break;
         case 71:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.japan);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Japan_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Japan");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Japan");
             }
             nashed.setText(R.string.nashed_Japan); nashed1.setText(R.string.nashed_Japan_1);
             nashed2.setText(R.string.nashed_Japan_2); nashed3.setText(R.string.nashed_Japan_3);
             nashed4.setText(R.string.nashed_Japan_4); nashed5.setText(R.string.nashed_Japan_5);
             nashed6.setText(R.string.nashed_Japan_6); nashed7.setText(R.string.nashed_Japan_7);
             nashed8.setText(R.string.nashed_Japan_8); nashed9.setText(R.string.nashed_Japan_9);
             nashed10.setText(R.string.nashed_Japan_10); nashed11.setText(R.string.nashed_Japan_11);
             nashed12.setText(R.string.nashed_Japan_12); nashed13.setText(R.string.nashed_Japan_13);
             nashed14.setText(R.string.nashed_Japan_14); nashed15.setText(R.string.nashed_Japan_15);
             nashed16.setText(R.string.nashed_Japan_16); nashed17.setText(R.string.nashed_Japan_17);
             nashed18.setText(R.string.nashed_Japan_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2E7D32"))
                     .show();
             break;
         case 72:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.north_korea);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.North_Korea_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/North_Korea");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/North_Korea");
             }
             nashed.setText(R.string.nashed_North_Korea); nashed1.setText(R.string.nashed_North_Korea_1);
             nashed2.setText(R.string.nashed_North_Korea_2); nashed3.setText(R.string.nashed_North_Korea_3);
             nashed4.setText(R.string.nashed_North_Korea_4); nashed5.setText(R.string.nashed_North_Korea_5);
             nashed6.setText(R.string.nashed_North_Korea_6); nashed7.setText(R.string.nashed_North_Korea_7);
             nashed8.setText(R.string.nashed_North_Korea_8); nashed9.setText(R.string.nashed_North_Korea_9);
             nashed10.setText(R.string.nashed_North_Korea_10); nashed11.setText(R.string.nashed_North_Korea_11);
             nashed12.setText(R.string.nashed_North_Korea_12); nashed13.setText(R.string.nashed_North_Korea_13);
             nashed14.setText(R.string.nashed_North_Korea_14); nashed15.setText(R.string.nashed_North_Korea_15);
             nashed16.setText(R.string.nashed_North_Korea_16); nashed17.setText(R.string.nashed_North_Korea_17);
             nashed18.setText(R.string.nashed_North_Korea_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2E7D32"))
                     .show();
             break;
         case 73:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.korea);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Korea_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/South_Korea");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/South_Korea");
             }
             nashed.setText(R.string.nashed_Korea); nashed1.setText(R.string.nashed_Korea_1);
             nashed2.setText(R.string.nashed_Korea_2); nashed3.setText(R.string.nashed_Korea_3);
             nashed4.setText(R.string.nashed_Korea_4); nashed5.setText(R.string.nashed_Korea_5);
             nashed6.setText(R.string.nashed_Korea_6); nashed7.setText(R.string.nashed_Korea_7);
             nashed8.setText(R.string.nashed_Korea_8); nashed9.setText(R.string.nashed_Korea_9);
             nashed10.setText(R.string.nashed_Korea_10); nashed11.setText(R.string.nashed_Korea_11);
             nashed12.setText(R.string.nashed_Korea_12); nashed13.setText(R.string.nashed_Korea_13);
             nashed14.setText(R.string.nashed_Korea_14); nashed15.setText(R.string.nashed_Korea_15);
             nashed16.setText(R.string.nashed_Korea_16); nashed17.setText(R.string.nashed_Korea_17);
             nashed18.setText(R.string.nashed_Korea_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2E7D32"))
                     .show();
             break;
         case 74:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.mongolia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Mongolia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Mongolia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Mongolia");
             }
             nashed.setText(R.string.nashed_Mongolia); nashed1.setText(R.string.nashed_Mongolia_1);
             nashed2.setText(R.string.nashed_Mongolia_2); nashed3.setText(R.string.nashed_Mongolia_3);
             nashed4.setText(R.string.nashed_Mongolia_4); nashed5.setText(R.string.nashed_Mongolia_5);
             nashed6.setText(R.string.nashed_Mongolia_6); nashed7.setText(R.string.nashed_Mongolia_7);
             nashed8.setText(R.string.nashed_Mongolia_8); nashed9.setText(R.string.nashed_Mongolia_9);
             nashed10.setText(R.string.nashed_Mongolia_10); nashed11.setText(R.string.nashed_Mongolia_11);
             nashed12.setText(R.string.nashed_Mongolia_12); nashed13.setText(R.string.nashed_Mongolia_13);
             nashed14.setText(R.string.nashed_Mongolia_14); nashed15.setText(R.string.nashed_Mongolia_15);
             nashed16.setText(R.string.nashed_Mongolia_16); nashed17.setText(R.string.nashed_Mongolia_17);
             nashed18.setText(R.string.nashed_Mongolia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#2E7D32"))
                     .show();
             break;
         case 75:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.brunei);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.grey);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Brunei_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Brunei");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Brunei");
             }
             nashed.setText(R.string.nashed_Brunei); nashed1.setText(R.string.nashed_Brunei_1);
             nashed2.setText(R.string.nashed_Brunei_2); nashed3.setText(R.string.nashed_Brunei_3);
             nashed4.setText(R.string.nashed_Brunei_4); nashed5.setText(R.string.nashed_Brunei_5);
             nashed6.setText(R.string.nashed_Brunei_6); nashed7.setText(R.string.nashed_Brunei_7);
             nashed8.setText(R.string.nashed_Brunei_8); nashed9.setText(R.string.nashed_Brunei_9);
             nashed10.setText(R.string.nashed_Brunei_10); nashed11.setText(R.string.nashed_Brunei_11);
             nashed12.setText(R.string.nashed_Brunei_12); nashed13.setText(R.string.nashed_Brunei_13);
             nashed14.setText(R.string.nashed_Brunei_14); nashed15.setText(R.string.nashed_Brunei_15);
             nashed16.setText(R.string.nashed_Brunei_16); nashed17.setText(R.string.nashed_Brunei_17);
             nashed18.setText(R.string.nashed_Brunei_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.southeast_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#689F38"))
                     .show();
             break;
         case 76:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.cambodia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Cambodia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Cambodia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Cambodia");
             }
             nashed.setText(R.string.nashed_Cambodia); nashed1.setText(R.string.nashed_Cambodia_1);
             nashed2.setText(R.string.nashed_Cambodia_2); nashed3.setText(R.string.nashed_Cambodia_3);
             nashed4.setText(R.string.nashed_Cambodia_4); nashed5.setText(R.string.nashed_Cambodia_5);
             nashed6.setText(R.string.nashed_Cambodia_6); nashed7.setText(R.string.nashed_Cambodia_7);
             nashed8.setText(R.string.nashed_Cambodia_8); nashed9.setText(R.string.nashed_Cambodia_9);
             nashed10.setText(R.string.nashed_Cambodia_10); nashed11.setText(R.string.nashed_Cambodia_11);
             nashed12.setText(R.string.nashed_Cambodia_12); nashed13.setText(R.string.nashed_Cambodia_13);
             nashed14.setText(R.string.nashed_Cambodia_14); nashed15.setText(R.string.nashed_Cambodia_15);
             nashed16.setText(R.string.nashed_Cambodia_16); nashed17.setText(R.string.nashed_Cambodia_17);
             nashed18.setText(R.string.nashed_Cambodia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.southeast_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#689F38"))
                     .show();
             break;
                case 77:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.east_timor);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.East_Timor_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/East_Timor");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/East_Timor");
             }
             nashed.setText(R.string.nashed_East_Timor); nashed1.setText(R.string.nashed_East_Timor_1);
             nashed2.setText(R.string.nashed_East_Timor_2); nashed3.setText(R.string.nashed_East_Timor_3);
             nashed4.setText(R.string.nashed_East_Timor_4); nashed5.setText(R.string.nashed_East_Timor_5);
             nashed6.setText(R.string.nashed_East_Timor_6); nashed7.setText(R.string.nashed_East_Timor_7);
             nashed8.setText(R.string.nashed_East_Timor_8); nashed9.setText(R.string.nashed_East_Timor_9);
             nashed10.setText(R.string.nashed_East_Timor_10); nashed11.setText(R.string.nashed_East_Timor_11);
             nashed12.setText(R.string.nashed_East_Timor_12); nashed13.setText(R.string.nashed_East_Timor_13);
             nashed14.setText(R.string.nashed_East_Timor_14); nashed15.setText(R.string.nashed_East_Timor_15);
             nashed16.setText(R.string.nashed_East_Timor_16); nashed17.setText(R.string.nashed_East_Timor_17);
             nashed18.setText(R.string.nashed_East_Timor_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.southeast_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#689F38"))
                     .show();
             break;
             case 78:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.indonesia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Indonesia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Indonesia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Indonesia");
             }
             nashed.setText(R.string.nashed_Indonesia); nashed1.setText(R.string.nashed_Indonesia_1);
             nashed2.setText(R.string.nashed_Indonesia_2); nashed3.setText(R.string.nashed_Indonesia_3);
             nashed4.setText(R.string.nashed_Indonesia_4); nashed5.setText(R.string.nashed_Indonesia_5);
             nashed6.setText(R.string.nashed_Indonesia_6); nashed7.setText(R.string.nashed_Indonesia_7);
             nashed8.setText(R.string.nashed_Indonesia_8); nashed9.setText(R.string.nashed_Indonesia_9);
             nashed10.setText(R.string.nashed_Indonesia_10); nashed11.setText(R.string.nashed_Indonesia_11);
             nashed12.setText(R.string.nashed_Indonesia_12); nashed13.setText(R.string.nashed_Indonesia_13);
             nashed14.setText(R.string.nashed_Indonesia_14); nashed15.setText(R.string.nashed_Indonesia_15);
             nashed16.setText(R.string.nashed_Indonesia_16); nashed17.setText(R.string.nashed_Indonesia_17);
             nashed18.setText(R.string.nashed_Indonesia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.southeast_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#689F38"))
                     .show();
             break;
                  case 79:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.laos);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Laos_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Laos");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Laos");
             }
             nashed.setText(R.string.nashed_Laos); nashed1.setText(R.string.nashed_Laos_1);
             nashed2.setText(R.string.nashed_Laos_2); nashed3.setText(R.string.nashed_Laos_3);
             nashed4.setText(R.string.nashed_Laos_4); nashed5.setText(R.string.nashed_Laos_5);
             nashed6.setText(R.string.nashed_Laos_6); nashed7.setText(R.string.nashed_Laos_7);
             nashed8.setText(R.string.nashed_Laos_8); nashed9.setText(R.string.nashed_Laos_9);
             nashed10.setText(R.string.nashed_Laos_10); nashed11.setText(R.string.nashed_Laos_11);
             nashed12.setText(R.string.nashed_Laos_12); nashed13.setText(R.string.nashed_Laos_13);
             nashed14.setText(R.string.nashed_Laos_14); nashed15.setText(R.string.nashed_Laos_15);
             nashed16.setText(R.string.nashed_Laos_16); nashed17.setText(R.string.nashed_Laos_17);
             nashed18.setText(R.string.nashed_Laos_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.southeast_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#689F38"))
                     .show();
             break;
             case 80:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.malaysia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Malaysia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Malaysia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Malaysia");
             }
             nashed.setText(R.string.nashed_Malaysia); nashed1.setText(R.string.nashed_Malaysia_1);
             nashed2.setText(R.string.nashed_Malaysia_2); nashed3.setText(R.string.nashed_Malaysia_3);
             nashed4.setText(R.string.nashed_Malaysia_4); nashed5.setText(R.string.nashed_Malaysia_5);
             nashed6.setText(R.string.nashed_Malaysia_6); nashed7.setText(R.string.nashed_Malaysia_7);
             nashed8.setText(R.string.nashed_Malaysia_8); nashed9.setText(R.string.nashed_Malaysia_9);
             nashed10.setText(R.string.nashed_Malaysia_10); nashed11.setText(R.string.nashed_Malaysia_11);
             nashed12.setText(R.string.nashed_Malaysia_12); nashed13.setText(R.string.nashed_Malaysia_13);
             nashed14.setText(R.string.nashed_Malaysia_14); nashed15.setText(R.string.nashed_Malaysia_15);
             nashed16.setText(R.string.nashed_Malaysia_16); nashed17.setText(R.string.nashed_Malaysia_17);
             nashed18.setText(R.string.nashed_Malaysia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.southeast_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#689F38"))
                     .show();
             break;
             case 81:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.philippines);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Philippines_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Philippines");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Philippines");
             }
             nashed.setText(R.string.nashed_Philippines); nashed1.setText(R.string.nashed_Philippines_1);
             nashed2.setText(R.string.nashed_Philippines_2); nashed3.setText(R.string.nashed_Philippines_3);
             nashed4.setText(R.string.nashed_Philippines_4); nashed5.setText(R.string.nashed_Philippines_5);
             nashed6.setText(R.string.nashed_Philippines_6); nashed7.setText(R.string.nashed_Philippines_7);
             nashed8.setText(R.string.nashed_Philippines_8); nashed9.setText(R.string.nashed_Philippines_9);
             nashed10.setText(R.string.nashed_Philippines_10); nashed11.setText(R.string.nashed_Philippines_11);
             nashed12.setText(R.string.nashed_Philippines_12); nashed13.setText(R.string.nashed_Philippines_13);
             nashed14.setText(R.string.nashed_Philippines_14); nashed15.setText(R.string.nashed_Philippines_15);
             nashed16.setText(R.string.nashed_Philippines_16); nashed17.setText(R.string.nashed_Philippines_17);
             nashed18.setText(R.string.nashed_Philippines_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.southeast_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#689F38"))
                     .show();
             break;
             case 82:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.singapore);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Singapore_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Singapore");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Singapore");
             }
             nashed.setText(R.string.nashed_Singapore); nashed1.setText(R.string.nashed_Singapore_1);
             nashed2.setText(R.string.nashed_Singapore_2); nashed3.setText(R.string.nashed_Singapore_3);
             nashed4.setText(R.string.nashed_Singapore_4); nashed5.setText(R.string.nashed_Singapore_5);
             nashed6.setText(R.string.nashed_Singapore_6); nashed7.setText(R.string.nashed_Singapore_7);
             nashed8.setText(R.string.nashed_Singapore_8); nashed9.setText(R.string.nashed_Singapore_9);
             nashed10.setText(R.string.nashed_Singapore_10); nashed11.setText(R.string.nashed_Singapore_11);
             nashed12.setText(R.string.nashed_Singapore_12); nashed13.setText(R.string.nashed_Singapore_13);
             nashed14.setText(R.string.nashed_Singapore_14); nashed15.setText(R.string.nashed_Singapore_15);
             nashed16.setText(R.string.nashed_Singapore_16); nashed17.setText(R.string.nashed_Singapore_17);
             nashed18.setText(R.string.nashed_Singapore_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.southeast_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#689F38"))
                     .show();
             break;
              case 83:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.thailand);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Thailand_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Thailand");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Thailand");
             }
             nashed.setText(R.string.nashed_Thailand); nashed1.setText(R.string.nashed_Thailand_1);
             nashed2.setText(R.string.nashed_Thailand_2); nashed3.setText(R.string.nashed_Thailand_3);
             nashed4.setText(R.string.nashed_Thailand_4); nashed5.setText(R.string.nashed_Thailand_5);
             nashed6.setText(R.string.nashed_Thailand_6); nashed7.setText(R.string.nashed_Thailand_7);
             nashed8.setText(R.string.nashed_Thailand_8); nashed9.setText(R.string.nashed_Thailand_9);
             nashed10.setText(R.string.nashed_Thailand_10); nashed11.setText(R.string.nashed_Thailand_11);
             nashed12.setText(R.string.nashed_Thailand_12); nashed13.setText(R.string.nashed_Thailand_13);
             nashed14.setText(R.string.nashed_Thailand_14); nashed15.setText(R.string.nashed_Thailand_15);
             nashed16.setText(R.string.nashed_Thailand_16); nashed17.setText(R.string.nashed_Thailand_17);
             nashed18.setText(R.string.nashed_Thailand_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.southeast_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#689F38"))
                     .show();
             break;
         case 84:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.bangladesh);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Bangladesh_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Bangladesh");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Bangladesh");
             }
             nashed.setText(R.string.nashed_Bangladesh); nashed1.setText(R.string.nashed_Bangladesh_1);
             nashed2.setText(R.string.nashed_Bangladesh_2); nashed3.setText(R.string.nashed_Bangladesh_3);
             nashed4.setText(R.string.nashed_Bangladesh_4); nashed5.setText(R.string.nashed_Bangladesh_5);
             nashed6.setText(R.string.nashed_Bangladesh_6); nashed7.setText(R.string.nashed_Bangladesh_7);
             nashed8.setText(R.string.nashed_Bangladesh_8); nashed9.setText(R.string.nashed_Bangladesh_9);
             nashed10.setText(R.string.nashed_Bangladesh_10); nashed11.setText(R.string.nashed_Bangladesh_11);
             nashed12.setText(R.string.nashed_Bangladesh_12); nashed13.setText(R.string.nashed_Bangladesh_13);
             nashed14.setText(R.string.nashed_Bangladesh_14); nashed15.setText(R.string.nashed_Bangladesh_15);
             nashed16.setText(R.string.nashed_Bangladesh_16); nashed17.setText(R.string.nashed_Bangladesh_17);
             nashed18.setText(R.string.nashed_Bangladesh_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#AFB42B"))
                     .show();
             break;
         case 85:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.bhutan);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Bhutan_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/%D8%A8%D9%88%D8%AA%D8%A7%D9%86");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Bhutan");
             }
             nashed.setText(R.string.nashed_Bhutan); nashed1.setText(R.string.nashed_Bhutan_1);
             nashed2.setText(R.string.nashed_Bhutan_2); nashed3.setText(R.string.nashed_Bhutan_3);
             nashed4.setText(R.string.nashed_Bhutan_4); nashed5.setText(R.string.nashed_Bhutan_5);
             nashed6.setText(R.string.nashed_Bhutan_6); nashed7.setText(R.string.nashed_Bhutan_7);
             nashed8.setText(R.string.nashed_Bhutan_8); nashed9.setText(R.string.nashed_Bhutan_9);
             nashed10.setText(R.string.nashed_Bhutan_10); nashed11.setText(R.string.nashed_Bhutan_11);
             nashed12.setText(R.string.nashed_Bhutan_12); nashed13.setText(R.string.nashed_Bhutan_13);
             nashed14.setText(R.string.nashed_Bhutan_14); nashed15.setText(R.string.nashed_Bhutan_15);
             nashed16.setText(R.string.nashed_Bhutan_16); nashed17.setText(R.string.nashed_Bhutan_17);
             nashed18.setText(R.string.nashed_Bhutan_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#AFB42B"))
                     .show();
             break;
         case 86:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.india);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.India_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/India");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/India");
             }
             nashed.setText(R.string.nashed_India); nashed1.setText(R.string.nashed_India_1);
             nashed2.setText(R.string.nashed_India_2); nashed3.setText(R.string.nashed_India_3);
             nashed4.setText(R.string.nashed_India_4); nashed5.setText(R.string.nashed_India_5);
             nashed6.setText(R.string.nashed_India_6); nashed7.setText(R.string.nashed_India_7);
             nashed8.setText(R.string.nashed_India_8); nashed9.setText(R.string.nashed_India_9);
             nashed10.setText(R.string.nashed_India_10); nashed11.setText(R.string.nashed_India_11);
             nashed12.setText(R.string.nashed_India_12); nashed13.setText(R.string.nashed_India_13);
             nashed14.setText(R.string.nashed_India_14); nashed15.setText(R.string.nashed_India_15);
             nashed16.setText(R.string.nashed_India_16); nashed17.setText(R.string.nashed_India_17);
             nashed18.setText(R.string.nashed_India_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#AFB42B"))
                     .show();
             break;
          case 87:
              EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.nepal);
              EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
              EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
              EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
              EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Nepal_lock);
              if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                  EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Nepal");
              }
              if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                  EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Nepal");
              }
              nashed.setText(R.string.nashed_Nepal); nashed1.setText(R.string.nashed_Nepal_1);
              nashed2.setText(R.string.nashed_Nepal_2); nashed3.setText(R.string.nashed_Nepal_3);
              nashed4.setText(R.string.nashed_Nepal_4); nashed5.setText(R.string.nashed_Nepal_5);
              nashed6.setText(R.string.nashed_Nepal_6); nashed7.setText(R.string.nashed_Nepal_7);
              nashed8.setText(R.string.nashed_Nepal_8); nashed9.setText(R.string.nashed_Nepal_9);
              nashed10.setText(R.string.nashed_Nepal_10); nashed11.setText(R.string.nashed_Nepal_11);
              nashed12.setText(R.string.nashed_Nepal_12); nashed13.setText(R.string.nashed_Nepal_13);
              nashed14.setText(R.string.nashed_Nepal_14); nashed15.setText(R.string.nashed_Nepal_15);
              nashed16.setText(R.string.nashed_Nepal_16); nashed17.setText(R.string.nashed_Nepal_17);
              nashed18.setText(R.string.nashed_Nepal_18);
              new MaterialToast(context)
                      .setMessage(getResources().getString(R.string.south_asia))
                      .setIcon(R.drawable.ic_check_circle)
                      .setDuration(Toast.LENGTH_SHORT)
                      .setBackgroundColor(Color.parseColor("#AFB42B"))
                      .show();
              break;
              case 88:
                  EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.pakistan);
                  EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
                  EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
                  EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
                  EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Pakistan_lock);
                  if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                      EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Pakistan");
                  }
                  if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                      EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Pakistan");
                  }
                  nashed.setText(R.string.nashed_Pakistan); nashed1.setText(R.string.nashed_Pakistan_1);
                  nashed2.setText(R.string.nashed_Pakistan_2); nashed3.setText(R.string.nashed_Pakistan_3);
                  nashed4.setText(R.string.nashed_Pakistan_4); nashed5.setText(R.string.nashed_Pakistan_5);
                  nashed6.setText(R.string.nashed_Pakistan_6); nashed7.setText(R.string.nashed_Pakistan_7);
                  nashed8.setText(R.string.nashed_Pakistan_8); nashed9.setText(R.string.nashed_Pakistan_9);
                  nashed10.setText(R.string.nashed_Pakistan_10); nashed11.setText(R.string.nashed_Pakistan_11);
                  nashed12.setText(R.string.nashed_Pakistan_12); nashed13.setText(R.string.nashed_Pakistan_13);
                  nashed14.setText(R.string.nashed_Pakistan_14); nashed15.setText(R.string.nashed_Pakistan_15);
                  nashed16.setText(R.string.nashed_Pakistan_16); nashed17.setText(R.string.nashed_Pakistan_17);
                  nashed18.setText(R.string.nashed_Pakistan_18);
                  new MaterialToast(context)
                          .setMessage(getResources().getString(R.string.south_asia))
                          .setIcon(R.drawable.ic_check_circle)
                          .setDuration(Toast.LENGTH_SHORT)
                          .setBackgroundColor(Color.parseColor("#AFB42B"))
                          .show();
                  break;
                case 89:
                    EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.sri_lanka);
                    EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
                    EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
                    EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
                    EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Sri_Lanka_lock);
                    if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                        EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Sri_Lanka");
                    }
                    if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                        EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Sri_Lanka");
                    }
                    nashed.setText(R.string.nashed_Sri_Lanka); nashed1.setText(R.string.nashed_Sri_Lanka_1);
                    nashed2.setText(R.string.nashed_Sri_Lanka_2); nashed3.setText(R.string.nashed_Sri_Lanka_3);
                    nashed4.setText(R.string.nashed_Sri_Lanka_4); nashed5.setText(R.string.nashed_Sri_Lanka_5);
                    nashed6.setText(R.string.nashed_Sri_Lanka_6); nashed7.setText(R.string.nashed_Sri_Lanka_7);
                    nashed8.setText(R.string.nashed_Sri_Lanka_8); nashed9.setText(R.string.nashed_Sri_Lanka_9);
                    nashed10.setText(R.string.nashed_Sri_Lanka_10); nashed11.setText(R.string.nashed_Sri_Lanka_11);
                    nashed12.setText(R.string.nashed_Sri_Lanka_12); nashed13.setText(R.string.nashed_Sri_Lanka_13);
                    nashed14.setText(R.string.nashed_Sri_Lanka_14); nashed15.setText(R.string.nashed_Sri_Lanka_15);
                    nashed16.setText(R.string.nashed_Sri_Lanka_16); nashed17.setText(R.string.nashed_Sri_Lanka_17);
                    nashed18.setText(R.string.nashed_Sri_Lanka_18);
                    new MaterialToast(context)
                            .setMessage(getResources().getString(R.string.south_asia))
                            .setIcon(R.drawable.ic_check_circle)
                            .setDuration(Toast.LENGTH_SHORT)
                            .setBackgroundColor(Color.parseColor("#AFB42B"))
                            .show();
                    break;
         case 90:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.armenia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Armenia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Armenia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Armenia");
             }
             nashed.setText(R.string.nashed_Armenia); nashed1.setText(R.string.nashed_Armenia_1);
             nashed2.setText(R.string.nashed_Armenia_2); nashed3.setText(R.string.nashed_Armenia_3);
             nashed4.setText(R.string.nashed_Armenia_4); nashed5.setText(R.string.nashed_Armenia_5);
             nashed6.setText(R.string.nashed_Armenia_6); nashed7.setText(R.string.nashed_Armenia_7);
             nashed8.setText(R.string.nashed_Armenia_8); nashed9.setText(R.string.nashed_Armenia_9);
             nashed10.setText(R.string.nashed_Armenia_10); nashed11.setText(R.string.nashed_Armenia_11);
             nashed12.setText(R.string.nashed_Armenia_12); nashed13.setText(R.string.nashed_Armenia_13);
             nashed14.setText(R.string.nashed_Armenia_14); nashed15.setText(R.string.nashed_Armenia_15);
             nashed16.setText(R.string.nashed_Armenia_16); nashed17.setText(R.string.nashed_Armenia_17);
             nashed18.setText(R.string.nashed_Armenia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFFF00"))
                     .show();
             break;
         case 91:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.azerbaijan);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Azerbaijan_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Azerbaijan");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Azerbaijan");
             }
             nashed.setText(R.string.nashed_Azerbaijan); nashed1.setText(R.string.nashed_Azerbaijan_1);
             nashed2.setText(R.string.nashed_Azerbaijan_2); nashed3.setText(R.string.nashed_Azerbaijan_3);
             nashed4.setText(R.string.nashed_Azerbaijan_4); nashed5.setText(R.string.nashed_Azerbaijan_5);
             nashed6.setText(R.string.nashed_Azerbaijan_6); nashed7.setText(R.string.nashed_Azerbaijan_7);
             nashed8.setText(R.string.nashed_Azerbaijan_8); nashed9.setText(R.string.nashed_Azerbaijan_9);
             nashed10.setText(R.string.nashed_Azerbaijan_10); nashed11.setText(R.string.nashed_Azerbaijan_11);
             nashed12.setText(R.string.nashed_Azerbaijan_12); nashed13.setText(R.string.nashed_Azerbaijan_13);
             nashed14.setText(R.string.nashed_Azerbaijan_14); nashed15.setText(R.string.nashed_Azerbaijan_15);
             nashed16.setText(R.string.nashed_Azerbaijan_16); nashed17.setText(R.string.nashed_Azerbaijan_17);
             nashed18.setText(R.string.nashed_Azerbaijan_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFFF00"))
                     .show();
             break;
         case 92:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.georgia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Georgia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Georgia_(country)");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Georgia_(country)");
             }
             nashed.setText(R.string.nashed_Georgia); nashed1.setText(R.string.nashed_Georgia_1);
             nashed2.setText(R.string.nashed_Georgia_2); nashed3.setText(R.string.nashed_Georgia_3);
             nashed4.setText(R.string.nashed_Georgia_4); nashed5.setText(R.string.nashed_Georgia_5);
             nashed6.setText(R.string.nashed_Georgia_6); nashed7.setText(R.string.nashed_Georgia_7);
             nashed8.setText(R.string.nashed_Georgia_8); nashed9.setText(R.string.nashed_Georgia_9);
             nashed10.setText(R.string.nashed_Georgia_10); nashed11.setText(R.string.nashed_Georgia_11);
             nashed12.setText(R.string.nashed_Georgia_12); nashed13.setText(R.string.nashed_Georgia_13);
             nashed14.setText(R.string.nashed_Georgia_14); nashed15.setText(R.string.nashed_Georgia_15);
             nashed16.setText(R.string.nashed_Georgia_16); nashed17.setText(R.string.nashed_Georgia_17);
             nashed18.setText(R.string.nashed_Georgia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFFF00"))
                     .show();
             break;
         case 93:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.iran);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Iran_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Iran");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Iran");
             }
             nashed.setText(R.string.nashed_Iran); nashed1.setText(R.string.nashed_Iran_1);
             nashed2.setText(R.string.nashed_Iran_2); nashed3.setText(R.string.nashed_Iran_3);
             nashed4.setText(R.string.nashed_Iran_4); nashed5.setText(R.string.nashed_Iran_5);
             nashed6.setText(R.string.nashed_Iran_6); nashed7.setText(R.string.nashed_Iran_7);
             nashed8.setText(R.string.nashed_Iran_8); nashed9.setText(R.string.nashed_Iran_9);
             nashed10.setText(R.string.nashed_Iran_10); nashed11.setText(R.string.nashed_Iran_11);
             nashed12.setText(R.string.nashed_Iran_12); nashed13.setText(R.string.nashed_Iran_13);
             nashed14.setText(R.string.nashed_Iran_14); nashed15.setText(R.string.nashed_Iran_15);
             nashed16.setText(R.string.nashed_Iran_16); nashed17.setText(R.string.nashed_Iran_17);
             nashed18.setText(R.string.nashed_Iran_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFFF00"))
                     .show();
             break;
         case 94:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.turkey);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Turkey_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Turkey");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Turkey");
             }
             nashed.setText(R.string.nashed_Turkey); nashed1.setText(R.string.nashed_Turkey_1);
             nashed2.setText(R.string.nashed_Turkey_2); nashed3.setText(R.string.nashed_Turkey_3);
             nashed4.setText(R.string.nashed_Turkey_4); nashed5.setText(R.string.nashed_Turkey_5);
             nashed6.setText(R.string.nashed_Turkey_6); nashed7.setText(R.string.nashed_Turkey_7);
             nashed8.setText(R.string.nashed_Turkey_8); nashed9.setText(R.string.nashed_Turkey_9);
             nashed10.setText(R.string.nashed_Turkey_10); nashed11.setText(R.string.nashed_Turkey_11);
             nashed12.setText(R.string.nashed_Turkey_12); nashed13.setText(R.string.nashed_Turkey_13);
             nashed14.setText(R.string.nashed_Turkey_14); nashed15.setText(R.string.nashed_Turkey_15);
             nashed16.setText(R.string.nashed_Turkey_16); nashed17.setText(R.string.nashed_Turkey_17);
             nashed18.setText(R.string.nashed_Turkey_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_asia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFFF00"))
                     .show();
             break;
         case 95:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.iceland);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Iceland_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Iceland");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Iceland");
             }
             nashed.setText(R.string.nashed_Iceland); nashed1.setText(R.string.nashed_Iceland_1);
             nashed2.setText(R.string.nashed_Iceland_2); nashed3.setText(R.string.nashed_Iceland_3);
             nashed4.setText(R.string.nashed_Iceland_4); nashed5.setText(R.string.nashed_Iceland_5);
             nashed6.setText(R.string.nashed_Iceland_6); nashed7.setText(R.string.nashed_Iceland_7);
             nashed8.setText(R.string.nashed_Iceland_8); nashed9.setText(R.string.nashed_Iceland_9);
             nashed10.setText(R.string.nashed_Iceland_10); nashed11.setText(R.string.nashed_Iceland_11);
             nashed12.setText(R.string.nashed_Iceland_12); nashed13.setText(R.string.nashed_Iceland_13);
             nashed14.setText(R.string.nashed_Iceland_14); nashed15.setText(R.string.nashed_Iceland_15);
             nashed16.setText(R.string.nashed_Iceland_16); nashed17.setText(R.string.nashed_Iceland_17);
             nashed18.setText(R.string.nashed_Iceland_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFA000"))
                     .show();
             break;
         case 96:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.norway);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Norway_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Norway");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Norway");
             }
             nashed.setText(R.string.nashed_Norway); nashed1.setText(R.string.nashed_Norway_1);
             nashed2.setText(R.string.nashed_Norway_2); nashed3.setText(R.string.nashed_Norway_3);
             nashed4.setText(R.string.nashed_Norway_4); nashed5.setText(R.string.nashed_Norway_5);
             nashed6.setText(R.string.nashed_Norway_6); nashed7.setText(R.string.nashed_Norway_7);
             nashed8.setText(R.string.nashed_Norway_8); nashed9.setText(R.string.nashed_Norway_9);
             nashed10.setText(R.string.nashed_Norway_10); nashed11.setText(R.string.nashed_Norway_11);
             nashed12.setText(R.string.nashed_Norway_12); nashed13.setText(R.string.nashed_Norway_13);
             nashed14.setText(R.string.nashed_Norway_14); nashed15.setText(R.string.nashed_Norway_15);
             nashed16.setText(R.string.nashed_Norway_16); nashed17.setText(R.string.nashed_Norway_17);
             nashed18.setText(R.string.nashed_Norway_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFA000"))
                     .show();
             break;
         case 97:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.sweden);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Sweden_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Sweden");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Sweden");
             }
             nashed.setText(R.string.nashed_Sweden); nashed1.setText(R.string.nashed_Sweden_1);
             nashed2.setText(R.string.nashed_Sweden_2); nashed3.setText(R.string.nashed_Sweden_3);
             nashed4.setText(R.string.nashed_Sweden_4); nashed5.setText(R.string.nashed_Sweden_5);
             nashed6.setText(R.string.nashed_Sweden_6); nashed7.setText(R.string.nashed_Sweden_7);
             nashed8.setText(R.string.nashed_Sweden_8); nashed9.setText(R.string.nashed_Sweden_9);
             nashed10.setText(R.string.nashed_Sweden_10); nashed11.setText(R.string.nashed_Sweden_11);
             nashed12.setText(R.string.nashed_Sweden_12); nashed13.setText(R.string.nashed_Sweden_13);
             nashed14.setText(R.string.nashed_Sweden_14); nashed15.setText(R.string.nashed_Sweden_15);
             nashed16.setText(R.string.nashed_Sweden_16); nashed17.setText(R.string.nashed_Sweden_17);
             nashed18.setText(R.string.nashed_Sweden_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFA000"))
                     .show();
             break;
         case 98:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.finland);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Finland_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Finland");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Finland");
             }
             nashed.setText(R.string.nashed_Finland); nashed1.setText(R.string.nashed_Finland_1);
             nashed2.setText(R.string.nashed_Finland_2); nashed3.setText(R.string.nashed_Finland_3);
             nashed4.setText(R.string.nashed_Finland_4); nashed5.setText(R.string.nashed_Finland_5);
             nashed6.setText(R.string.nashed_Finland_6); nashed7.setText(R.string.nashed_Finland_7);
             nashed8.setText(R.string.nashed_Finland_8); nashed9.setText(R.string.nashed_Finland_9);
             nashed10.setText(R.string.nashed_Finland_10); nashed11.setText(R.string.nashed_Finland_11);
             nashed12.setText(R.string.nashed_Finland_12); nashed13.setText(R.string.nashed_Finland_13);
             nashed14.setText(R.string.nashed_Finland_14); nashed15.setText(R.string.nashed_Finland_15);
             nashed16.setText(R.string.nashed_Finland_16); nashed17.setText(R.string.nashed_Finland_17);
             nashed18.setText(R.string.nashed_Finland_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFA000"))
                     .show();
             break;
         case 99:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.estonia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Estonia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Estonia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Estonia");
             }
             nashed.setText(R.string.nashed_Estonia); nashed1.setText(R.string.nashed_Estonia_1);
             nashed2.setText(R.string.nashed_Estonia_2); nashed3.setText(R.string.nashed_Estonia_3);
             nashed4.setText(R.string.nashed_Estonia_4); nashed5.setText(R.string.nashed_Estonia_5);
             nashed6.setText(R.string.nashed_Estonia_6); nashed7.setText(R.string.nashed_Estonia_7);
             nashed8.setText(R.string.nashed_Estonia_8); nashed9.setText(R.string.nashed_Estonia_9);
             nashed10.setText(R.string.nashed_Estonia_10); nashed11.setText(R.string.nashed_Estonia_11);
             nashed12.setText(R.string.nashed_Estonia_12); nashed13.setText(R.string.nashed_Estonia_13);
             nashed14.setText(R.string.nashed_Estonia_14); nashed15.setText(R.string.nashed_Estonia_15);
             nashed16.setText(R.string.nashed_Estonia_16); nashed17.setText(R.string.nashed_Estonia_17);
             nashed18.setText(R.string.nashed_Estonia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFA000"))
                     .show();
             break;
         case 100:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.latvia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Latvia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Latvia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Latvia");
             }
             nashed.setText(R.string.nashed_Latvia); nashed1.setText(R.string.nashed_Latvia_1);
             nashed2.setText(R.string.nashed_Latvia_2); nashed3.setText(R.string.nashed_Latvia_3);
             nashed4.setText(R.string.nashed_Latvia_4); nashed5.setText(R.string.nashed_Latvia_5);
             nashed6.setText(R.string.nashed_Latvia_6); nashed7.setText(R.string.nashed_Latvia_7);
             nashed8.setText(R.string.nashed_Latvia_8); nashed9.setText(R.string.nashed_Latvia_9);
             nashed10.setText(R.string.nashed_Latvia_10); nashed11.setText(R.string.nashed_Latvia_11);
             nashed12.setText(R.string.nashed_Latvia_12); nashed13.setText(R.string.nashed_Latvia_13);
             nashed14.setText(R.string.nashed_Latvia_14); nashed15.setText(R.string.nashed_Latvia_15);
             nashed16.setText(R.string.nashed_Latvia_16); nashed17.setText(R.string.nashed_Latvia_17);
             nashed18.setText(R.string.nashed_Latvia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFA000"))
                     .show();
             break;
         case 101:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.lithuania);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Lithuania_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Lithuania");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Lithuania");
             }
             nashed.setText(R.string.nashed_Lithuania); nashed1.setText(R.string.nashed_Lithuania_1);
             nashed2.setText(R.string.nashed_Lithuania_2); nashed3.setText(R.string.nashed_Lithuania_3);
             nashed4.setText(R.string.nashed_Lithuania_4); nashed5.setText(R.string.nashed_Lithuania_5);
             nashed6.setText(R.string.nashed_Lithuania_6); nashed7.setText(R.string.nashed_Lithuania_7);
             nashed8.setText(R.string.nashed_Lithuania_8); nashed9.setText(R.string.nashed_Lithuania_9);
             nashed10.setText(R.string.nashed_Lithuania_10); nashed11.setText(R.string.nashed_Lithuania_11);
             nashed12.setText(R.string.nashed_Lithuania_12); nashed13.setText(R.string.nashed_Lithuania_13);
             nashed14.setText(R.string.nashed_Lithuania_14); nashed15.setText(R.string.nashed_Lithuania_15);
             nashed16.setText(R.string.nashed_Lithuania_16); nashed17.setText(R.string.nashed_Lithuania_17);
             nashed18.setText(R.string.nashed_Lithuania_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFA000"))
                     .show();
             break;
         case 102:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.denmark);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Denmark_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Denmark");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Denmark");
             }
             nashed.setText(R.string.nashed_Denmark); nashed1.setText(R.string.nashed_Denmark_1);
             nashed2.setText(R.string.nashed_Denmark_2); nashed3.setText(R.string.nashed_Denmark_3);
             nashed4.setText(R.string.nashed_Denmark_4); nashed5.setText(R.string.nashed_Denmark_5);
             nashed6.setText(R.string.nashed_Denmark_6); nashed7.setText(R.string.nashed_Denmark_7);
             nashed8.setText(R.string.nashed_Denmark_8); nashed9.setText(R.string.nashed_Denmark_9);
             nashed10.setText(R.string.nashed_Denmark_10); nashed11.setText(R.string.nashed_Denmark_11);
             nashed12.setText(R.string.nashed_Denmark_12); nashed13.setText(R.string.nashed_Denmark_13);
             nashed14.setText(R.string.nashed_Denmark_14); nashed15.setText(R.string.nashed_Denmark_15);
             nashed16.setText(R.string.nashed_Denmark_16); nashed17.setText(R.string.nashed_Denmark_17);
             nashed18.setText(R.string.nashed_Denmark_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFA000"))
                     .show();
             break;
         case 103:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.ireland);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Ireland_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Ireland");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Ireland");
             }
             nashed.setText(R.string.nashed_Ireland); nashed1.setText(R.string.nashed_Ireland_1);
             nashed2.setText(R.string.nashed_Ireland_2); nashed3.setText(R.string.nashed_Ireland_3);
             nashed4.setText(R.string.nashed_Ireland_4); nashed5.setText(R.string.nashed_Ireland_5);
             nashed6.setText(R.string.nashed_Ireland_6); nashed7.setText(R.string.nashed_Ireland_7);
             nashed8.setText(R.string.nashed_Ireland_8); nashed9.setText(R.string.nashed_Ireland_9);
             nashed10.setText(R.string.nashed_Ireland_10); nashed11.setText(R.string.nashed_Ireland_11);
             nashed12.setText(R.string.nashed_Ireland_12); nashed13.setText(R.string.nashed_Ireland_13);
             nashed14.setText(R.string.nashed_Ireland_14); nashed15.setText(R.string.nashed_Ireland_15);
             nashed16.setText(R.string.nashed_Ireland_16); nashed17.setText(R.string.nashed_Ireland_17);
             nashed18.setText(R.string.nashed_Ireland_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFA000"))
                     .show();
             break;
         case 104:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.united_kingdom);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.United_Kingdom_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/United_Kingdom");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/United_Kingdom");
             }
             nashed.setText(R.string.nashed_United_Kingdom); nashed1.setText(R.string.nashed_United_Kingdom_1);
             nashed2.setText(R.string.nashed_United_Kingdom_2); nashed3.setText(R.string.nashed_United_Kingdom_3);
             nashed4.setText(R.string.nashed_United_Kingdom_4); nashed5.setText(R.string.nashed_United_Kingdom_5);
             nashed6.setText(R.string.nashed_United_Kingdom_6); nashed7.setText(R.string.nashed_United_Kingdom_7);
             nashed8.setText(R.string.nashed_United_Kingdom_8); nashed9.setText(R.string.nashed_United_Kingdom_9);
             nashed10.setText(R.string.nashed_United_Kingdom_10); nashed11.setText(R.string.nashed_United_Kingdom_11);
             nashed12.setText(R.string.nashed_United_Kingdom_12); nashed13.setText(R.string.nashed_United_Kingdom_13);
             nashed14.setText(R.string.nashed_United_Kingdom_14); nashed15.setText(R.string.nashed_United_Kingdom_15);
             nashed16.setText(R.string.nashed_United_Kingdom_16); nashed17.setText(R.string.nashed_United_Kingdom_17);
             nashed18.setText(R.string.nashed_United_Kingdom_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FFA000"))
                     .show();
             break;
         case 105:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.portugal);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Portugal_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Portugal");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Portugal");
             }
             nashed.setText(R.string.nashed_Portugal); nashed1.setText(R.string.nashed_Portugal_1);
             nashed2.setText(R.string.nashed_Portugal_2); nashed3.setText(R.string.nashed_Portugal_3);
             nashed4.setText(R.string.nashed_Portugal_4); nashed5.setText(R.string.nashed_Portugal_5);
             nashed6.setText(R.string.nashed_Portugal_6); nashed7.setText(R.string.nashed_Portugal_7);
             nashed8.setText(R.string.nashed_Portugal_8); nashed9.setText(R.string.nashed_Portugal_9);
             nashed10.setText(R.string.nashed_Portugal_10); nashed11.setText(R.string.nashed_Portugal_11);
             nashed12.setText(R.string.nashed_Portugal_12); nashed13.setText(R.string.nashed_Portugal_13);
             nashed14.setText(R.string.nashed_Portugal_14); nashed15.setText(R.string.nashed_Portugal_15);
             nashed16.setText(R.string.nashed_Portugal_16); nashed17.setText(R.string.nashed_Portugal_17);
             nashed18.setText(R.string.nashed_Portugal_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF6D00"))
                     .show();
             break;
         case 106:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.spain);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Spain_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Spain");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Spain");
             }
             nashed.setText(R.string.nashed_Spain); nashed1.setText(R.string.nashed_Spain_1);
             nashed2.setText(R.string.nashed_Spain_2); nashed3.setText(R.string.nashed_Spain_3);
             nashed4.setText(R.string.nashed_Spain_4); nashed5.setText(R.string.nashed_Spain_5);
             nashed6.setText(R.string.nashed_Spain_6); nashed7.setText(R.string.nashed_Spain_7);
             nashed8.setText(R.string.nashed_Spain_8); nashed9.setText(R.string.nashed_Spain_9);
             nashed10.setText(R.string.nashed_Spain_10); nashed11.setText(R.string.nashed_Spain_11);
             nashed12.setText(R.string.nashed_Spain_12); nashed13.setText(R.string.nashed_Spain_13);
             nashed14.setText(R.string.nashed_Spain_14); nashed15.setText(R.string.nashed_Spain_15);
             nashed16.setText(R.string.nashed_Spain_16); nashed17.setText(R.string.nashed_Spain_17);
             nashed18.setText(R.string.nashed_Spain_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF6D00"))
                     .show();
             break;
         case 107:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.malta);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Malta_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Malta");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Malta");
             }
             nashed.setText(R.string.nashed_Malta); nashed1.setText(R.string.nashed_Malta_1);
             nashed2.setText(R.string.nashed_Malta_2); nashed3.setText(R.string.nashed_Malta_3);
             nashed4.setText(R.string.nashed_Malta_4); nashed5.setText(R.string.nashed_Malta_5);
             nashed6.setText(R.string.nashed_Malta_6); nashed7.setText(R.string.nashed_Malta_7);
             nashed8.setText(R.string.nashed_Malta_8); nashed9.setText(R.string.nashed_Malta_9);
             nashed10.setText(R.string.nashed_Malta_10); nashed11.setText(R.string.nashed_Malta_11);
             nashed12.setText(R.string.nashed_Malta_12); nashed13.setText(R.string.nashed_Malta_13);
             nashed14.setText(R.string.nashed_Malta_14); nashed15.setText(R.string.nashed_Malta_15);
             nashed16.setText(R.string.nashed_Malta_16); nashed17.setText(R.string.nashed_Malta_17);
             nashed18.setText(R.string.nashed_Malta_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF6D00"))
                     .show();
             break;
         case 108:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.italy);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Italy_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Italy");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Italy");
             }
             nashed.setText(R.string.nashed_Italy); nashed1.setText(R.string.nashed_Italy_1);
             nashed2.setText(R.string.nashed_Italy_2); nashed3.setText(R.string.nashed_Italy_3);
             nashed4.setText(R.string.nashed_Italy_4); nashed5.setText(R.string.nashed_Italy_5);
             nashed6.setText(R.string.nashed_Italy_6); nashed7.setText(R.string.nashed_Italy_7);
             nashed8.setText(R.string.nashed_Italy_8); nashed9.setText(R.string.nashed_Italy_9);
             nashed10.setText(R.string.nashed_Italy_10); nashed11.setText(R.string.nashed_Italy_11);
             nashed12.setText(R.string.nashed_Italy_12); nashed13.setText(R.string.nashed_Italy_13);
             nashed14.setText(R.string.nashed_Italy_14); nashed15.setText(R.string.nashed_Italy_15);
             nashed16.setText(R.string.nashed_Italy_16); nashed17.setText(R.string.nashed_Italy_17);
             nashed18.setText(R.string.nashed_Italy_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF6D00"))
                     .show();
             break;
         case 109:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.greece);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Greece_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Greece");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Greece");
             }
             nashed.setText(R.string.nashed_Greece); nashed1.setText(R.string.nashed_Greece_1);
             nashed2.setText(R.string.nashed_Greece_2); nashed3.setText(R.string.nashed_Greece_3);
             nashed4.setText(R.string.nashed_Greece_4); nashed5.setText(R.string.nashed_Greece_5);
             nashed6.setText(R.string.nashed_Greece_6); nashed7.setText(R.string.nashed_Greece_7);
             nashed8.setText(R.string.nashed_Greece_8); nashed9.setText(R.string.nashed_Greece_9);
             nashed10.setText(R.string.nashed_Greece_10); nashed11.setText(R.string.nashed_Greece_11);
             nashed12.setText(R.string.nashed_Greece_12); nashed13.setText(R.string.nashed_Greece_13);
             nashed14.setText(R.string.nashed_Greece_14); nashed15.setText(R.string.nashed_Greece_15);
             nashed16.setText(R.string.nashed_Greece_16); nashed17.setText(R.string.nashed_Greece_17);
             nashed18.setText(R.string.nashed_Greece_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF6D00"))
                     .show();
             break;
         case 110:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.albania);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Albania_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Albania");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Albania");
             }
             nashed.setText(R.string.nashed_Albania); nashed1.setText(R.string.nashed_Albania_1);
             nashed2.setText(R.string.nashed_Albania_2); nashed3.setText(R.string.nashed_Albania_3);
             nashed4.setText(R.string.nashed_Albania_4); nashed5.setText(R.string.nashed_Albania_5);
             nashed6.setText(R.string.nashed_Albania_6); nashed7.setText(R.string.nashed_Albania_7);
             nashed8.setText(R.string.nashed_Albania_8); nashed9.setText(R.string.nashed_Albania_9);
             nashed10.setText(R.string.nashed_Albania_10); nashed11.setText(R.string.nashed_Albania_11);
             nashed12.setText(R.string.nashed_Albania_12); nashed13.setText(R.string.nashed_Albania_13);
             nashed14.setText(R.string.nashed_Albania_14); nashed15.setText(R.string.nashed_Albania_15);
             nashed16.setText(R.string.nashed_Albania_16); nashed17.setText(R.string.nashed_Albania_17);
             nashed18.setText(R.string.nashed_Albania_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF6D00"))
                     .show();
             break;
         case 111:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.serbia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Serbia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Serbia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Serbia");
             }
             nashed.setText(R.string.nashed_Serbia); nashed1.setText(R.string.nashed_Serbia_1);
             nashed2.setText(R.string.nashed_Serbia_2); nashed3.setText(R.string.nashed_Serbia_3);
             nashed4.setText(R.string.nashed_Serbia_4); nashed5.setText(R.string.nashed_Serbia_5);
             nashed6.setText(R.string.nashed_Serbia_6); nashed7.setText(R.string.nashed_Serbia_7);
             nashed8.setText(R.string.nashed_Serbia_8); nashed9.setText(R.string.nashed_Serbia_9);
             nashed10.setText(R.string.nashed_Serbia_10); nashed11.setText(R.string.nashed_Serbia_11);
             nashed12.setText(R.string.nashed_Serbia_12); nashed13.setText(R.string.nashed_Serbia_13);
             nashed14.setText(R.string.nashed_Serbia_14); nashed15.setText(R.string.nashed_Serbia_15);
             nashed16.setText(R.string.nashed_Serbia_16); nashed17.setText(R.string.nashed_Serbia_17);
             nashed18.setText(R.string.nashed_Serbia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF6D00"))
                     .show();
             break;
         case 112:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.croatia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Croatia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Croatia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Croatia");
             }
             nashed.setText(R.string.nashed_Croatia); nashed1.setText(R.string.nashed_Croatia_1);
             nashed2.setText(R.string.nashed_Croatia_2); nashed3.setText(R.string.nashed_Croatia_3);
             nashed4.setText(R.string.nashed_Croatia_4); nashed5.setText(R.string.nashed_Croatia_5);
             nashed6.setText(R.string.nashed_Croatia_6); nashed7.setText(R.string.nashed_Croatia_7);
             nashed8.setText(R.string.nashed_Croatia_8); nashed9.setText(R.string.nashed_Croatia_9);
             nashed10.setText(R.string.nashed_Croatia_10); nashed11.setText(R.string.nashed_Croatia_11);
             nashed12.setText(R.string.nashed_Croatia_12); nashed13.setText(R.string.nashed_Croatia_13);
             nashed14.setText(R.string.nashed_Croatia_14); nashed15.setText(R.string.nashed_Croatia_15);
             nashed16.setText(R.string.nashed_Croatia_16); nashed17.setText(R.string.nashed_Croatia_17);
             nashed18.setText(R.string.nashed_Croatia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF6D00"))
                     .show();
             break;
         case 113:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.bosnia_and_herzegovina);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.B_H_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/%D8%A7%D9%84%D8%A8%D9%88%D8%B3%D9%86%D8%A9_%D9%88%D8%A7%D9%84%D9%87%D8%B1%D8%B3%D9%83");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Bosnia_and_Herzegovina");
             }
             nashed.setText(R.string.nashed_B_H); nashed1.setText(R.string.nashed_B_H_1);
             nashed2.setText(R.string.nashed_B_H_2); nashed3.setText(R.string.nashed_B_H_3);
             nashed4.setText(R.string.nashed_B_H_4); nashed5.setText(R.string.nashed_B_H_5);
             nashed6.setText(R.string.nashed_B_H_6); nashed7.setText(R.string.nashed_B_H_7);
             nashed8.setText(R.string.nashed_B_H_8); nashed9.setText(R.string.nashed_B_H_9);
             nashed10.setText(R.string.nashed_B_H_10); nashed11.setText(R.string.nashed_B_H_11);
             nashed12.setText(R.string.nashed_B_H_12); nashed13.setText(R.string.nashed_B_H_13);
             nashed14.setText(R.string.nashed_B_H_14); nashed15.setText(R.string.nashed_B_H_15);
             nashed16.setText(R.string.nashed_B_H_16); nashed17.setText(R.string.nashed_B_H_17);
             nashed18.setText(R.string.nashed_B_H_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF6D00"))
                     .show();
             break;
         case 114:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.slovenia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Slovenia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Slovenia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Slovenia");
             }
             nashed.setText(R.string.nashed_Slovenia); nashed1.setText(R.string.nashed_Slovenia_1);
             nashed2.setText(R.string.nashed_Slovenia_2); nashed3.setText(R.string.nashed_Slovenia_3);
             nashed4.setText(R.string.nashed_Slovenia_4); nashed5.setText(R.string.nashed_Slovenia_5);
             nashed6.setText(R.string.nashed_Slovenia_6); nashed7.setText(R.string.nashed_Slovenia_7);
             nashed8.setText(R.string.nashed_Slovenia_8); nashed9.setText(R.string.nashed_Slovenia_9);
             nashed10.setText(R.string.nashed_Slovenia_10); nashed11.setText(R.string.nashed_Slovenia_11);
             nashed12.setText(R.string.nashed_Slovenia_12); nashed13.setText(R.string.nashed_Slovenia_13);
             nashed14.setText(R.string.nashed_Slovenia_14); nashed15.setText(R.string.nashed_Slovenia_15);
             nashed16.setText(R.string.nashed_Slovenia_16); nashed17.setText(R.string.nashed_Slovenia_17);
             nashed18.setText(R.string.nashed_Slovenia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF6D00"))
                     .show();
             break;
         case 115:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.france);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.France_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/France");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/France");
             }
             nashed.setText(R.string.nashed_France); nashed1.setText(R.string.nashed_France_1);
             nashed2.setText(R.string.nashed_France_2); nashed3.setText(R.string.nashed_France_3);
             nashed4.setText(R.string.nashed_France_4); nashed5.setText(R.string.nashed_France_5);
             nashed6.setText(R.string.nashed_France_6); nashed7.setText(R.string.nashed_France_7);
             nashed8.setText(R.string.nashed_France_8); nashed9.setText(R.string.nashed_France_9);
             nashed10.setText(R.string.nashed_France_10); nashed11.setText(R.string.nashed_France_11);
             nashed12.setText(R.string.nashed_France_12); nashed13.setText(R.string.nashed_France_13);
             nashed14.setText(R.string.nashed_France_14); nashed15.setText(R.string.nashed_France_15);
             nashed16.setText(R.string.nashed_France_16); nashed17.setText(R.string.nashed_France_17);
             nashed18.setText(R.string.nashed_France_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF3D00"))
                     .show();
             break;
         case 116:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.belgium);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Belgium_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Belgium");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Belgium");
             }
             nashed.setText(R.string.nashed_Belgium); nashed1.setText(R.string.nashed_Belgium_1);
             nashed2.setText(R.string.nashed_Belgium_2); nashed3.setText(R.string.nashed_Belgium_3);
             nashed4.setText(R.string.nashed_Belgium_4); nashed5.setText(R.string.nashed_Belgium_5);
             nashed6.setText(R.string.nashed_Belgium_6); nashed7.setText(R.string.nashed_Belgium_7);
             nashed8.setText(R.string.nashed_Belgium_8); nashed9.setText(R.string.nashed_Belgium_9);
             nashed10.setText(R.string.nashed_Belgium_10); nashed11.setText(R.string.nashed_Belgium_11);
             nashed12.setText(R.string.nashed_Belgium_12); nashed13.setText(R.string.nashed_Belgium_13);
             nashed14.setText(R.string.nashed_Belgium_14); nashed15.setText(R.string.nashed_Belgium_15);
             nashed16.setText(R.string.nashed_Belgium_16); nashed17.setText(R.string.nashed_Belgium_17);
             nashed18.setText(R.string.nashed_Belgium_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF3D00"))
                     .show();
             break;
         case 117:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.luxembourg);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Luxembourg_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Luxembourg");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Luxembourg");
             }
             nashed.setText(R.string.nashed_Luxembourg); nashed1.setText(R.string.nashed_Luxembourg_1);
             nashed2.setText(R.string.nashed_Luxembourg_2); nashed3.setText(R.string.nashed_Luxembourg_3);
             nashed4.setText(R.string.nashed_Luxembourg_4); nashed5.setText(R.string.nashed_Luxembourg_5);
             nashed6.setText(R.string.nashed_Luxembourg_6); nashed7.setText(R.string.nashed_Luxembourg_7);
             nashed8.setText(R.string.nashed_Luxembourg_8); nashed9.setText(R.string.nashed_Luxembourg_9);
             nashed10.setText(R.string.nashed_Luxembourg_10); nashed11.setText(R.string.nashed_Luxembourg_11);
             nashed12.setText(R.string.nashed_Luxembourg_12); nashed13.setText(R.string.nashed_Luxembourg_13);
             nashed14.setText(R.string.nashed_Luxembourg_14); nashed15.setText(R.string.nashed_Luxembourg_15);
             nashed16.setText(R.string.nashed_Luxembourg_16); nashed17.setText(R.string.nashed_Luxembourg_17);
             nashed18.setText(R.string.nashed_Luxembourg_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF3D00"))
                     .show();
             break;
         case 118:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.netherlands);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Netherlands_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Netherlands");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Netherlands");
             }
             nashed.setText(R.string.nashed_Netherlands); nashed1.setText(R.string.nashed_Netherlands_1);
             nashed2.setText(R.string.nashed_Netherlands_2); nashed3.setText(R.string.nashed_Netherlands_3);
             nashed4.setText(R.string.nashed_Netherlands_4); nashed5.setText(R.string.nashed_Netherlands_5);
             nashed6.setText(R.string.nashed_Netherlands_6); nashed7.setText(R.string.nashed_Netherlands_7);
             nashed8.setText(R.string.nashed_Netherlands_8); nashed9.setText(R.string.nashed_Netherlands_9);
             nashed10.setText(R.string.nashed_Netherlands_10); nashed11.setText(R.string.nashed_Netherlands_11);
             nashed12.setText(R.string.nashed_Netherlands_12); nashed13.setText(R.string.nashed_Netherlands_13);
             nashed14.setText(R.string.nashed_Netherlands_14); nashed15.setText(R.string.nashed_Netherlands_15);
             nashed16.setText(R.string.nashed_Netherlands_16); nashed17.setText(R.string.nashed_Netherlands_17);
             nashed18.setText(R.string.nashed_Netherlands_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF3D00"))
                     .show();
             break;
         case 119:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.germany);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Germany_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Germany");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Germany");
             }
             nashed.setText(R.string.nashed_Germany); nashed1.setText(R.string.nashed_Germany_1);
             nashed2.setText(R.string.nashed_Germany_2); nashed3.setText(R.string.nashed_Germany_3);
             nashed4.setText(R.string.nashed_Germany_4); nashed5.setText(R.string.nashed_Germany_5);
             nashed6.setText(R.string.nashed_Germany_6); nashed7.setText(R.string.nashed_Germany_7);
             nashed8.setText(R.string.nashed_Germany_8); nashed9.setText(R.string.nashed_Germany_9);
             nashed10.setText(R.string.nashed_Germany_10); nashed11.setText(R.string.nashed_Germany_11);
             nashed12.setText(R.string.nashed_Germany_12); nashed13.setText(R.string.nashed_Germany_13);
             nashed14.setText(R.string.nashed_Germany_14); nashed15.setText(R.string.nashed_Germany_15);
             nashed16.setText(R.string.nashed_Germany_16); nashed17.setText(R.string.nashed_Germany_17);
             nashed18.setText(R.string.nashed_Germany_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF3D00"))
                     .show();
             break;
         case 120:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.austria);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Austria_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Austria");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Austria");
             }
             nashed.setText(R.string.nashed_Austria); nashed1.setText(R.string.nashed_Austria_1);
             nashed2.setText(R.string.nashed_Austria_2); nashed3.setText(R.string.nashed_Austria_3);
             nashed4.setText(R.string.nashed_Austria_4); nashed5.setText(R.string.nashed_Austria_5);
             nashed6.setText(R.string.nashed_Austria_6); nashed7.setText(R.string.nashed_Austria_7);
             nashed8.setText(R.string.nashed_Austria_8); nashed9.setText(R.string.nashed_Austria_9);
             nashed10.setText(R.string.nashed_Austria_10); nashed11.setText(R.string.nashed_Austria_11);
             nashed12.setText(R.string.nashed_Austria_12); nashed13.setText(R.string.nashed_Austria_13);
             nashed14.setText(R.string.nashed_Austria_14); nashed15.setText(R.string.nashed_Austria_15);
             nashed16.setText(R.string.nashed_Austria_16); nashed17.setText(R.string.nashed_Austria_17);
             nashed18.setText(R.string.nashed_Austria_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.west_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF3D00"))
                     .show();
             break;
         case 121:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.ukraine);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Ukraine_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Ukraine");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Ukraine");
             }
             nashed.setText(R.string.nashed_Ukraine); nashed1.setText(R.string.nashed_Ukraine_1);
             nashed2.setText(R.string.nashed_Ukraine_2); nashed3.setText(R.string.nashed_Ukraine_3);
             nashed4.setText(R.string.nashed_Ukraine_4); nashed5.setText(R.string.nashed_Ukraine_5);
             nashed6.setText(R.string.nashed_Ukraine_6); nashed7.setText(R.string.nashed_Ukraine_7);
             nashed8.setText(R.string.nashed_Ukraine_8); nashed9.setText(R.string.nashed_Ukraine_9);
             nashed10.setText(R.string.nashed_Ukraine_10); nashed11.setText(R.string.nashed_Ukraine_11);
             nashed12.setText(R.string.nashed_Ukraine_12); nashed13.setText(R.string.nashed_Ukraine_13);
             nashed14.setText(R.string.nashed_Ukraine_14); nashed15.setText(R.string.nashed_Ukraine_15);
             nashed16.setText(R.string.nashed_Ukraine_16); nashed17.setText(R.string.nashed_Ukraine_17);
             nashed18.setText(R.string.nashed_Ukraine_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#4E342E"))
                     .show();
             break;
         case 122:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.czech);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Czech_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Czech_Republic");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Czech_Republic");
             }
             nashed.setText(R.string.nashed_Czech); nashed1.setText(R.string.nashed_Czech_1);
             nashed2.setText(R.string.nashed_Czech_2); nashed3.setText(R.string.nashed_Czech_3);
             nashed4.setText(R.string.nashed_Czech_4); nashed5.setText(R.string.nashed_Czech_5);
             nashed6.setText(R.string.nashed_Czech_6); nashed7.setText(R.string.nashed_Czech_7);
             nashed8.setText(R.string.nashed_Czech_8); nashed9.setText(R.string.nashed_Czech_9);
             nashed10.setText(R.string.nashed_Czech_10); nashed11.setText(R.string.nashed_Czech_11);
             nashed12.setText(R.string.nashed_Czech_12); nashed13.setText(R.string.nashed_Czech_13);
             nashed14.setText(R.string.nashed_Czech_14); nashed15.setText(R.string.nashed_Czech_15);
             nashed16.setText(R.string.nashed_Czech_16); nashed17.setText(R.string.nashed_Czech_17);
             nashed18.setText(R.string.nashed_Czech_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#4E342E"))
                     .show();
             break;
         case 123:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.slovakia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Slovakia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Slovakia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Slovakia");
             }
             nashed.setText(R.string.nashed_Slovakia); nashed1.setText(R.string.nashed_Slovakia_1);
             nashed2.setText(R.string.nashed_Slovakia_2); nashed3.setText(R.string.nashed_Slovakia_3);
             nashed4.setText(R.string.nashed_Slovakia_4); nashed5.setText(R.string.nashed_Slovakia_5);
             nashed6.setText(R.string.nashed_Slovakia_6); nashed7.setText(R.string.nashed_Slovakia_7);
             nashed8.setText(R.string.nashed_Slovakia_8); nashed9.setText(R.string.nashed_Slovakia_9);
             nashed10.setText(R.string.nashed_Slovakia_10); nashed11.setText(R.string.nashed_Slovakia_11);
             nashed12.setText(R.string.nashed_Slovakia_12); nashed13.setText(R.string.nashed_Slovakia_13);
             nashed14.setText(R.string.nashed_Slovakia_14); nashed15.setText(R.string.nashed_Slovakia_15);
             nashed16.setText(R.string.nashed_Slovakia_16); nashed17.setText(R.string.nashed_Slovakia_17);
             nashed18.setText(R.string.nashed_Slovakia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#4E342E"))
                     .show();
             break;
         case 124:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.russia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Russia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Russia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Russia");
             }
             nashed.setText(R.string.nashed_Russia); nashed1.setText(R.string.nashed_Russia_1);
             nashed2.setText(R.string.nashed_Russia_2); nashed3.setText(R.string.nashed_Russia_3);
             nashed4.setText(R.string.nashed_Russia_4); nashed5.setText(R.string.nashed_Russia_5);
             nashed6.setText(R.string.nashed_Russia_6); nashed7.setText(R.string.nashed_Russia_7);
             nashed8.setText(R.string.nashed_Russia_8); nashed9.setText(R.string.nashed_Russia_9);
             nashed10.setText(R.string.nashed_Russia_10); nashed11.setText(R.string.nashed_Russia_11);
             nashed12.setText(R.string.nashed_Russia_12); nashed13.setText(R.string.nashed_Russia_13);
             nashed14.setText(R.string.nashed_Russia_14); nashed15.setText(R.string.nashed_Russia_15);
             nashed16.setText(R.string.nashed_Russia_16); nashed17.setText(R.string.nashed_Russia_17);
             nashed18.setText(R.string.nashed_Russia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#4E342E"))
                     .show();
             break;
         case 125:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.moldova);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Moldova_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Moldova");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Moldova");
             }
             nashed.setText(R.string.nashed_Moldova); nashed1.setText(R.string.nashed_Moldova_1);
             nashed2.setText(R.string.nashed_Moldova_2); nashed3.setText(R.string.nashed_Moldova_3);
             nashed4.setText(R.string.nashed_Moldova_4); nashed5.setText(R.string.nashed_Moldova_5);
             nashed6.setText(R.string.nashed_Moldova_6); nashed7.setText(R.string.nashed_Moldova_7);
             nashed8.setText(R.string.nashed_Moldova_8); nashed9.setText(R.string.nashed_Moldova_9);
             nashed10.setText(R.string.nashed_Moldova_10); nashed11.setText(R.string.nashed_Moldova_11);
             nashed12.setText(R.string.nashed_Moldova_12); nashed13.setText(R.string.nashed_Moldova_13);
             nashed14.setText(R.string.nashed_Moldova_14); nashed15.setText(R.string.nashed_Moldova_15);
             nashed16.setText(R.string.nashed_Moldova_16); nashed17.setText(R.string.nashed_Moldova_17);
             nashed18.setText(R.string.nashed_Moldova_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#4E342E"))
                     .show();
             break;
         case 126:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.poland);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Poland_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Poland");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Poland");
             }
             nashed.setText(R.string.nashed_Poland); nashed1.setText(R.string.nashed_Poland_1);
             nashed2.setText(R.string.nashed_Poland_2); nashed3.setText(R.string.nashed_Poland_3);
             nashed4.setText(R.string.nashed_Poland_4); nashed5.setText(R.string.nashed_Poland_5);
             nashed6.setText(R.string.nashed_Poland_6); nashed7.setText(R.string.nashed_Poland_7);
             nashed8.setText(R.string.nashed_Poland_8); nashed9.setText(R.string.nashed_Poland_9);
             nashed10.setText(R.string.nashed_Poland_10); nashed11.setText(R.string.nashed_Poland_11);
             nashed12.setText(R.string.nashed_Poland_12); nashed13.setText(R.string.nashed_Poland_13);
             nashed14.setText(R.string.nashed_Poland_14); nashed15.setText(R.string.nashed_Poland_15);
             nashed16.setText(R.string.nashed_Poland_16); nashed17.setText(R.string.nashed_Poland_17);
             nashed18.setText(R.string.nashed_Poland_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#4E342E"))
                     .show();
             break;
         case 127:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.romania);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Romania_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Romania");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Romania");
             }
             nashed.setText(R.string.nashed_Romania); nashed1.setText(R.string.nashed_Romania_1);
             nashed2.setText(R.string.nashed_Romania_2); nashed3.setText(R.string.nashed_Romania_3);
             nashed4.setText(R.string.nashed_Romania_4); nashed5.setText(R.string.nashed_Romania_5);
             nashed6.setText(R.string.nashed_Romania_6); nashed7.setText(R.string.nashed_Romania_7);
             nashed8.setText(R.string.nashed_Romania_8); nashed9.setText(R.string.nashed_Romania_9);
             nashed10.setText(R.string.nashed_Romania_10); nashed11.setText(R.string.nashed_Romania_11);
             nashed12.setText(R.string.nashed_Romania_12); nashed13.setText(R.string.nashed_Romania_13);
             nashed14.setText(R.string.nashed_Romania_14); nashed15.setText(R.string.nashed_Romania_15);
             nashed16.setText(R.string.nashed_Romania_16); nashed17.setText(R.string.nashed_Romania_17);
             nashed18.setText(R.string.nashed_Romania_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#4E342E"))
                     .show();
             break;
         case 128:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.bulgaria);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Bulgaria_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Bulgaria");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Bulgaria");
             }
             nashed.setText(R.string.nashed_Bulgaria); nashed1.setText(R.string.nashed_Bulgaria_1);
             nashed2.setText(R.string.nashed_Bulgaria_2); nashed3.setText(R.string.nashed_Bulgaria_3);
             nashed4.setText(R.string.nashed_Bulgaria_4); nashed5.setText(R.string.nashed_Bulgaria_5);
             nashed6.setText(R.string.nashed_Bulgaria_6); nashed7.setText(R.string.nashed_Bulgaria_7);
             nashed8.setText(R.string.nashed_Bulgaria_8); nashed9.setText(R.string.nashed_Bulgaria_9);
             nashed10.setText(R.string.nashed_Bulgaria_10); nashed11.setText(R.string.nashed_Bulgaria_11);
             nashed12.setText(R.string.nashed_Bulgaria_12); nashed13.setText(R.string.nashed_Bulgaria_13);
             nashed14.setText(R.string.nashed_Bulgaria_14); nashed15.setText(R.string.nashed_Bulgaria_15);
             nashed16.setText(R.string.nashed_Bulgaria_16); nashed17.setText(R.string.nashed_Bulgaria_17);
             nashed18.setText(R.string.nashed_Bulgaria_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#4E342E"))
                     .show();
             break;
         case 129:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.hungary);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Hungary_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Hungary");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Hungary");
             }
             nashed.setText(R.string.nashed_Hungary); nashed1.setText(R.string.nashed_Hungary_1);
             nashed2.setText(R.string.nashed_Hungary_2); nashed3.setText(R.string.nashed_Hungary_3);
             nashed4.setText(R.string.nashed_Hungary_4); nashed5.setText(R.string.nashed_Hungary_5);
             nashed6.setText(R.string.nashed_Hungary_6); nashed7.setText(R.string.nashed_Hungary_7);
             nashed8.setText(R.string.nashed_Hungary_8); nashed9.setText(R.string.nashed_Hungary_9);
             nashed10.setText(R.string.nashed_Hungary_10); nashed11.setText(R.string.nashed_Hungary_11);
             nashed12.setText(R.string.nashed_Hungary_12); nashed13.setText(R.string.nashed_Hungary_13);
             nashed14.setText(R.string.nashed_Hungary_14); nashed15.setText(R.string.nashed_Hungary_15);
             nashed16.setText(R.string.nashed_Hungary_16); nashed17.setText(R.string.nashed_Hungary_17);
             nashed18.setText(R.string.nashed_Hungary_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.east_europe))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#4E342E"))
                     .show();
             break;
         case 130:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.canada);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Canada_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Canada");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Canada");
             }
             nashed.setText(R.string.nashed_Canada); nashed1.setText(R.string.nashed_Canada_1);
             nashed2.setText(R.string.nashed_Canada_2); nashed3.setText(R.string.nashed_Canada_3);
             nashed4.setText(R.string.nashed_Canada_4); nashed5.setText(R.string.nashed_Canada_5);
             nashed6.setText(R.string.nashed_Canada_6); nashed7.setText(R.string.nashed_Canada_7);
             nashed8.setText(R.string.nashed_Canada_8); nashed9.setText(R.string.nashed_Canada_9);
             nashed10.setText(R.string.nashed_Canada_10); nashed11.setText(R.string.nashed_Canada_11);
             nashed12.setText(R.string.nashed_Canada_12); nashed13.setText(R.string.nashed_Canada_13);
             nashed14.setText(R.string.nashed_Canada_14); nashed15.setText(R.string.nashed_Canada_15);
             nashed16.setText(R.string.nashed_Canada_16); nashed17.setText(R.string.nashed_Canada_17);
             nashed18.setText(R.string.nashed_Canada_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#616161"))
                     .show();
             break;
         case 131:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.mexico);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Mexico_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Mexico");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Mexico");
             }
             nashed.setText(R.string.nashed_Mexico); nashed1.setText(R.string.nashed_Mexico_1);
             nashed2.setText(R.string.nashed_Mexico_2); nashed3.setText(R.string.nashed_Mexico_3);
             nashed4.setText(R.string.nashed_Mexico_4); nashed5.setText(R.string.nashed_Mexico_5);
             nashed6.setText(R.string.nashed_Mexico_6); nashed7.setText(R.string.nashed_Mexico_7);
             nashed8.setText(R.string.nashed_Mexico_8); nashed9.setText(R.string.nashed_Mexico_9);
             nashed10.setText(R.string.nashed_Mexico_10); nashed11.setText(R.string.nashed_Mexico_11);
             nashed12.setText(R.string.nashed_Mexico_12); nashed13.setText(R.string.nashed_Mexico_13);
             nashed14.setText(R.string.nashed_Mexico_14); nashed15.setText(R.string.nashed_Mexico_15);
             nashed16.setText(R.string.nashed_Mexico_16); nashed17.setText(R.string.nashed_Mexico_17);
             nashed18.setText(R.string.nashed_Mexico_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#616161"))
                     .show();
             break;
         case 132:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.usa);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Usa_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/United_States");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/United_States");
             }
             nashed.setText(R.string.nashed_Usa); nashed1.setText(R.string.nashed_Usa_1);
             nashed2.setText(R.string.nashed_Usa_2); nashed3.setText(R.string.nashed_Usa_3);
             nashed4.setText(R.string.nashed_Usa_4); nashed5.setText(R.string.nashed_Usa_5);
             nashed6.setText(R.string.nashed_Usa_6); nashed7.setText(R.string.nashed_Usa_7);
             nashed8.setText(R.string.nashed_Usa_8); nashed9.setText(R.string.nashed_Usa_9);
             nashed10.setText(R.string.nashed_Usa_10); nashed11.setText(R.string.nashed_Usa_11);
             nashed12.setText(R.string.nashed_Usa_12); nashed13.setText(R.string.nashed_Usa_13);
             nashed14.setText(R.string.nashed_Usa_14); nashed15.setText(R.string.nashed_Usa_15);
             nashed16.setText(R.string.nashed_Usa_16); nashed17.setText(R.string.nashed_Usa_17);
             nashed18.setText(R.string.nashed_Usa_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#616161"))
                     .show();
             break;
         case 133:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.antigua_and_barbuda);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.A_b_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Antigua_and_Barbuda");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Antigua_and_Barbuda");
             }
             nashed.setText(R.string.nashed_A_b); nashed1.setText(R.string.nashed_A_b_1);
             nashed2.setText(R.string.nashed_A_b_2); nashed3.setText(R.string.nashed_A_b_3);
             nashed4.setText(R.string.nashed_A_b_4); nashed5.setText(R.string.nashed_A_b_5);
             nashed6.setText(R.string.nashed_A_b_6); nashed7.setText(R.string.nashed_A_b_7);
             nashed8.setText(R.string.nashed_A_b_8); nashed9.setText(R.string.nashed_A_b_9);
             nashed10.setText(R.string.nashed_A_b_10); nashed11.setText(R.string.nashed_A_b_11);
             nashed12.setText(R.string.nashed_A_b_12); nashed13.setText(R.string.nashed_A_b_13);
             nashed14.setText(R.string.nashed_A_b_14); nashed15.setText(R.string.nashed_A_b_15);
             nashed16.setText(R.string.nashed_A_b_16); nashed17.setText(R.string.nashed_A_b_17);
             nashed18.setText(R.string.nashed_A_b_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 134:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.bahamas);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Bahamas_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Bahamas");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Bahamas");
             }
             nashed.setText(R.string.nashed_Bahamas); nashed1.setText(R.string.nashed_Bahamas_1);
             nashed2.setText(R.string.nashed_Bahamas_2); nashed3.setText(R.string.nashed_Bahamas_3);
             nashed4.setText(R.string.nashed_Bahamas_4); nashed5.setText(R.string.nashed_Bahamas_5);
             nashed6.setText(R.string.nashed_Bahamas_6); nashed7.setText(R.string.nashed_Bahamas_7);
             nashed8.setText(R.string.nashed_Bahamas_8); nashed9.setText(R.string.nashed_Bahamas_9);
             nashed10.setText(R.string.nashed_Bahamas_10); nashed11.setText(R.string.nashed_Bahamas_11);
             nashed12.setText(R.string.nashed_Bahamas_12); nashed13.setText(R.string.nashed_Bahamas_13);
             nashed14.setText(R.string.nashed_Bahamas_14); nashed15.setText(R.string.nashed_Bahamas_15);
             nashed16.setText(R.string.nashed_Bahamas_16); nashed17.setText(R.string.nashed_Bahamas_17);
             nashed18.setText(R.string.nashed_Bahamas_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 135:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.barbados);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Barbados_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Barbados");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Barbados");
             }
             nashed.setText(R.string.nashed_Barbados); nashed1.setText(R.string.nashed_Barbados_1);
             nashed2.setText(R.string.nashed_Barbados_2); nashed3.setText(R.string.nashed_Barbados_3);
             nashed4.setText(R.string.nashed_Barbados_4); nashed5.setText(R.string.nashed_Barbados_5);
             nashed6.setText(R.string.nashed_Barbados_6); nashed7.setText(R.string.nashed_Barbados_7);
             nashed8.setText(R.string.nashed_Barbados_8); nashed9.setText(R.string.nashed_Barbados_9);
             nashed10.setText(R.string.nashed_Barbados_10); nashed11.setText(R.string.nashed_Barbados_11);
             nashed12.setText(R.string.nashed_Barbados_12); nashed13.setText(R.string.nashed_Barbados_13);
             nashed14.setText(R.string.nashed_Barbados_14); nashed15.setText(R.string.nashed_Barbados_15);
             nashed16.setText(R.string.nashed_Barbados_16); nashed17.setText(R.string.nashed_Barbados_17);
             nashed18.setText(R.string.nashed_Barbados_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 136:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.cuba);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Cuba_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Cuba");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Cuba");
             }
             nashed.setText(R.string.nashed_Cuba); nashed1.setText(R.string.nashed_Cuba_1);
             nashed2.setText(R.string.nashed_Cuba_2); nashed3.setText(R.string.nashed_Cuba_3);
             nashed4.setText(R.string.nashed_Cuba_4); nashed5.setText(R.string.nashed_Cuba_5);
             nashed6.setText(R.string.nashed_Cuba_6); nashed7.setText(R.string.nashed_Cuba_7);
             nashed8.setText(R.string.nashed_Cuba_8); nashed9.setText(R.string.nashed_Cuba_9);
             nashed10.setText(R.string.nashed_Cuba_10); nashed11.setText(R.string.nashed_Cuba_11);
             nashed12.setText(R.string.nashed_Cuba_12); nashed13.setText(R.string.nashed_Cuba_13);
             nashed14.setText(R.string.nashed_Cuba_14); nashed15.setText(R.string.nashed_Cuba_15);
             nashed16.setText(R.string.nashed_Cuba_16); nashed17.setText(R.string.nashed_Cuba_17);
             nashed18.setText(R.string.nashed_Cuba_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 137:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.dominica);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Dominica_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Dominica");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Dominica");
             }
             nashed.setText(R.string.nashed_Dominica); nashed1.setText(R.string.nashed_Dominica_1);
             nashed2.setText(R.string.nashed_Dominica_2); nashed3.setText(R.string.nashed_Dominica_3);
             nashed4.setText(R.string.nashed_Dominica_4); nashed5.setText(R.string.nashed_Dominica_5);
             nashed6.setText(R.string.nashed_Dominica_6); nashed7.setText(R.string.nashed_Dominica_7);
             nashed8.setText(R.string.nashed_Dominica_8); nashed9.setText(R.string.nashed_Dominica_9);
             nashed10.setText(R.string.nashed_Dominica_10); nashed11.setText(R.string.nashed_Dominica_11);
             nashed12.setText(R.string.nashed_Dominica_12); nashed13.setText(R.string.nashed_Dominica_13);
             nashed14.setText(R.string.nashed_Dominica_14); nashed15.setText(R.string.nashed_Dominica_15);
             nashed16.setText(R.string.nashed_Dominica_16); nashed17.setText(R.string.nashed_Dominica_17);
             nashed18.setText(R.string.nashed_Dominica_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 138:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.dominican);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Dominican_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Dominican_Republic");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Dominican_Republic");
             }
             nashed.setText(R.string.nashed_Dominican); nashed1.setText(R.string.nashed_Dominican_1);
             nashed2.setText(R.string.nashed_Dominican_2); nashed3.setText(R.string.nashed_Dominican_3);
             nashed4.setText(R.string.nashed_Dominican_4); nashed5.setText(R.string.nashed_Dominican_5);
             nashed6.setText(R.string.nashed_Dominican_6); nashed7.setText(R.string.nashed_Dominican_7);
             nashed8.setText(R.string.nashed_Dominican_8); nashed9.setText(R.string.nashed_Dominican_9);
             nashed10.setText(R.string.nashed_Dominican_10); nashed11.setText(R.string.nashed_Dominican_11);
             nashed12.setText(R.string.nashed_Dominican_12); nashed13.setText(R.string.nashed_Dominican_13);
             nashed14.setText(R.string.nashed_Dominican_14); nashed15.setText(R.string.nashed_Dominican_15);
             nashed16.setText(R.string.nashed_Dominican_16); nashed17.setText(R.string.nashed_Dominican_17);
             nashed18.setText(R.string.nashed_Dominican_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
           case 139:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.grenada);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Grenada_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Grenada");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Grenada");
             }
             nashed.setText(R.string.nashed_Grenada); nashed1.setText(R.string.nashed_Grenada_1);
             nashed2.setText(R.string.nashed_Grenada_2); nashed3.setText(R.string.nashed_Grenada_3);
             nashed4.setText(R.string.nashed_Grenada_4); nashed5.setText(R.string.nashed_Grenada_5);
             nashed6.setText(R.string.nashed_Grenada_6); nashed7.setText(R.string.nashed_Grenada_7);
             nashed8.setText(R.string.nashed_Grenada_8); nashed9.setText(R.string.nashed_Grenada_9);
             nashed10.setText(R.string.nashed_Grenada_10); nashed11.setText(R.string.nashed_Grenada_11);
             nashed12.setText(R.string.nashed_Grenada_12); nashed13.setText(R.string.nashed_Grenada_13);
             nashed14.setText(R.string.nashed_Grenada_14); nashed15.setText(R.string.nashed_Grenada_15);
             nashed16.setText(R.string.nashed_Grenada_16); nashed17.setText(R.string.nashed_Grenada_17);
             nashed18.setText(R.string.nashed_Grenada_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 140:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.haiti);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Haiti_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Haiti");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Haiti");
             }
             nashed.setText(R.string.nashed_Haiti); nashed1.setText(R.string.nashed_Haiti_1);
             nashed2.setText(R.string.nashed_Haiti_2); nashed3.setText(R.string.nashed_Haiti_3);
             nashed4.setText(R.string.nashed_Haiti_4); nashed5.setText(R.string.nashed_Haiti_5);
             nashed6.setText(R.string.nashed_Haiti_6); nashed7.setText(R.string.nashed_Haiti_7);
             nashed8.setText(R.string.nashed_Haiti_8); nashed9.setText(R.string.nashed_Haiti_9);
             nashed10.setText(R.string.nashed_Haiti_10); nashed11.setText(R.string.nashed_Haiti_11);
             nashed12.setText(R.string.nashed_Haiti_12); nashed13.setText(R.string.nashed_Haiti_13);
             nashed14.setText(R.string.nashed_Haiti_14); nashed15.setText(R.string.nashed_Haiti_15);
             nashed16.setText(R.string.nashed_Haiti_16); nashed17.setText(R.string.nashed_Haiti_17);
             nashed18.setText(R.string.nashed_Haiti_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 141:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.jamaica);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Jamaica_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Jamaica");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Jamaica");
             }
             nashed.setText(R.string.nashed_Jamaica); nashed1.setText(R.string.nashed_Jamaica_1);
             nashed2.setText(R.string.nashed_Jamaica_2); nashed3.setText(R.string.nashed_Jamaica_3);
             nashed4.setText(R.string.nashed_Jamaica_4); nashed5.setText(R.string.nashed_Jamaica_5);
             nashed6.setText(R.string.nashed_Jamaica_6); nashed7.setText(R.string.nashed_Jamaica_7);
             nashed8.setText(R.string.nashed_Jamaica_8); nashed9.setText(R.string.nashed_Jamaica_9);
             nashed10.setText(R.string.nashed_Jamaica_10); nashed11.setText(R.string.nashed_Jamaica_11);
             nashed12.setText(R.string.nashed_Jamaica_12); nashed13.setText(R.string.nashed_Jamaica_13);
             nashed14.setText(R.string.nashed_Jamaica_14); nashed15.setText(R.string.nashed_Jamaica_15);
             nashed16.setText(R.string.nashed_Jamaica_16); nashed17.setText(R.string.nashed_Jamaica_17);
             nashed18.setText(R.string.nashed_Jamaica_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 142:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.saint_kitts_and_nevis);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.S_k_n_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Saint_Kitts_and_Nevis");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Saint_Kitts_and_Nevis");
             }
             nashed.setText(R.string.nashed_S_k_n); nashed1.setText(R.string.nashed_S_k_n_1);
             nashed2.setText(R.string.nashed_S_k_n_2); nashed3.setText(R.string.nashed_S_k_n_3);
             nashed4.setText(R.string.nashed_S_k_n_4); nashed5.setText(R.string.nashed_S_k_n_5);
             nashed6.setText(R.string.nashed_S_k_n_6); nashed7.setText(R.string.nashed_S_k_n_7);
             nashed8.setText(R.string.nashed_S_k_n_8); nashed9.setText(R.string.nashed_S_k_n_9);
             nashed10.setText(R.string.nashed_S_k_n_10); nashed11.setText(R.string.nashed_S_k_n_11);
             nashed12.setText(R.string.nashed_S_k_n_12); nashed13.setText(R.string.nashed_S_k_n_13);
             nashed14.setText(R.string.nashed_S_k_n_14); nashed15.setText(R.string.nashed_S_k_n_15);
             nashed16.setText(R.string.nashed_S_k_n_16); nashed17.setText(R.string.nashed_S_k_n_17);
             nashed18.setText(R.string.nashed_S_k_n_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 143:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.saint_lucia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.S_l_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Saint_Lucia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Saint_Lucia");
             }
             nashed.setText(R.string.nashed_S_l); nashed1.setText(R.string.nashed_S_l_1);
             nashed2.setText(R.string.nashed_S_l_2); nashed3.setText(R.string.nashed_S_l_3);
             nashed4.setText(R.string.nashed_S_l_4); nashed5.setText(R.string.nashed_S_l_5);
             nashed6.setText(R.string.nashed_S_l_6); nashed7.setText(R.string.nashed_S_l_7);
             nashed8.setText(R.string.nashed_S_l_8); nashed9.setText(R.string.nashed_S_l_9);
             nashed10.setText(R.string.nashed_S_l_10); nashed11.setText(R.string.nashed_S_l_11);
             nashed12.setText(R.string.nashed_S_l_12); nashed13.setText(R.string.nashed_S_l_13);
             nashed14.setText(R.string.nashed_S_l_14); nashed15.setText(R.string.nashed_S_l_15);
             nashed16.setText(R.string.nashed_S_l_16); nashed17.setText(R.string.nashed_S_l_17);
             nashed18.setText(R.string.nashed_S_l_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 144:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.saint_vincent_and_the_grenadines);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.S_v_g_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Saint_Vincent_and_the_Grenadines");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Saint_Vincent_and_the_Grenadines");
             }
             nashed.setText(R.string.nashed_S_v_g); nashed1.setText(R.string.nashed_S_v_g_1);
             nashed2.setText(R.string.nashed_S_v_g_2); nashed3.setText(R.string.nashed_S_v_g_3);
             nashed4.setText(R.string.nashed_S_v_g_4); nashed5.setText(R.string.nashed_S_v_g_5);
             nashed6.setText(R.string.nashed_S_v_g_6); nashed7.setText(R.string.nashed_S_v_g_7);
             nashed8.setText(R.string.nashed_S_v_g_8); nashed9.setText(R.string.nashed_S_v_g_9);
             nashed10.setText(R.string.nashed_S_v_g_10); nashed11.setText(R.string.nashed_S_v_g_11);
             nashed12.setText(R.string.nashed_S_v_g_12); nashed13.setText(R.string.nashed_S_v_g_13);
             nashed14.setText(R.string.nashed_S_v_g_14); nashed15.setText(R.string.nashed_S_v_g_15);
             nashed16.setText(R.string.nashed_S_v_g_16); nashed17.setText(R.string.nashed_S_v_g_17);
             nashed18.setText(R.string.nashed_S_v_g_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 145:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.trinidad_and_tobago);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.T_t_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Trinidad_and_Tobago");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Trinidad_and_Tobago");
             }
             nashed.setText(R.string.nashed_T_t); nashed1.setText(R.string.nashed_T_t_1);
             nashed2.setText(R.string.nashed_T_t_2); nashed3.setText(R.string.nashed_T_t_3);
             nashed4.setText(R.string.nashed_T_t_4); nashed5.setText(R.string.nashed_T_t_5);
             nashed6.setText(R.string.nashed_T_t_6); nashed7.setText(R.string.nashed_T_t_7);
             nashed8.setText(R.string.nashed_T_t_8); nashed9.setText(R.string.nashed_T_t_9);
             nashed10.setText(R.string.nashed_T_t_10); nashed11.setText(R.string.nashed_T_t_11);
             nashed12.setText(R.string.nashed_T_t_12); nashed13.setText(R.string.nashed_T_t_13);
             nashed14.setText(R.string.nashed_T_t_14); nashed15.setText(R.string.nashed_T_t_15);
             nashed16.setText(R.string.nashed_T_t_16); nashed17.setText(R.string.nashed_T_t_17);
             nashed18.setText(R.string.nashed_T_t_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.north_amirca_gzr))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#37474F"))
                     .show();
             break;
         case 146:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.belize);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Belize_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Belize");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Belize");
             }
             nashed.setText(R.string.nashed_Belize); nashed1.setText(R.string.nashed_Belize_1);
             nashed2.setText(R.string.nashed_Belize_2); nashed3.setText(R.string.nashed_Belize_3);
             nashed4.setText(R.string.nashed_Belize_4); nashed5.setText(R.string.nashed_Belize_5);
             nashed6.setText(R.string.nashed_Belize_6); nashed7.setText(R.string.nashed_Belize_7);
             nashed8.setText(R.string.nashed_Belize_8); nashed9.setText(R.string.nashed_Belize_9);
             nashed10.setText(R.string.nashed_Belize_10); nashed11.setText(R.string.nashed_Belize_11);
             nashed12.setText(R.string.nashed_Belize_12); nashed13.setText(R.string.nashed_Belize_13);
             nashed14.setText(R.string.nashed_Belize_14); nashed15.setText(R.string.nashed_Belize_15);
             nashed16.setText(R.string.nashed_Belize_16); nashed17.setText(R.string.nashed_Belize_17);
             nashed18.setText(R.string.nashed_Belize_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 147:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.costa_rica);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Costa_Rica_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Costa_Rica");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Costa_Rica");
             }
             nashed.setText(R.string.nashed_Costa_Rica); nashed1.setText(R.string.nashed_Costa_Rica_1);
             nashed2.setText(R.string.nashed_Costa_Rica_2); nashed3.setText(R.string.nashed_Costa_Rica_3);
             nashed4.setText(R.string.nashed_Costa_Rica_4); nashed5.setText(R.string.nashed_Costa_Rica_5);
             nashed6.setText(R.string.nashed_Costa_Rica_6); nashed7.setText(R.string.nashed_Costa_Rica_7);
             nashed8.setText(R.string.nashed_Costa_Rica_8); nashed9.setText(R.string.nashed_Costa_Rica_9);
             nashed10.setText(R.string.nashed_Costa_Rica_10); nashed11.setText(R.string.nashed_Costa_Rica_11);
             nashed12.setText(R.string.nashed_Costa_Rica_12); nashed13.setText(R.string.nashed_Costa_Rica_13);
             nashed14.setText(R.string.nashed_Costa_Rica_14); nashed15.setText(R.string.nashed_Costa_Rica_15);
             nashed16.setText(R.string.nashed_Costa_Rica_16); nashed17.setText(R.string.nashed_Costa_Rica_17);
             nashed18.setText(R.string.nashed_Costa_Rica_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 148:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.el_salvador);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.El_Salvador_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/El_Salvador");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/El_Salvador");
             }
             nashed.setText(R.string.nashed_El_Salvador); nashed1.setText(R.string.nashed_El_Salvador_1);
             nashed2.setText(R.string.nashed_El_Salvador_2); nashed3.setText(R.string.nashed_El_Salvador_3);
             nashed4.setText(R.string.nashed_El_Salvador_4); nashed5.setText(R.string.nashed_El_Salvador_5);
             nashed6.setText(R.string.nashed_El_Salvador_6); nashed7.setText(R.string.nashed_El_Salvador_7);
             nashed8.setText(R.string.nashed_El_Salvador_8); nashed9.setText(R.string.nashed_El_Salvador_9);
             nashed10.setText(R.string.nashed_El_Salvador_10); nashed11.setText(R.string.nashed_El_Salvador_11);
             nashed12.setText(R.string.nashed_El_Salvador_12); nashed13.setText(R.string.nashed_El_Salvador_13);
             nashed14.setText(R.string.nashed_El_Salvador_14); nashed15.setText(R.string.nashed_El_Salvador_15);
             nashed16.setText(R.string.nashed_El_Salvador_16); nashed17.setText(R.string.nashed_El_Salvador_17);
             nashed18.setText(R.string.nashed_El_Salvador_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 149:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.guatemala);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Guatemala_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Guatemala");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Guatemala");
             }
             nashed.setText(R.string.nashed_Guatemala); nashed1.setText(R.string.nashed_Guatemala_1);
             nashed2.setText(R.string.nashed_Guatemala_2); nashed3.setText(R.string.nashed_Guatemala_3);
             nashed4.setText(R.string.nashed_Guatemala_4); nashed5.setText(R.string.nashed_Guatemala_5);
             nashed6.setText(R.string.nashed_Guatemala_6); nashed7.setText(R.string.nashed_Guatemala_7);
             nashed8.setText(R.string.nashed_Guatemala_8); nashed9.setText(R.string.nashed_Guatemala_9);
             nashed10.setText(R.string.nashed_Guatemala_10); nashed11.setText(R.string.nashed_Guatemala_11);
             nashed12.setText(R.string.nashed_Guatemala_12); nashed13.setText(R.string.nashed_Guatemala_13);
             nashed14.setText(R.string.nashed_Guatemala_14); nashed15.setText(R.string.nashed_Guatemala_15);
             nashed16.setText(R.string.nashed_Guatemala_16); nashed17.setText(R.string.nashed_Guatemala_17);
             nashed18.setText(R.string.nashed_Guatemala_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 150:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.honduras);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Honduras_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Honduras");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Honduras");
             }
             nashed.setText(R.string.nashed_Honduras); nashed1.setText(R.string.nashed_Honduras_1);
             nashed2.setText(R.string.nashed_Honduras_2); nashed3.setText(R.string.nashed_Honduras_3);
             nashed4.setText(R.string.nashed_Honduras_4); nashed5.setText(R.string.nashed_Honduras_5);
             nashed6.setText(R.string.nashed_Honduras_6); nashed7.setText(R.string.nashed_Honduras_7);
             nashed8.setText(R.string.nashed_Honduras_8); nashed9.setText(R.string.nashed_Honduras_9);
             nashed10.setText(R.string.nashed_Honduras_10); nashed11.setText(R.string.nashed_Honduras_11);
             nashed12.setText(R.string.nashed_Honduras_12); nashed13.setText(R.string.nashed_Honduras_13);
             nashed14.setText(R.string.nashed_Honduras_14); nashed15.setText(R.string.nashed_Honduras_15);
             nashed16.setText(R.string.nashed_Honduras_16); nashed17.setText(R.string.nashed_Honduras_17);
             nashed18.setText(R.string.nashed_Honduras_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 151:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.nicaragua);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Nicaragua_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Nicaragua");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Nicaragua");
             }
             nashed.setText(R.string.nashed_Nicaragua); nashed1.setText(R.string.nashed_Nicaragua_1);
             nashed2.setText(R.string.nashed_Nicaragua_2); nashed3.setText(R.string.nashed_Nicaragua_3);
             nashed4.setText(R.string.nashed_Nicaragua_4); nashed5.setText(R.string.nashed_Nicaragua_5);
             nashed6.setText(R.string.nashed_Nicaragua_6); nashed7.setText(R.string.nashed_Nicaragua_7);
             nashed8.setText(R.string.nashed_Nicaragua_8); nashed9.setText(R.string.nashed_Nicaragua_9);
             nashed10.setText(R.string.nashed_Nicaragua_10); nashed11.setText(R.string.nashed_Nicaragua_11);
             nashed12.setText(R.string.nashed_Nicaragua_12); nashed13.setText(R.string.nashed_Nicaragua_13);
             nashed14.setText(R.string.nashed_Nicaragua_14); nashed15.setText(R.string.nashed_Nicaragua_15);
             nashed16.setText(R.string.nashed_Nicaragua_16); nashed17.setText(R.string.nashed_Nicaragua_17);
             nashed18.setText(R.string.nashed_Nicaragua_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 152:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.panama);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Panama_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Panama");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Panama");
             }
             nashed.setText(R.string.nashed_Panama); nashed1.setText(R.string.nashed_Panama_1);
             nashed2.setText(R.string.nashed_Panama_2); nashed3.setText(R.string.nashed_Panama_3);
             nashed4.setText(R.string.nashed_Panama_4); nashed5.setText(R.string.nashed_Panama_5);
             nashed6.setText(R.string.nashed_Panama_6); nashed7.setText(R.string.nashed_Panama_7);
             nashed8.setText(R.string.nashed_Panama_8); nashed9.setText(R.string.nashed_Panama_9);
             nashed10.setText(R.string.nashed_Panama_10); nashed11.setText(R.string.nashed_Panama_11);
             nashed12.setText(R.string.nashed_Panama_12); nashed13.setText(R.string.nashed_Panama_13);
             nashed14.setText(R.string.nashed_Panama_14); nashed15.setText(R.string.nashed_Panama_15);
             nashed16.setText(R.string.nashed_Panama_16); nashed17.setText(R.string.nashed_Panama_17);
             nashed18.setText(R.string.nashed_Panama_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.meddil_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF1744"))
                     .show();
             break;
         case 153:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.argentina);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Argentina_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Argentina");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Argentina");
             }
             nashed.setText(R.string.nashed_Argentina); nashed1.setText(R.string.nashed_Argentina_1);
             nashed2.setText(R.string.nashed_Argentina_2); nashed3.setText(R.string.nashed_Argentina_3);
             nashed4.setText(R.string.nashed_Argentina_4); nashed5.setText(R.string.nashed_Argentina_5);
             nashed6.setText(R.string.nashed_Argentina_6); nashed7.setText(R.string.nashed_Argentina_7);
             nashed8.setText(R.string.nashed_Argentina_8); nashed9.setText(R.string.nashed_Argentina_9);
             nashed10.setText(R.string.nashed_Argentina_10); nashed11.setText(R.string.nashed_Argentina_11);
             nashed12.setText(R.string.nashed_Argentina_12); nashed13.setText(R.string.nashed_Argentina_13);
             nashed14.setText(R.string.nashed_Argentina_14); nashed15.setText(R.string.nashed_Argentina_15);
             nashed16.setText(R.string.nashed_Argentina_16); nashed17.setText(R.string.nashed_Argentina_17);
             nashed18.setText(R.string.nashed_Argentina_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF4081"))
                     .show();
             break;
         case 154:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.brazil);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Brazil_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Brazil");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Brazil");
             }
             nashed.setText(R.string.nashed_Brazil); nashed1.setText(R.string.nashed_Brazil_1);
             nashed2.setText(R.string.nashed_Brazil_2); nashed3.setText(R.string.nashed_Brazil_3);
             nashed4.setText(R.string.nashed_Brazil_4); nashed5.setText(R.string.nashed_Brazil_5);
             nashed6.setText(R.string.nashed_Brazil_6); nashed7.setText(R.string.nashed_Brazil_7);
             nashed8.setText(R.string.nashed_Brazil_8); nashed9.setText(R.string.nashed_Brazil_9);
             nashed10.setText(R.string.nashed_Brazil_10); nashed11.setText(R.string.nashed_Brazil_11);
             nashed12.setText(R.string.nashed_Brazil_12); nashed13.setText(R.string.nashed_Brazil_13);
             nashed14.setText(R.string.nashed_Brazil_14); nashed15.setText(R.string.nashed_Brazil_15);
             nashed16.setText(R.string.nashed_Brazil_16); nashed17.setText(R.string.nashed_Brazil_17);
             nashed18.setText(R.string.nashed_Brazil_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF4081"))
                     .show();
             break;
         case 155:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.bolivia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Bolivia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Bolivia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Bolivia");
             }
             nashed.setText(R.string.nashed_Bolivia); nashed1.setText(R.string.nashed_Bolivia_1);
             nashed2.setText(R.string.nashed_Bolivia_2); nashed3.setText(R.string.nashed_Bolivia_3);
             nashed4.setText(R.string.nashed_Bolivia_4); nashed5.setText(R.string.nashed_Bolivia_5);
             nashed6.setText(R.string.nashed_Bolivia_6); nashed7.setText(R.string.nashed_Bolivia_7);
             nashed8.setText(R.string.nashed_Bolivia_8); nashed9.setText(R.string.nashed_Bolivia_9);
             nashed10.setText(R.string.nashed_Bolivia_10); nashed11.setText(R.string.nashed_Bolivia_11);
             nashed12.setText(R.string.nashed_Bolivia_12); nashed13.setText(R.string.nashed_Bolivia_13);
             nashed14.setText(R.string.nashed_Bolivia_14); nashed15.setText(R.string.nashed_Bolivia_15);
             nashed16.setText(R.string.nashed_Bolivia_16); nashed17.setText(R.string.nashed_Bolivia_17);
             nashed18.setText(R.string.nashed_Bolivia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF4081"))
                     .show();
             break;
         case 156:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.colombia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Colombia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Colombia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Colombia");
             }
             nashed.setText(R.string.nashed_Colombia); nashed1.setText(R.string.nashed_Colombia_1);
             nashed2.setText(R.string.nashed_Colombia_2); nashed3.setText(R.string.nashed_Colombia_3);
             nashed4.setText(R.string.nashed_Colombia_4); nashed5.setText(R.string.nashed_Colombia_5);
             nashed6.setText(R.string.nashed_Colombia_6); nashed7.setText(R.string.nashed_Colombia_7);
             nashed8.setText(R.string.nashed_Colombia_8); nashed9.setText(R.string.nashed_Colombia_9);
             nashed10.setText(R.string.nashed_Colombia_10); nashed11.setText(R.string.nashed_Colombia_11);
             nashed12.setText(R.string.nashed_Colombia_12); nashed13.setText(R.string.nashed_Colombia_13);
             nashed14.setText(R.string.nashed_Colombia_14); nashed15.setText(R.string.nashed_Colombia_15);
             nashed16.setText(R.string.nashed_Colombia_16); nashed17.setText(R.string.nashed_Colombia_17);
             nashed18.setText(R.string.nashed_Colombia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF4081"))
                     .show();
             break;
         case 157:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.chile);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Chile_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Chile");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Chile");
             }
             nashed.setText(R.string.nashed_Chile); nashed1.setText(R.string.nashed_Chile_1);
             nashed2.setText(R.string.nashed_Chile_2); nashed3.setText(R.string.nashed_Chile_3);
             nashed4.setText(R.string.nashed_Chile_4); nashed5.setText(R.string.nashed_Chile_5);
             nashed6.setText(R.string.nashed_Chile_6); nashed7.setText(R.string.nashed_Chile_7);
             nashed8.setText(R.string.nashed_Chile_8); nashed9.setText(R.string.nashed_Chile_9);
             nashed10.setText(R.string.nashed_Chile_10); nashed11.setText(R.string.nashed_Chile_11);
             nashed12.setText(R.string.nashed_Chile_12); nashed13.setText(R.string.nashed_Chile_13);
             nashed14.setText(R.string.nashed_Chile_14); nashed15.setText(R.string.nashed_Chile_15);
             nashed16.setText(R.string.nashed_Chile_16); nashed17.setText(R.string.nashed_Chile_17);
             nashed18.setText(R.string.nashed_Chile_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF4081"))
                     .show();
             break;
         case 158:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.ecuador);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Ecuador_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Ecuador");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Ecuador");
             }
             nashed.setText(R.string.nashed_Ecuador); nashed1.setText(R.string.nashed_Ecuador_1);
             nashed2.setText(R.string.nashed_Ecuador_2); nashed3.setText(R.string.nashed_Ecuador_3);
             nashed4.setText(R.string.nashed_Ecuador_4); nashed5.setText(R.string.nashed_Ecuador_5);
             nashed6.setText(R.string.nashed_Ecuador_6); nashed7.setText(R.string.nashed_Ecuador_7);
             nashed8.setText(R.string.nashed_Ecuador_8); nashed9.setText(R.string.nashed_Ecuador_9);
             nashed10.setText(R.string.nashed_Ecuador_10); nashed11.setText(R.string.nashed_Ecuador_11);
             nashed12.setText(R.string.nashed_Ecuador_12); nashed13.setText(R.string.nashed_Ecuador_13);
             nashed14.setText(R.string.nashed_Ecuador_14); nashed15.setText(R.string.nashed_Ecuador_15);
             nashed16.setText(R.string.nashed_Ecuador_16); nashed17.setText(R.string.nashed_Ecuador_17);
             nashed18.setText(R.string.nashed_Ecuador_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF4081"))
                     .show();
             break;
         case 159:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.uruguay);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Uruguay_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Uruguay");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Uruguay");
             }
             nashed.setText(R.string.nashed_Uruguay); nashed1.setText(R.string.nashed_Uruguay_1);
             nashed2.setText(R.string.nashed_Uruguay_2); nashed3.setText(R.string.nashed_Uruguay_3);
             nashed4.setText(R.string.nashed_Uruguay_4); nashed5.setText(R.string.nashed_Uruguay_5);
             nashed6.setText(R.string.nashed_Uruguay_6); nashed7.setText(R.string.nashed_Uruguay_7);
             nashed8.setText(R.string.nashed_Uruguay_8); nashed9.setText(R.string.nashed_Uruguay_9);
             nashed10.setText(R.string.nashed_Uruguay_10); nashed11.setText(R.string.nashed_Uruguay_11);
             nashed12.setText(R.string.nashed_Uruguay_12); nashed13.setText(R.string.nashed_Uruguay_13);
             nashed14.setText(R.string.nashed_Uruguay_14); nashed15.setText(R.string.nashed_Uruguay_15);
             nashed16.setText(R.string.nashed_Uruguay_16); nashed17.setText(R.string.nashed_Uruguay_17);
             nashed18.setText(R.string.nashed_Uruguay_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF4081"))
                     .show();
             break;
         case 160:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.paraguay);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Paraguay_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Paraguay");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Paraguay");
             }
             nashed.setText(R.string.nashed_Paraguay); nashed1.setText(R.string.nashed_Paraguay_1);
             nashed2.setText(R.string.nashed_Paraguay_2); nashed3.setText(R.string.nashed_Paraguay_3);
             nashed4.setText(R.string.nashed_Paraguay_4); nashed5.setText(R.string.nashed_Paraguay_5);
             nashed6.setText(R.string.nashed_Paraguay_6); nashed7.setText(R.string.nashed_Paraguay_7);
             nashed8.setText(R.string.nashed_Paraguay_8); nashed9.setText(R.string.nashed_Paraguay_9);
             nashed10.setText(R.string.nashed_Paraguay_10); nashed11.setText(R.string.nashed_Paraguay_11);
             nashed12.setText(R.string.nashed_Paraguay_12); nashed13.setText(R.string.nashed_Paraguay_13);
             nashed14.setText(R.string.nashed_Paraguay_14); nashed15.setText(R.string.nashed_Paraguay_15);
             nashed16.setText(R.string.nashed_Paraguay_16); nashed17.setText(R.string.nashed_Paraguay_17);
             nashed18.setText(R.string.nashed_Paraguay_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF4081"))
                     .show();
             break;
         case 161:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.suriname);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Suriname_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Suriname");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Suriname");
             }
             nashed.setText(R.string.nashed_Suriname); nashed1.setText(R.string.nashed_Suriname_1);
             nashed2.setText(R.string.nashed_Suriname_2); nashed3.setText(R.string.nashed_Suriname_3);
             nashed4.setText(R.string.nashed_Suriname_4); nashed5.setText(R.string.nashed_Suriname_5);
             nashed6.setText(R.string.nashed_Suriname_6); nashed7.setText(R.string.nashed_Suriname_7);
             nashed8.setText(R.string.nashed_Suriname_8); nashed9.setText(R.string.nashed_Suriname_9);
             nashed10.setText(R.string.nashed_Suriname_10); nashed11.setText(R.string.nashed_Suriname_11);
             nashed12.setText(R.string.nashed_Suriname_12); nashed13.setText(R.string.nashed_Suriname_13);
             nashed14.setText(R.string.nashed_Suriname_14); nashed15.setText(R.string.nashed_Suriname_15);
             nashed16.setText(R.string.nashed_Suriname_16); nashed17.setText(R.string.nashed_Suriname_17);
             nashed18.setText(R.string.nashed_Suriname_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF4081"))
                     .show();
             break;
         case 162:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.venezuela);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.black);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Venezuela_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Venezuela");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Venezuela");
             }
             nashed.setText(R.string.nashed_Venezuela); nashed1.setText(R.string.nashed_Venezuela_1);
             nashed2.setText(R.string.nashed_Venezuela_2); nashed3.setText(R.string.nashed_Venezuela_3);
             nashed4.setText(R.string.nashed_Venezuela_4); nashed5.setText(R.string.nashed_Venezuela_5);
             nashed6.setText(R.string.nashed_Venezuela_6); nashed7.setText(R.string.nashed_Venezuela_7);
             nashed8.setText(R.string.nashed_Venezuela_8); nashed9.setText(R.string.nashed_Venezuela_9);
             nashed10.setText(R.string.nashed_Venezuela_10); nashed11.setText(R.string.nashed_Venezuela_11);
             nashed12.setText(R.string.nashed_Venezuela_12); nashed13.setText(R.string.nashed_Venezuela_13);
             nashed14.setText(R.string.nashed_Venezuela_14); nashed15.setText(R.string.nashed_Venezuela_15);
             nashed16.setText(R.string.nashed_Venezuela_16); nashed17.setText(R.string.nashed_Venezuela_17);
             nashed18.setText(R.string.nashed_Venezuela_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.south_amrica))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#FF4081"))
                     .show();
             break;
         case 163:
             EasySharedPreference.Companion.putInt(KEY_IMAGE,R.drawable.australia);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_H, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_M, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_CLOCK_S, R.color.gray);
             EasySharedPreference.Companion.putInt(KEY_MARQUEE,R.string.Australia_lock);
             if (context.getString(R.string.lang).equals(egyptLanguageCode)) {
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://ar.wikipedia.org/wiki/Australia");
             }
             if (context.getString(R.string.lang).equals(londonLanguagwCode)){
                 EasySharedPreference.Companion.putString(KEY_WEB, "https://en.wikipedia.org/wiki/Australia");
             }
             nashed.setText(R.string.nashed_Australia); nashed1.setText(R.string.nashed_Australia_1);
             nashed2.setText(R.string.nashed_Australia_2); nashed3.setText(R.string.nashed_Australia_3);
             nashed4.setText(R.string.nashed_Australia_4); nashed5.setText(R.string.nashed_Australia_5);
             nashed6.setText(R.string.nashed_Australia_6); nashed7.setText(R.string.nashed_Australia_7);
             nashed8.setText(R.string.nashed_Australia_8); nashed9.setText(R.string.nashed_Australia_9);
             nashed10.setText(R.string.nashed_Australia_10); nashed11.setText(R.string.nashed_Australia_11);
             nashed12.setText(R.string.nashed_Australia_12); nashed13.setText(R.string.nashed_Australia_13);
             nashed14.setText(R.string.nashed_Australia_14); nashed15.setText(R.string.nashed_Australia_15);
             nashed16.setText(R.string.nashed_Australia_16); nashed17.setText(R.string.nashed_Australia_17);
             nashed18.setText(R.string.nashed_Australia_18);
             new MaterialToast(context)
                     .setMessage(getResources().getString(R.string.australia))
                     .setIcon(R.drawable.ic_check_circle)
                     .setDuration(Toast.LENGTH_SHORT)
                     .setBackgroundColor(Color.parseColor("#9C27B0"))
                     .show();
             break;




      }

  }
  }

    @Override
    public void onScrollStart(@NonNull ForecastAdapter.ViewHolder holder, int position) {
        holder.hideText();
    }

    @Override
    public void onScroll(
            float position,
            int currentIndex, int newIndex,
            @Nullable ForecastAdapter.ViewHolder currentHolder,
            @Nullable ForecastAdapter.ViewHolder newHolder) {
        Forecast current = forecasts.get(currentIndex);
        RecyclerView.Adapter<?> adapter = cityPicker.getAdapter();
        int itemCount = adapter != null ? adapter.getItemCount() : 0;
        if (newIndex >= 0 && newIndex < itemCount) {
            Forecast next = forecasts.get(newIndex);
            forecastView.onScroll(1f - Math.abs(position), current, next);
        }
    }

    @Override
    public void onScrollEnd(@NonNull ForecastAdapter.ViewHolder holder, int position) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
            // Toast for back pressed
        new MaterialToast(context)
                .setMessage(getResources().getString(R.string.back))
                .setIcon(R.drawable.ic_check_circle)
                .setDuration(Toast.LENGTH_LONG)
                .setBackgroundColor(Color.parseColor("#9E9E9E"))
                .show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 2000);
    }


}