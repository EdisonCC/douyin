package com.evan.dy.task;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.ehook.utils.LogUtil;
import com.evan.dy.api.DyHookApi;
import com.evan.dy.api.model.DyConfig;
import com.evan.dy.utils.ConfigUtil;

public class DyAutoSendMsgTask {
    private static final int MSG_SEND_NEXT = 200;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case MSG_SEND_NEXT:
                    sendNext();
                    break;
            }
            return false;
        }
    });
    private boolean isRunning;

    public final synchronized void startSend(){
        DyConfig.DySendFan sendFan = ConfigUtil.getInstance().getDyConfig().sendFan;
        if (sendFan != null && sendFan.status == 1) {

        }
    }
    public final void sendNextDelayed() {
        DyConfig.DySendFan sendFan = ConfigUtil.getInstance().getDyConfig().sendFan;
        if (sendFan != null && sendFan.status == 1) {
            if (sendFan.interval > 0) {
                handler.removeMessages(MSG_SEND_NEXT);
                handler.sendEmptyMessageDelayed(200, ((long) sendFan.interval) * 1000);
            } else {
                LogUtil.INSTANCE.e("AutoSendMsg. interval <= 0, not run.");
                stop();
            }
        } else {
            stop();
        }
    }
    public final void sendNext() {
        DyConfig.DySendFan sendFan = ConfigUtil.getInstance().getDyConfig().sendFan;
        if (sendFan != null && sendFan.status == 1) {
//            DyHookApi.sendMass(cmdBean.count, cmdBean.gender, cmdBean.followStatus, cmdBean.content, null);
        } else {
            stop();
        }
    }
    public final synchronized void stop() {
        if (isRunning) {
            LogUtil.INSTANCE.e("AutoSendMsg stop....");
            handler.removeMessages(MSG_SEND_NEXT);
            isRunning = false;
        }
    }
}
