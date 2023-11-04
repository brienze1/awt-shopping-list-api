package org.brienze.shopping.list.api.utils;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordUtils {

    public static String decodeUsername(String authorization) {
        String base64 = authorization.replace("Basic", "").replace(" ", "");

        String decodedString = new String(Base64.getDecoder().decode(base64));

        return decodedString.split(":")[0];
    }

    public static String hash(String authorization, byte[] salt) {
        return OpenBSDBCrypt.generate(authorization.toCharArray(), salt, 10);
    }

    public static String hash(String username, String password, byte[] salt) {
        String authorization = "Basic " + encode(username + ":" + password);

        return OpenBSDBCrypt.generate(authorization.toCharArray(), salt, 10);
    }

    private static String encode(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes(StandardCharsets.UTF_8));
    }
}
