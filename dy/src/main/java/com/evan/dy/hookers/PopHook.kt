package com.evan.dy.hookers

import android.util.Log
import com.ehook.HookGlobal
import com.ehook.core.Clazz
import com.ehook.core.EHook
import com.ehook.core.HookCenter
import com.evan.dy.interfaces.IDialogHook
import com.evan.dy.interfaces.IPopHook

object PopHook : HookCenter() {
    override val interfaces: List<Class<*>>
        get() = listOf(IPopHook::class.java)

    override fun provideEventHooker(event: String): EHook? {
        return when (event) {
//            "showAsDropDown" -> {
//                iMethodNotifyHooker(
//                    clazz = Clazz.PopupWindow,
//                    method = "showAsDropDown",
//                    iClazz = IPopHook::class.java,
//                    iMethodAfter = event,
//                    needObject = true
//                )
//            }
//            "showAsDropDown3" -> {
//                iMethodNotifyHooker(
//                    clazz = Clazz.PopupWindow,
//                    method = "showAsDropDown",
//                    iClazz = IPopHook::class.java,
//                    iMethodAfter = event,
//                    needObject = true,
//                    parameterTypes = *arrayOf(Clazz.View, Clazz.Int, Clazz.Int)
//                )
//            }
//            "showAsDropDown4" -> {
//                iMethodNotifyHooker(
//                    clazz = Clazz.PopupWindow,
//                    method = "showAsDropDown",
//                    iClazz = IPopHook::class.java,
//                    iMethodAfter = event,
//                    needObject = true,
//                    parameterTypes = *arrayOf(Clazz.View, Clazz.Int, Clazz.Int,Clazz.Int)
//                )
//            }
            "dismiss" -> {
                iMethodNotifyHooker(
                    clazz = Clazz.PopupWindow,
                    method = "dismiss",
                    iClazz = IPopHook::class.java,
                    iMethodAfter = event,
                    needObject = true
                )
            }
            else -> {
                when {
                    HookGlobal.unitTestMode -> throw IllegalArgumentException("Unknown event: $event")
                    else -> {
                        Log.e(PopHook::class.java.name, "function not found: $event")
                        return null
                    }
                }
            }
        }
    }
}
