package com.ehook.dy

import android.app.Application
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) {
//            CmdUtil.killProcesses(this, "com.tencent.mm")
//            CmdUtil.killProcesses(this, "com.tencent.mm")
//        }
        Bugly.init(this, "9f00de46d8", false);
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
    }
}
