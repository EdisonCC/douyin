package com.evan.dy;

import android.content.Context;

import com.ehook.utils.LogUtil;
import com.evan.dy.utils.ConfigUtil;
import com.evan.socket.Constants;
import com.evan.socket.SocketClient;
import com.evan.socket.SocketRequestHandler;
import com.evan.dy.cmd.CmdEngine;

public class WeWorkService {
    public static final String TAG = "WeWorkService";

    public static void onCreate(Context context) {
        LogUtil.INSTANCE.e(TAG, "WeWorkService onCreate ....");
        ConfigUtil.getInstance().registerReceiver(context);
        SocketClient.getInstance()
            .connect("sekiro.virjar.com", Constants.defaultNatServerPort, "1234567890", "dy");
        for (SocketRequestHandler handler : CmdEngine.INSTANCE.getCmdList()) {
            SocketClient.getInstance().registerHandler(handler.getClass().getSimpleName(), handler);
        }
    }
}
