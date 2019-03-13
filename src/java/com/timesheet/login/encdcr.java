/*
 * Decompiled with CFR 0_118.
 */
package com.timesheet.login;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncDecr {
    private static final char[] PASSWORD = "checkpassword".toCharArray();
    private static final byte[] SALT = new byte[]{-34, 51, 16, 18, -34, 51, 16, 18};

    private static String encrypt(String property) throws Exception {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(1, (Key)key, new PBEParameterSpec(SALT, 20));
        return EncDecr.base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
    }

    private static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    private static String decrypt(String property) throws Exception {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(2, (Key)key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(EncDecr.base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws Exception {
        return new BASE64Decoder().decodeBuffer(property);
    }
}