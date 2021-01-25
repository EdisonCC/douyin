package com.evan.dy.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.ehook.utils.LogUtil;
import com.evan.dy.Global;
import com.evan.dy.api.model.DyComBean;
import com.evan.dy.api.model.DyConfig;
import com.evan.dy.api.model.dy.DyProfileUser;
import com.evan.dy.task.DyTask;

public final class ConfigUtil {

    private static final String ACTION_BIND_ASSISTANT_CHANGED = "action_bind_assistant_changed";
    private static final String ACTION_DY_CONFIG_CHANGED = "action_dy_config_changed";
    private static final String ACTION_DY_CONFIG_CHANGED_FROM_MODULE = "action_dy_config_changed_from_module";
    private static final String ACTION_LOGIN_CHANGED = "action_login_changed";
    private static ConfigUtil configUtil = null;
    public static ConfigUtil getInstance() {
        if (configUtil == null) {
            synchronized (ConfigUtil.class) {
                if (configUtil == null) {
                    configUtil = new ConfigUtil();
                }
            }
        }
        return configUtil;
    }
    private static DyConfig dyConfig;
    public static DyProfileUser loginUser;
    private static BroadcastReceiver mConfigChangedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            StringBuilder sb = new StringBuilder();
            sb.append("configChangedReceiver. onReceive action = ");
            sb.append(intent != null ? intent.getAction() : null);
            LogUtil.INSTANCE.e(sb.toString());
            String action = intent != null ? intent.getAction() : null;
            if (TextUtils.equals(action, ConfigUtil.ACTION_DY_CONFIG_CHANGED_FROM_MODULE)) {
                ConfigUtil.getInstance().updateDyConfig(ConfigUtil.getInstance().getDyConfigDisk());
                LogUtil.INSTANCE.e("dyConfig updated");
            }
            if (TextUtils.equals(action, ConfigUtil.ACTION_DY_CONFIG_CHANGED)) {
                ConfigUtil.getInstance().updateDyConfig(ConfigUtil.getInstance().getDyConfigDisk());
                LogUtil.INSTANCE.e("dyConfig updated");
            }
        }
    };
    private DyComBean dyComBean;


    public DyComBean getComBean() {
        String json = FileUtil.readFile(Global.getDdCommPath());
        LogUtil.INSTANCE.e("ComBean=" + json);
        if (!TextUtils.isEmpty(json)) {
            try {
                dyComBean = JsonUtils.fromJson(json, DyComBean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (dyComBean == null) {
            dyComBean = new DyComBean();
        }
        return dyComBean;
    }

    public void setDayCoinNum(int dayCoinNum) {
        DyComBean comBean = getComBean();
        comBean.setDayCoinNum(dayCoinNum);
        setComBean(comBean);
    }

    public void setComBean(DyComBean dyComBean) {
        String s = JsonUtils.toJson(dyComBean);
        LogUtil.INSTANCE.e("ComBean=" + s);
        FileUtil.save(Global.getDdCommPath(), s);
    }

    public DyConfig getDyConfigDisk() {
        String json = FileUtil.readFile(Global.getDdConfigPath());
        LogUtil.INSTANCE.e("DdConfig=" + json);
        if (TextUtils.isEmpty(json)) {
            json = Global.BASE_CONFIG_JSON;
        }
        try {
            dyConfig = JsonUtils.fromJson(json, DyConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dyConfig;
    }

    public DyConfig getDyConfig() {
        if (dyConfig == null) {
            dyConfig = getDyConfigDisk();
        }
        return dyConfig;
    }

    public void setDyConfig(DyConfig dyConfig) {
        this.dyConfig = dyConfig;
        FileUtil.save(Global.getDdConfigPath(), JsonUtils.toJson(dyConfig));
    }


    public void registerReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_DY_CONFIG_CHANGED);
        intentFilter.addAction(ACTION_LOGIN_CHANGED);
        intentFilter.addAction(ACTION_BIND_ASSISTANT_CHANGED);
        intentFilter.addAction(ACTION_DY_CONFIG_CHANGED_FROM_MODULE);
        context.registerReceiver(mConfigChangedReceiver, intentFilter);
    }

    private ConfigUtil() {
    }


    private void sendBroadcastReceiver(Context context, String str) {
        context.sendBroadcast(new Intent(str));
    }

    public void destroyTask() {
        DyTask.getInstance().stopTask();
//        AutoSendMsgHandler.stop$default(AutoSendMsgHandler.INSTANCE, 0, 1, (Object) null);
    }

    public void updateDyConfig(DyConfig dyConfig2) {
        this.dyConfig = dyConfig2;
    }

    public void updateAndNotify(Context context) {
        sendBroadcastReceiver(context, ACTION_DY_CONFIG_CHANGED_FROM_MODULE);
    }

    public void updateAndNotifyFromModule(Context context) {
        setDyConfig(dyConfig);
        sendBroadcastReceiver(context, ACTION_DY_CONFIG_CHANGED_FROM_MODULE);
    }
}
