package com.yy.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author apeng
 * @dec https://github.com/tangpeng
 * @date 2018/11/1 15:30
 */
public final class EsayPermissions {

    private AppCompatActivity mActivity;
    private List<String> mPermissions = new ArrayList<String>();
    private boolean mConstant;

    private EsayPermissions(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * 设置请求的对象
     */
    public static EsayPermissions with(AppCompatActivity activity) {
        return new EsayPermissions(activity);
    }

    /**
     * 设置权限组
     */
    public EsayPermissions permission(String... permissions) {
        try {
            List<String> permissionList = Arrays.asList(permissions);
            mPermissions.addAll(permissionList);
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * 设置权限组
     */
    public EsayPermissions permission(String[]... permissions) {
        for (String[] group : permissions) {
            mPermissions.addAll(Arrays.asList(group));
        }
        return this;
    }

    /**
     * 设置权限组
     */
    public EsayPermissions permission(List<String> permissions) {
        mPermissions.addAll(permissions);
        return this;
    }

    /**
     * 被拒绝后继续申请，直到授权或者永久拒绝
     */
    public EsayPermissions constantRequest() {
        mConstant = true;
        return this;
    }

    /**
     * 请求权限
     */
    public void request(OnPermission call) {
        //如果没有指定请求的权限，就使用清单注册的权限进行请求
        if (mPermissions == null || mPermissions.size() == 0)
            mPermissions = PermissionUtils.getManifestPermissions(mActivity);
        if (mPermissions == null || mPermissions.size() == 0)
            throw new IllegalArgumentException("The requested permission cannot be empty");
        //使用isFinishing方法Activity在熄屏状态下会导致崩溃
        //if (mActivity == null || mActivity.isFinishing()) throw new IllegalArgumentException("Illegal Activity was passed in");
        if (mActivity == null) throw new IllegalArgumentException("The activity is empty");
        if (call == null)
            throw new IllegalArgumentException("The permission request callback interface must be implemented");

        PermissionUtils.checkTargetSdkVersion(mActivity, mPermissions);

        ArrayList<String> failPermissions = PermissionUtils.getFailPermissions(mActivity, mPermissions);

        if (failPermissions == null || failPermissions.size() == 0) {
            //证明权限已经全部授予过
            call.hasPermission(mPermissions, true);
        } else {
            //检测权限有没有在清单文件中注册
            PermissionUtils.checkPermissions(mActivity, mPermissions);
            //申请没有授予过的权限
            PermissionFragment.newInstant((new ArrayList<>(mPermissions)), mConstant).prepareRequest(mActivity, call);
        }
    }

    /**
     * 检查某些权限是否全部授予了
     *
     * @param context     上下文对象
     * @param permissions 需要请求的权限组
     */
    public static boolean isHasPermission(Context context, String... permissions) {
        ArrayList<String> failPermissions = PermissionUtils.getFailPermissions(context, Arrays.asList(permissions));
        return failPermissions == null || failPermissions.size() == 0;
    }

    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查某些权限是否全部授予了
     *
     * @param context     上下文对象
     * @param permissions 需要请求的权限组
     */
    public static boolean isHasPermission(Context context, String[]... permissions) {
        List<String> permissionList = new ArrayList<>();
        for (String[] group : permissions) {
            permissionList.addAll(Arrays.asList(group));
        }
        ArrayList<String> failPermissions = PermissionUtils.getFailPermissions(context, permissionList);
        return failPermissions == null || failPermissions.size() == 0;
    }

    /**
     * 跳转到应用权限设置页面
     *
     * @param context 上下文对象
     */
    public static void gotoPermissionSettings(Context context) {
        PermissionSettingPage.start(context, false);
    }

    /**
     * 跳转到应用权限设置页面
     *
     * @param context 上下文对象
     * @param newTask 是否使用新的任务栈启动
     */
    public static void gotoPermissionSettings(Context context, boolean newTask) {
        PermissionSettingPage.start(context, newTask);
    }

    public static void openPermissionSettings(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }
}
