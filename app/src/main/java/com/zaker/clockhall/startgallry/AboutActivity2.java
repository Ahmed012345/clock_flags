package com.zaker.clockhall.startgallry;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.marcoscg.materialtoast.MaterialToast;
import com.zaker.clockhall.R;
import com.zaker.clockhall.materialabout.builder.AboutBuilder;
import com.zaker.clockhall.materialabout.views.AboutView;
import com.zaker.clockhall.startgallry.receiver.AlarmReceiver2;
import com.zaker.clockhall.startgallry.receiver.AlarmReceiver6;

import java.util.Calendar;


public class AboutActivity2 extends LocalizationActivity {

    FrameLayout flHolder;
    Context context = this;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about2);

        flHolder = findViewById(R.id.about);

        AboutBuilder builder = AboutBuilder.with(this)
                .setAppIcon(R.drawable.clock)
                .setAppName(R.string.app_name)
                .setPhoto(R.mipmap.profile_picture)
                .setCover(R.mipmap.profile_cover)
                .setLinksAnimated(true)
                .setDividerDashGap(13)
                .setName(R.string.myname)
                .setSubTitle(R.string.Developer)
                .setLinksColumnsCount(4)
                .setBrief(R.string.makolaty)
                .addGitHubLink("Ahmed012345")
                .addFacebookLink("kraro0o0o")
                .addTwitterLink("A7md_c3d")
       //       .addInstagramLink("jnrvans")
                .addYoutubeChannelLink("UCYg6-xM8lEL6RAGwJ5BFG7A")
       //       .addLinkedInLink("arleu-cezar-vansuita-júnior-83769271")
                .addEmailLink("asmmya7@gmail.com")
                .addWhatsappLink("Ahmed", "+201002264423")
       //       .addSkypeLink("user")
                .addWebsiteLink("mybluesky.site")
                .addFiveStarsAction()
                .addMoreFromMeAction("A7md+S3d&hl=ar&gl=US")
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .setActionsColumnsCount(2)
                .addFeedbackAction("asmmya7@gmail.com")
                .addIntroduceAction(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DemoUtils.showLicensesDialog(context);
                    }
                })
                .setWrapScrollView(true)
                .setShowAsCard(true);

        AboutView view = builder.build();

        flHolder.addView(view);

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
                .setMessage(getResources().getString(R.string.finalback))
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