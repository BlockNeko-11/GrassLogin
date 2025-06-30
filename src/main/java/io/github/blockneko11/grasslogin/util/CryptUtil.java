package io.github.blockneko11.grasslogin.util;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import org.apache.commons.codec.binary.Hex;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public final class CryptUtil {
    private static final List<String> methods = Arrays.asList("MD5", "SHA-1", "SHA-256", "SHA-512");

    @Nullable
    public static String encrypt(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance(GrassLoginPlugin.getPluginConfig().getString("encryption.method", "SHA-512"));
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
