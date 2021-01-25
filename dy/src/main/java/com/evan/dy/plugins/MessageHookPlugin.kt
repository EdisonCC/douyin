package com.evan.dy.plugins

import com.ehook.utils.LogUtil
import com.evan.dy.api.ChatRoomApi
import com.evan.dy.api.model.LiveRoom
import com.evan.dy.api.model.LiveRoomMessage
import com.evan.dy.floatingview.FloatingView
import com.evan.dy.interfaces.IMessageHook
import com.evan.dy.task.DyTask
import com.evan.socket.JsonUtil
import kotlin.jvm.internal.Intrinsics


object MessageHookPlugin : IMessageHook {
    override fun onCommentWidgetResume(widget: Any?) {
        LogUtil.e(MessageHookPlugin::class.java.simpleName, "onCommentWidgetResume")
        ChatRoomApi.setCommentWidget(widget)
    }

    override fun onCommentWidgetPause(widget: Any?) {
        LogUtil.e(MessageHookPlugin::class.java.simpleName, "onCommentWidgetPause")
        if (Intrinsics.areEqual(ChatRoomApi.getCommentWidget(), widget)) {
            ChatRoomApi.setCommentWidget(null)
            ChatRoomApi.setRoom(null)
        }
    }
    override fun onRoomChanged(room: Any?) {
        if (room != null) {
            LogUtil.e(MessageHookPlugin::class.java.simpleName, "onRoomChanged")
        }
        ChatRoomApi.setRoom(LiveRoom(room))
        FloatingView.get().text("开始")
        DyTask.getInstance().stopTask()
    }

    override fun onMessage(message: Any?) {
        if (message != null) {
            DyTask.getInstance().handleMessage(LiveRoomMessage(message).message)
            LogUtil.e(MessageHookPlugin::class.java.simpleName, "onMessage=${LiveRoomMessage(message).message}")
//            LogUtil.e(MessageHookPlugin::class.java.simpleName, "qqqq=${JsonUtil.toJson(message)}")
        }else{
            LogUtil.e(MessageHookPlugin::class.java.simpleName, "message null")
        }
    }
}
