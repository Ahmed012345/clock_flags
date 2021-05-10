package com.zaker.clockhall.small_library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zaker.clockhall.R;


@SuppressLint("AppCompatCustomView")
public class LightTextView extends TextView {

    public LightTextView(Context context) {
        super(context);
    }

    public LightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   public LightTextView(Context context, AttributeSet attrs) {
       super(context, attrs);

       if (context.getString(R.string.lang).equals("ar")) {
           setTypeface(FontManger.yad);
       }
       if (context.getString(R.string.lang).equals("en")) {
           setTypeface(FontManger.english);
       }
   }
}


