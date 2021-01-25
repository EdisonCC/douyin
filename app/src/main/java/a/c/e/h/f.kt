package a.c.e.h

import a.c.e.f.AESUtil
import a.c.e.f.s
import a.c.e.f.DeviceUtils
import android.content.Context
import android.text.TextUtils

object f {
    fun c(context: Context?, t: String?): Boolean {
        val i = AESUtil.decrypt(t,"qweasdzxc" + s.h)
        var c = DeviceUtils.getIMEI(context)
        return TextUtils.equals(i, c)
    }
}
