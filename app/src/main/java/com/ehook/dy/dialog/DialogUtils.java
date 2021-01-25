package com.ehook.dy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ehook.dy.MainActivity;
import com.ehook.dy.R;
import com.ehook.dy.utils.KeyBordUtil;
import com.evan.dy.floatingview.FloatingView;

public abstract class DialogUtils extends Dialog {

    private final TextView tvTitle;
    protected final View contentView;
    protected final TextView tvSave;

    public DialogUtils(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        //获得dialog的window窗口
        Window window = this.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();

        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = (int) (getDeviceHeight(context) * 3f / 5);
        //将设置好的属性set回去
        window.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            this.getWindow().setWindowAnimations(R.style.dialog_anim);
        }
        View inflate = LinearLayout.inflate(context, R.layout.dialog_view, null);
        tvTitle = inflate.findViewById(R.id.text_title);
        tvSave = inflate.findViewById(R.id.text_save);
        ScrollView scrollView = inflate.findViewById(R.id.scrollView);
        inflate.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, lp.height));
        contentView = LinearLayout.inflate(getContext(), getLayoutId(), null);
        scrollView.addView(contentView);
        setContentView(inflate);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBordUtil.hideSoftKeyboard(view);
                onSaveClick();
            }
        });
    }

    protected void setTitle(String title) {
        tvTitle.setText(title);
    }

    abstract int getLayoutId();


    protected void onSaveClick() {

    }

    public interface DialogCallback{
        void callback(Dialog dialog, Object o);
    }
    public static int getDeviceHeight(Context context) {
        DisplayMetrics outMetrics = obtain(context);
        return outMetrics.heightPixels;
    }

    private static DisplayMetrics obtain(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
