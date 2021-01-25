package com.evan.dy.floatingview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.evan.dy.floatingview.utils.SystemUtils;


public class EnFloatingView extends FloatingMagnetView {


    public final TextView textView;
    public final TextView textView1;
    private final TextView textView0;

    public EnFloatingView(@NonNull Context context) {
        super(context, null);
        LinearLayout linearLayout = new LinearLayout(context);
        textView0 = genText();
        textView0.setClickable(true);
        textView0.setText("控场 :");
        textView0.setTextColor(Color.parseColor("#E5E4E6"));
        textView = genText();
        textView.setClickable(true);
        textView.setText("开始");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(layoutParams);
        int v = (int) SystemUtils.dipToPx(getContext(), 42);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(v, v);
        textView1 = genText();
        textView1.setText("设置");
        textView1.setClickable(true);
//        layoutParams.topMargin = (int) SystemUtils.dipToPx(context, 30);
        linearLayout.addView(textView0, layoutParams1);
        linearLayout.addView(textView, layoutParams1);
        linearLayout.addView(textView1, layoutParams1);
        this.addView(linearLayout);
    }

    public TextView genText() {
        TextView textView = new TextView(getContext());
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setTextSize(15);
        textView.setGravity(Gravity.CENTER);
//        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//        textView.setBackgroundColor(Color.parseColor("#cc333333"));
        return textView;
    }

    public void setText(String text) {
        this.textView.setText(text);
    }

    public void setText1(String text) {
        this.textView1.setText(text);
    }
}
