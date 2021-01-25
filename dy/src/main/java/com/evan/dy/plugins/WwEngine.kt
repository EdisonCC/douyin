package com.evan.dy.plugins

import com.ehook.core.HookCenter
import com.evan.dy.hookers.DialogHook
import com.evan.dy.hookers.MessageHook
import com.evan.dy.hookers.PopHook


object WwEngine {

    var hookCenters: List<HookCenter> = listOf(
        MessageHook, DialogHook//, PopHook
    )
    var plugins = listOf(
        MessageHookPlugin,
        ActivityPlugin,
        DialogPlugin
//        PopPlugin
    )
}
