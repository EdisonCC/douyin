package com.evan.dy.hookers

import android.util.Log
import com.ehook.HookGlobal
import com.ehook.core.EHook
import com.ehook.core.HookCenter
import com.ehook.utils.LogUtil
import com.evan.dy.interfaces.IMessageHook
import com.evan.dy.mirror.com.bytedance.android.livesdk.chatroom.presenter.Classes
import com.evan.dy.mirror.com.bytedance.android.livesdk.chatroom.presenter.Methods
import com.evan.dy.mirror.com.bytedance.android.livesdk.chatroom.ui.Classes.getClassLivesdkChatroomUiCh
import com.evan.dy.mirror.com.bytedance.android.livesdk.chatroom.ui.Methods.getClassLivesdkChatroomUiCh_a
import com.evan.dy.mirror.com.bytedance.android.livesdk.chatroom.viewmodule.Classes.ClassCommentWidget
import com.evan.dy.mirror.com.bytedance.android.livesdk.chatroom.viewmodule.Methods.ClassCommentWidget_onPause
import com.evan.dy.mirror.com.bytedance.android.livesdk.chatroom.viewmodule.Methods.ClassCommentWidget_onResume

object MessageHook : HookCenter() {
    override val interfaces: List<Class<*>>
        get() = listOf(IMessageHook::class.java)

    override fun provideEventHooker(event: String): EHook? {
        return when (event) {
            "onCommentWidgetResume" -> {
                iMethodNotifyHooker(
                    clazz = ClassCommentWidget,
                    method = ClassCommentWidget_onResume,
                    iClazz = IMessageHook::class.java,
                    iMethodAfter = event,
                    needObject = true
                )
            }
            "onCommentWidgetPause" -> {
                iMethodNotifyHooker(
                    clazz = ClassCommentWidget,
                    method = ClassCommentWidget_onPause,
                    iClazz = IMessageHook::class.java,
                    iMethodAfter =event,
                    needObject = true
                )
            }
            "onRoomChanged" -> {
                iMethodNotifyHooker(
                    clazz = getClassLivesdkChatroomUiCh,
                    method = getClassLivesdkChatroomUiCh_a,
                    iClazz = IMessageHook::class.java,
                    iMethodAfter = event,
                    parameterTypes = *arrayOf(com.evan.dy.mirror.com.bytedance.android.livesdkapi.depend.model.live.Classes.ClassLivesdkapiDependModelLiveRoom)
                )
            }
            "onMessage" -> {
                iMethodNotifyHooker(
                    clazz = Classes.ClassChatroomPresenterAz,
                    method = Methods.onMessage,
                    iClazz = IMessageHook::class.java,
                    iMethodAfter = event,
                    parameterTypes = *arrayOf(com.evan.dy.mirror.com.ss.ugc.live.sdk.message.data.Classes.ClassLiveSdkMessageDataIMessage)
                )
            }
            else -> {
                when {
                    HookGlobal.unitTestMode -> throw IllegalArgumentException("Unknown event: $event")
                    else -> {
                        Log.e(MessageHook::class.java.name, "function not found: $event")
                        return null
                    }
                }
            }
        }
    }
}
