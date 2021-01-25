package com.evan.dy.interfaces

import android.view.View
import android.widget.PopupWindow

interface IPopHook {

    fun showAsDropDown(popupWindow: PopupWindow, view: View)
    fun showAsDropDown3(popupWindow: PopupWindow, view: View, i1: Int, i2: Int)
    fun showAsDropDown4(popupWindow: PopupWindow, view: View, i1: Int, i2: Int, i3: Int)
    fun dismiss(popupWindow: PopupWindow)

}
