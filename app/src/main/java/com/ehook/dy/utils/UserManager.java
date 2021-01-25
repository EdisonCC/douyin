package com.ehook.dy.utils;

import android.content.Context;
import android.text.TextUtils;

import com.ehook.dy.model.LoginUser;

import a.c.e.h.f;

public class UserManager {
    private static String KYE_USER_INFO = "DY_USER_INFO_V4";
    private static UserManager userManager;
    private LoginUser user;

    public static UserManager getInstance() {
        if (userManager == null) {
            synchronized (UserManager.class) {
                if (userManager == null) {
                    userManager = new UserManager();
                }
            }
        }
        return userManager;
    }

    public LoginUser getUser(Context context) {
        if (user == null) {
            user = SharedUtil.instance(context).getObject(KYE_USER_INFO, LoginUser.class);
        }
        if (user == null) {
            user = new LoginUser();
        }
        return user;
    }

    public void setLoginUser(Context context, LoginUser user) {
        this.user = user;
        SharedUtil.instance(context).saveObject(KYE_USER_INFO, user);
    }

    public boolean isLogin(Context context) {
        return !TextUtils.isEmpty(getUser(context).token);
    }

        public long time = 1000 * 60 * 60 * 2;
//    public long time = 1000 * 10;

    public boolean isValidTime(Context context) {
        LoginUser user1 = getUser(context);
        if (user1.time == 0) {
            user1.time = System.currentTimeMillis();
            setLoginUser(context, user1);
            return true;
        }
        if (Math.abs(user1.time - System.currentTimeMillis()) > time) {
            return false;
        }
        return true;
    }

    public boolean isValidLogin(Context context) {
        if (isLogin(context)) {
            LoginUser user = getUser(context);
            return f.INSTANCE.c(context, user.token);
        }
        return false;
    }
}
