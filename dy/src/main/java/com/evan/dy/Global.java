package com.evan.dy;

import android.os.Environment;

import java.io.File;

import kotlin.jvm.internal.Intrinsics;

public class Global {
    public static final String DY_PACKAGE_NAME = "com.ss.android.ugc.aweme";
    public static final Boolean isDebug = false;
    public static final String BASE_CONFIG_JSON = "{\"masterSwitchStatus\":{\"assistant_id\":0,\"type\":0,\"id\":0,\"status\":0},\"autoClick\":{\"name\":\"直播点赞\",\"threshold\":1,\"id\":68,\"status\":0},\"autoSendGift\":{\"name\":\"自动刷礼物\",\"gift_json\":[\"小心心\",\"樱花\",\"甜甜圈\",\"抖音\",\"好想吃\"],\"interval\":120,\"threshold\":111,\"id\":69,\"status\":0},\"autoShopping\":{\"name\":\"自动刷单\",\"interval\":60,\"id\":68,\"status\":0},\"autoSubscribe\":{\"name\":\"关注回复\",\"interval\":120,\"threshold\":500,\"id\":68,\"status\":0},\"replyGift\":{\"name\":\"礼物回复\",\"content\":\"感谢您的[礼物名称]！\",\"gift_json\":[\"小心心\",\"樱花\",\"甜甜圈\",\"抖音\",\"好想吃\"],\"id\":70,\"status\":0},\"replyKeyword\":{\"name\":\"关键词回复\",\"data\":[{\"key\":\"代金券\",\"content\":\"关注主播,领取代金券!\"}],\"id\":51,\"status\":0},\"replySubscribe\":{\"name\":\"关注回复\",\"content\":\"感谢您的关注！\",\"id\":68,\"status\":0},\"replyWelcome\":{\"name\":\"欢迎语\",\"content\":\"欢迎来到直播间！\",\"id\":68,\"status\":1},\"sendFan\":{\"name\":\"粉丝群发消息\",\"content\":\"晚上8点直播间不见不散\",\"interval\":60,\"max_send_num\":111,\"sex_type\":0,\"subscribe_type\":0,\"id\":84,\"status\":0},\"sendBulletin\":{\"name\":\"定时公告\",\"content\":\"欢迎来到直播间 666!\",\"interval\":30,\"id\":68,\"status\":0}}";

    public static String getDdConfigPath() {
        return getRootPath() + "/dy/config/dy.conf";
    }
    public static String getDdCommPath() {
        return getRootPath() + "/dy/comm/com.conf";
    }
    public static String getRootPath() {
        StringBuilder sb = new StringBuilder();
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        Intrinsics.checkExpressionValueIsNotNull(externalStorageDirectory, "Environment.getExternalStorageDirectory()");
        sb.append(externalStorageDirectory.getPath());
        return sb.append("/ehook").toString();
    }
}
