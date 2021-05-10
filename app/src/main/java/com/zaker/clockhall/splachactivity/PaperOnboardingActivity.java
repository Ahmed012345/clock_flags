package com.zaker.clockhall.splachactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.zaker.clockhall.R;
import com.zaker.clockhall.paperonboarding.PaperOnboardingEngine;
import com.zaker.clockhall.paperonboarding.PaperOnboardingPage;
import com.zaker.clockhall.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.zaker.clockhall.paperonboarding.listeners.PaperOnboardingOnRightOutListener;
import com.zaker.clockhall.small_library.FontManger;
import com.zaker.clockhall.startgallry.MainActivity;
import java.util.ArrayList;

public class PaperOnboardingActivity extends LocalizationActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_main_layout);
        FontManger.getInstance(getApplicationContext().getAssets());

        PaperOnboardingEngine engine = new PaperOnboardingEngine(findViewById(R.id.onboardingRootView),
                getDataForOnboarding(), getApplicationContext());

        engine.setOnChangeListener(new PaperOnboardingOnChangeListener() {
            @Override
            public void onPageChanged(int oldElementIndex, int newElementIndex) {
            }
        });

        engine.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                // Probably here will be your exit action
                startActivity(new Intent(PaperOnboardingActivity.this, MainActivity.class));
            }
        });



        }

    // Just example data for Onboarding
    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage(R.string.earth, R.string.earth_big,
                Color.parseColor("#845EC2"), R.drawable.earth, R.drawable.earth_small);
        PaperOnboardingPage scr2 = new PaperOnboardingPage(R.string.naby, R.string.hadis,
                Color.parseColor("#D65DB1"), R.drawable.peace, R.drawable.peace_small);
        PaperOnboardingPage scr3 = new PaperOnboardingPage(R.string.makola, R.string.makola2,
                Color.parseColor("#FF6F91"), R.drawable.humans, R.drawable.human_small);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }

}
