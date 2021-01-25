package com.evan.dy.hookers

import android.util.Log
import com.ehook.HookGlobal
import com.ehook.core.Clazz
import com.ehook.core.EHook
import com.ehook.core.HookCenter
import com.evan.dy.interfaces.IDialogHook

object DialogHook : HookCenter() {
    override val interfaces: List<Class<*>>
        get() = listOf(IDialogHook::class.java)

    override fun provideEventHooker(event: String): EHook? {
        return when (event) {
            "show" -> {
                iMethodNotifyHooker(
                    clazz = Clazz.Dialog,
                    method = "show",
                    iClazz = IDialogHook::class.java,
                    iMethodAfter = event,
                    needObject = true
                )
            }
            "dismiss" -> {
                iMethodNotifyHooker(
                    clazz = Clazz.Dialog,
                    method = "dismiss",
                    iClazz = IDialogHook::class.java,
                    iMethodAfter = event,
                    needObject = true
                )
            }
            else -> {
                when {
                    HookGlobal.unitTestMode -> throw IllegalArgumentException("Unknown event: $event")
                    else -> {
                        Log.e(DialogHook::class.java.name, "function not found: $event")
                        return null
                    }
                }
            }
        }
    }
}
