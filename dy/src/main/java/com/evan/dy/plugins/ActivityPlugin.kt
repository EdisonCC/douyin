package com.evan.dy.plugins

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Toast
import com.ehook.plugins.interfaces.IActivityHook
import com.ehook.utils.LogUtil
import com.ehook.utils.RouterUtil
import com.evan.dy.api.ChatRoomApi
import com.evan.dy.api.DyHookApi
import com.evan.dy.floatingview.FloatingView
import com.evan.dy.task.DyTask
import com.evan.dy.utils.ConfigUtil
import com.evan.dy.utils.JsonUtils
import kotlin.jvm.internal.Intrinsics


object ActivityPlugin : IActivityHook {

    /*  ------------------  IActivityHook  ----------------- */
    override fun onCreate(activity: Activity, savedInstanceState: Bundle?) {
        LogUtil.e(
            ActivityPlugin.javaClass.simpleName,
            "onCreate  ${activity.javaClass.simpleName}"
        )
        if (activity.javaClass.simpleName.contains("ExternalWechatUserMessageListActivity")) {
//            Debug.hook()
        }
        FloatingView.get().add(activity)
            .text("开始").setTextClick {
                val currUser = DyHookApi.getCurrUser()
                LogUtil.e("currUser..." + JsonUtils.toJson(currUser))
                if (currUser?.uid == null) {
                    Toast.makeText(activity, "请先登录!", Toast.LENGTH_SHORT).show()
                    return@setTextClick
                }
                if (FloatingView.get().isStart) {
                    Toast.makeText(activity, "控场开始", Toast.LENGTH_SHORT).show()
                    FloatingView.get().text("停止")
                    DyTask.getInstance().startTask()
                } else {
                    DyTask.getInstance().stopTask()
                    Toast.makeText(activity, "控场停止", Toast.LENGTH_SHORT).show()
                    FloatingView.get().text("开始")
                }
            }.text1("设置").setText1Click {
                RouterUtil.startActivity(activity,"com.ehook.dy","com.ehook.dy.MainActivity")
            }
    }

    override fun onResume(activity: Activity) {
        LogUtil.e(
            ActivityPlugin.javaClass.simpleName,
            "onResume  ${activity.javaClass}"
        )
        ConfigUtil.getInstance().getDyConfigDisk()
//        if (TextUtils.equals(activity.javaClass.simpleName,"LivePlayActivity")) {
//            FloatingView.get().attach(activity)
//        }
    }


    override fun onPause(activity: Activity) {
        LogUtil.e( ActivityPlugin.javaClass.simpleName,
            "onPause  ${activity.javaClass}")
//        FloatingView.get().detach(activity)
//        if (TextUtils.equals(activity.javaClass.simpleName,"LivePlayActivity")) {
//            FloatingView.get().text("开始").detach(activity)
//        }
//        DyTask.getInstance().stopTask()
    }
}
