package com.evan.dy.floatingview;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.evan.dy.floatingview.utils.SystemUtils;


public class EnFloatingView extends FloatingMagnetView {


    public final TextView textView;
    public final TextView textView1;

    public EnFloatingView(@NonNull Context context) {
        super(context, null);
        LinearLayout linearLayout = new LinearLayout(context);
        textView = genText();
        textView.setText("开启");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(layoutParams);
        int v = (int) SystemUtils.dipToPx(getContext(), 50);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(v, v);
        linearLayout.addView(textView,layoutParams1);
        textView1 = genText();
        textView1.setText("设置");
        layoutParams.topMargin = (int) SystemUtils.dipToPx(context, 30);
        linearLayout.addView(textView1,layoutParams1);
        this.addView(linearLayout);
    }

    public TextView genText() {
        TextView textView = new TextView(getContext());
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setTextSize(18);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.parseColor("#F5A623"));
        return textView;
    }

    public void setText(String text) {
        this.textView.setText(text);
    }

    public void setText1(String text) {
        this.textView1.setText(text);
    }
}
