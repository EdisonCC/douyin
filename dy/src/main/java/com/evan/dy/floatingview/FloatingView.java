package com.evan.dy.floatingview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;

public class FloatingView {
    public EnFloatingView mEnFloatingView;
    private static volatile FloatingView mInstance;
    private ViewGroup mContainer;
    private boolean isStart;
    private FloatingView() {
    }

    public static FloatingView get() {
        if (mInstance == null) {
            synchronized (FloatingView.class) {
                if (mInstance == null) {
                    mInstance = new FloatingView();
                }
            }
        }
        return mInstance;
    }

    public FloatingView remove() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mEnFloatingView == null) {
                    return;
                }
                if (ViewCompat.isAttachedToWindow(mEnFloatingView) && mContainer != null) {
                    mContainer.removeView(mEnFloatingView);
                }
                mEnFloatingView = null;
            }
        });
        return this;
    }

    private void ensureMiniPlayer(Context context) {
        synchronized (this) {
            if (mEnFloatingView != null) {
                return;
            }
            mEnFloatingView = new EnFloatingView(context.getApplicationContext());
            mEnFloatingView.setLayoutParams(getParams(context));
            addViewToWindow(mEnFloatingView);
        }
    }

    public FloatingView add(Activity activity) {
        ensureMiniPlayer(activity);
        return this;
    }

    public FloatingView attach(Activity activity) {
        ViewGroup activityRoot = getActivityRoot(activity);
        attach(activityRoot);
        return this;
    }
    public FloatingView attach(Dialog dialog) {
        ViewGroup activityRoot = getDialogRoot(dialog);
        attach(activityRoot);
        return this;
    }
    public FloatingView attach(ViewGroup container) {
        if (container == null || mEnFloatingView == null) {
            mContainer = container;
            return this;
        }
        if (mEnFloatingView.getParent() == container) {
            return this;
        }
        if (mContainer != null && mEnFloatingView.getParent() == mContainer) {
            mContainer.removeView(mEnFloatingView);
        }
        mContainer = container;
        container.addView(mEnFloatingView);
        mEnFloatingView.bringToFront();
//        container.bringChildToFront(mEnFloatingView);
        return this;
    }

    public FloatingView detach(Activity activity) {
        detach(getActivityRoot(activity));
        return this;
    }
    public FloatingView detach(Dialog dialog) {
        detach(getDialogRoot(dialog));
        return this;
    }

    public FloatingView detach(ViewGroup container) {
//        if (mEnFloatingView != null && container != null && ViewCompat.isAttachedToWindow(mEnFloatingView)) {
        if (mEnFloatingView != null && container != null) {
            try {
                container.removeView(mEnFloatingView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mContainer == container) {
            mContainer = null;
        }
        return this;
    }

    public EnFloatingView getView() {
        return mEnFloatingView;
    }

    public FloatingView text(String text) {
        isStart = TextUtils.equals("开始", text);
        if (mEnFloatingView != null) {
            mEnFloatingView.setText(text);
        }
        return this;
    }
    public boolean isStart(){
        return isStart;
    }
    public FloatingView text1(String text) {
        if (mEnFloatingView != null) {
            mEnFloatingView.setText1(text);
        }
        return this;
    }

    public FloatingView setTextClick(View.OnClickListener listener) {
        if (mEnFloatingView != null) {
            mEnFloatingView.textView.setOnClickListener(listener);
        }
        return this;
    }


    public FloatingView setText1Click(View.OnClickListener listener) {
        if (mEnFloatingView != null) {
            mEnFloatingView.textView1.setOnClickListener(listener);
        }
        return this;
    }

    public FloatingView layoutParams(ViewGroup.LayoutParams params) {
        if (mEnFloatingView != null) {
            mEnFloatingView.setLayoutParams(params);
        }
        return this;
    }


    private void addViewToWindow(final EnFloatingView view) {
        if (mContainer == null) {
            return;
        }
        mContainer.addView(view);
    }

    private FrameLayout.LayoutParams getParams(Context context) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.START;
//        params.setMargins(params.leftMargin, (int) SystemUtils.dipToPx(context, 78), params.rightMargin, params.bottomMargin);
        params.setMargins(params.leftMargin, 0, params.rightMargin, params.bottomMargin);
        return params;
    }

    private FrameLayout getDialogRoot(Dialog dialog) {
        if (dialog == null) {
            return null;
        }
        try {
            return (FrameLayout) dialog.getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private ViewGroup getActivityRoot(Activity activity) {
        if (activity == null) {
            return null;
        }
        try {
//            return (ViewGroup) activity.getWindow().getDecorView();
            return (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
