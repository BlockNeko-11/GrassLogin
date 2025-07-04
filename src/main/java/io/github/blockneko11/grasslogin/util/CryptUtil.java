package io.github.blockneko11.grasslogin.util;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import org.apache.commons.codec.binary.Hex;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * 加密工具类。
 */
public final class CryptUtil {
    /**
     * 所有支持的加密方法。
     */
    private static final List<String> methods = Arrays.asList("MD5", "SHA-1", "SHA-256", "SHA-512");

    /**
     * 根据配置文件指定的加密方法，加密字符串。
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
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
