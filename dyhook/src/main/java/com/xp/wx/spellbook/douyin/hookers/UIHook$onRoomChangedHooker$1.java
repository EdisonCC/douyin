package com.xp.wx.spellbook.douyin.hookers;

import android.util.Log;
import com.xp.api.XC_MethodHook;
import com.xp.api.XmasBridge;
import com.xp.wx.spellbook.douyin.interfaces.IUIHook;
import com.xp.wx.spellbook.douyin.mirror.com.bytedance.android.livesdk.chatroom.ui.Methods;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 1, 13})
/* compiled from: UIHook.kt */
final class UIHook$onRoomChangedHooker$1 extends Lambda implements Function0<Unit> {
    public static final UIHook$onRoomChangedHooker$1 INSTANCE = new UIHook$onRoomChangedHooker$1();

    UIHook$onRoomChangedHooker$1() {
        super(0);
    }

    public final void invoke() {
        XmasBridge.hookMethod(Methods.INSTANCE.getClassLivesdkChatroomUiCh_a(), new XC_MethodHook() {
            /* access modifiers changed from: protected */
            public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                Intrinsics.checkParameterIsNotNull(methodHookParam, "p");
                Set<Object> findObservers = UIHook.INSTANCE.findObservers("onRoomChanged");
                if (findObservers != null) {
                    for (Object next : findObservers) {
                        if (next != null) {
                            try {
                                ((IUIHook) next).onRoomChanged(methodHookParam.args[0]);
                                Unit unit = Unit.INSTANCE;
                            } catch (Throwable th) {
                                Log.e("Xposed", Log.getStackTraceString(th));
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type com.xp.wx.spellbook.douyin.interfaces.IUIHook");
                        }
                    }
                }
            }
        });
        XmasBridge.hookMethod(Methods.INSTANCE.getClassLivesdkChatroomUiCh_p(), new XC_MethodHook() {
            /* access modifiers changed from: protected */
            public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                Intrinsics.checkParameterIsNotNull(methodHookParam, "p");
                Set<Object> findObservers = UIHook.INSTANCE.findObservers("onRoomChanged");
                if (findObservers != null) {
                    for (Object next : findObservers) {
                        if (next != null) {
                            try {
                                ((IUIHook) next).onRoomChanged((Object) null);
                                Unit unit = Unit.INSTANCE;
                            } catch (Throwable th) {
                                Log.e("Xposed", Log.getStackTraceString(th));
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type com.xp.wx.spellbook.douyin.interfaces.IUIHook");
                        }
                    }
                }
            }
        });
    }
}
