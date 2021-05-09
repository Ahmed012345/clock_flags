package com.zaker.clockhall.lockscreen;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.zaker.clockhall.App;
import com.zaker.clockhall.R;
import com.zaker.clockhall.clockview.Clock;
import com.zaker.clockhall.easysharedpreferences.EasySharedPreference;
import com.zaker.clockhall.lockscreen.utils.ViewUtils;
import com.zaker.clockhall.small_library.FontManger;
import com.zaker.clockhall.startgallry.AboutActivity2;
import com.zaker.clockhall.startgallry.MainActivity;
import com.zaker.clockhall.startgallry.VerticalMarqueeTextView;
import com.zaker.clockhall.startgallry.receiver.AlarmReceiver10;
import com.zaker.clockhall.startgallry.receiver.AlarmReceiver4;
import com.zaker.clockhall.startgallry.receiver.AlarmReceiver9;
import com.zaker.clockhall.widget.TouchToUnLockView;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.Locale;

import androidx.appcompat.widget.AppCompatImageView;

import static com.zaker.clockhall.startgallry.MainActivity.KEY_CLOCK_H;
import static com.zaker.clockhall.startgallry.MainActivity.KEY_CLOCK_M;
import static com.zaker.clockhall.startgallry.MainActivity.KEY_CLOCK_S;


public class LockScreenActivity extends LocalizationActivity {

    private Calendar calendar = GregorianCalendar.getInstance();
    private SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MMM d", Locale.getDefault());

    TextView  data;
    private TouchToUnLockView mUnlockView;
    private View mContainerView;
    Clock clock_lock;
    public final static String KEY_IMAGE = "IMAGE";
    public final static String KEY_MARQUEE = "MARQUEE";
    VerticalMarqueeTextView marquee;
    AppCompatImageView clock_background;
    private InterstitialAd mInterstitialAd;


    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen_activity);
        FontManger.getInstance(getApplicationContext().getAssets());

        initView();
        updateTimeUI();
        int image = EasySharedPreference.Companion.getInt(KEY_IMAGE, R.drawable.egypt);
        clock_background.setImageResource(image);
        int image_hour = EasySharedPreference.Companion.getInt(KEY_CLOCK_H, R.color.yellow);
        clock_lock.setHoursNeedleColor(image_hour);
        int image_minutes = EasySharedPreference.Companion.getInt(KEY_CLOCK_M, R.color.yellow);
        clock_lock.setMinutesNeedleColor(image_minutes);
        int image_seconds = EasySharedPreference.Companion.getInt(KEY_CLOCK_S, R.color.yellow);
        clock_lock.setSecondsNeedleColor(image_seconds);
        int m = EasySharedPreference.Companion.getInt(KEY_MARQUEE, R.string.Egypt_lock);
        marquee.setText(m);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9574398154137015/9399188150");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });




    }

        @SuppressLint("SetTextI18n")
    private void updateTimeUI() {
        data.setText(weekFormat.format(calendar.getTime()) + "    " + monthFormat.format(calendar.getTime()));
    }

    private void initView() {
        // Set everything up...
        marquee = findViewById(R.id.marquee);
        clock_lock = findViewById(R.id.clock_lock);
        clock_background = findViewById(R.id.clock_background);
        data = ViewUtils.get(this, R.id.txtv_LockDate);
        mContainerView = ViewUtils.get(this, R.id.relel_ContentContainer);
        mUnlockView = ViewUtils.get(this, R.id.tulv_UnlockView);

        mUnlockView.setOnTouchToUnlockListener(new TouchToUnLockView.OnTouchToUnlockListener() {
                @Override
                public void onTouchLockArea() {
                    //Take picture using the camera without preview.
                    // Permission not granted, ask for it


                }

                @Override
                public void onSlidePercent(float percent) {
                    if (mContainerView != null) {
                        mContainerView.setAlpha(Math.max(1 - percent, 0.05f));
                        mContainerView.setScaleX(1 + (Math.min(percent, 1f)) * 0.08f);
                        mContainerView.setScaleY(1 + (Math.min(percent, 1f)) * 0.08f);
                    }
                }

                @Override
                public void onSlideToUnlock() {

                    finish();

                }

                @Override
                public void onSlideAbort() {
                    if (mContainerView != null) {
                        mContainerView.setAlpha(1.0f);
                        mContainerView.setBackgroundColor(0);
                        mContainerView.setScaleX(1f);
                        mContainerView.setScaleY(1f);
                    }
                }
            });
        }


    @Override
    public void onAttachedToWindow() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    //    |WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onAttachedToWindow();

    }

    @Override
    public void onResume() {
        super.onResume();

        mUnlockView.startAnim();
        ((App) getApplication()).lockScreenShow = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        mUnlockView.stopAnim();
        ((App) getApplication()).lockScreenShow = false;
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

}
