package com.zaker.clockhall.materialabout.util;

import androidx.annotation.Nullable;
import android.view.View;

/**
 * Created by jrvansuita on 20/02/17.
 */

public final class VisibleUtil {

    public static void handle(View v, @Nullable String s) {
        v.setVisibility(s == null || s.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
