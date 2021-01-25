package a.c.e.f;


import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.Charset;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private static final Charset CHAR_SET = Charset.forName("utf-8");
    private static final String ALGORITHM = "AES/ECB/PKCS7Padding";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public AESUtil() {
    }

    public static String encrypt(String target, String key) {
        try {
            Cipher e = Cipher.getInstance(ALGORITHM);
            e.init(1, initKey(key));
            byte[] encryptResult = e.doFinal(target.getBytes(CHAR_SET));
            String unsafeStr = new String(Base64.encodeBase64(encryptResult, false), CHAR_SET);
//            return unsafeStr.replace('+', '-').replace('/', '_');
            return unsafeStr;
        } catch (Exception var5) {
            return null;
//            throw new BaseServiceErrorException("敏感数据加密错误", var5);
        }
    }

    public static String decrypt(String target, String key) {
        try {
            Cipher e = Cipher.getInstance(ALGORITHM);
            e.init(2, initKey(key));
//            String unsafeStr = target.replace('-', '+').replace('_', '/');
            String unsafeStr = target;
            byte[] decryptResult = e.doFinal(Base64.decodeBase64(unsafeStr.getBytes(CHAR_SET)));
            return new String(decryptResult, CHAR_SET);
        } catch (Exception var5) {
            return null;
//            throw new BaseServiceErrorException("敏感数据解密错误", var5);
        }
    }

    private static SecretKeySpec initKey(String originalKey) {
        byte[] keys = originalKey.getBytes(CHAR_SET);
        byte[] bytes = new byte[16];

        for(int i = 0; i < bytes.length; ++i) {
            if(keys.length > i) {
                bytes[i] = keys[i];
            } else {
                bytes[i] = 0;
            }
        }

        return new SecretKeySpec(bytes, "AES");
    }
}
