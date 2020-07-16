package com.xp.wx.spellbook.util;

import android.app.ActivityManager;
import com.xp.api.XC_MethodHook;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0014¨\u0006\u0006"}, d2 = {"com/xp/wx/spellbook/util/AvoidSealUtil$avoidSealSystem$2", "Lcom/xp/api/XC_MethodHook;", "afterHookedMethod", "", "param", "Lcom/xp/api/XC_MethodHook$MethodHookParam;", "app_release"}, k = 1, mv = {1, 1, 13})
/* compiled from: AvoidSealUtil.kt */
public final class AvoidSealUtil$avoidSealSystem$2 extends XC_MethodHook {
    AvoidSealUtil$avoidSealSystem$2() {
    }

    /* access modifiers changed from: protected */
    public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
        Intrinsics.checkParameterIsNotNull(methodHookParam, "param");
        List list = (List) methodHookParam.getResult();
        if (list != null && !list.isEmpty()) {
            Iterator it = list.iterator();
            Intrinsics.checkExpressionValueIsNotNull(it, "result.iterator()");
            while (it.hasNext()) {
                if (AvoidSealUtil.INSTANCE.checkFilterName(((ActivityManager.RunningAppProcessInfo) it.next()).processName)) {
                    it.remove();
                }
            }
            methodHookParam.setResult(list);
        }
    }
}
