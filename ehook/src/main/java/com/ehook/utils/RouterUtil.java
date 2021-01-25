package com.ehook.utils;

import android.app.Activity;
import android.content.Intent;

public class RouterUtil {
    public static void startActivity(Activity activity, String pageName, String activityName) {
        Intent intent = new Intent();
        intent.setClassName(pageName, activityName);
        activity.startActivity(intent);
    }
}
