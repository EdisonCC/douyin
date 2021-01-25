package com.evan.dy.plugins

import android.app.Dialog
import com.ehook.utils.LogUtil
import com.evan.dy.floatingview.FloatingView
import com.evan.dy.interfaces.IDialogHook
import com.evan.dy.utils.ConfigUtil


object DialogPlugin : IDialogHook {

    override fun show(dialog: Dialog) {
        LogUtil.e(
            DialogPlugin.javaClass.simpleName,
            "show  ${dialog.javaClass}"
        )
        when (dialog.javaClass.name) {
            "com.bytedance.android.livesdk.chatroom.viewmodule.toolbar.l"->
            {
                ConfigUtil.getInstance().getDyConfigDisk()
                FloatingView.get().attach(dialog)
            }
        }

    }

    override fun dismiss(dialog: Dialog) {
        LogUtil.e(
            DialogPlugin.javaClass.simpleName,
            "dismiss  ${dialog.javaClass}"
        )
        when (dialog.javaClass.name) {
            "com.bytedance.android.livesdk.chatroom.viewmodule.toolbar.l"->
            {
                FloatingView.get().detach(dialog)
            }
        }

//        FloatingView.get().text("开始")
//        DyTask.getInstance().stopTask()
    }
}
