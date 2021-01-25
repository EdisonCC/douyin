package com.evan.dy.plugins

import android.view.View
import android.widget.PopupWindow
import com.ehook.utils.LogUtil
import com.evan.dy.floatingview.FloatingView
import com.evan.dy.interfaces.IPopHook
import com.evan.dy.task.DyTask
import com.evan.dy.utils.ConfigUtil


object PopPlugin : IPopHook {


    override fun showAsDropDown(popupWindow: PopupWindow, view: View) {
        LogUtil.e(
            PopPlugin.javaClass.simpleName,
            "showAsDropDown  ${popupWindow.javaClass}"
        )
        ConfigUtil.getInstance().getDyConfigDisk()
//        FloatingView.get().attach(dialog)
    }

    override fun showAsDropDown3(popupWindow: PopupWindow, view: View, int1: Int, int2: Int) {
        LogUtil.e(
            PopPlugin.javaClass.simpleName,
            "showAsDropDown3  ${popupWindow.javaClass}"
        )
    }

    override fun showAsDropDown4(popupWindow: PopupWindow,view: View,int1: Int,int2: Int,int3:Int) {
        LogUtil.e(
            PopPlugin.javaClass.simpleName,
            "showAsDropDown4  ${popupWindow.javaClass}"
        )
    }

    override fun dismiss(popupWindow: PopupWindow) {
        LogUtil.e(
            PopPlugin.javaClass.simpleName,
            "dismiss  ${popupWindow.javaClass}"
        )
        FloatingView.get().text("开始")
        DyTask.getInstance().stopTask()
    }
}
