package a.c.e.g;

import android.content.Context;
import android.text.TextUtils;

import a.c.e.f.AESUtil;
import a.c.e.f.DeviceUtils;

public class a {

    public static final String k = "hd123qweasdzxc";

    public boolean c(Context context, String token) {
        if (token != null) {
            String decrypt = AESUtil.decrypt(token, k);
            String imei = DeviceUtils.getIMEI(context);
            return TextUtils.equals(decrypt, imei);
        }
        return false;
    }
}
