package com.evan.dy.interfaces

import android.app.Dialog

interface IDialogHook {

    fun show(dialog: Dialog)

    fun dismiss(dialog: Dialog)

}
