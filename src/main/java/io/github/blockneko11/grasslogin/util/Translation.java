package io.github.blockneko11.grasslogin.util;

import com.google.gson.Gson;
import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地化工具类。
 */
public final class Translation {
    /**
     * 默认语言。
     */
    public static final String DEFAULT_LANGUAGE = "en_us";

    private static final Gson gson = new Gson();

    /**
     * 翻译表。
     */
    private static Map<String, String> translations = new HashMap<>();

    /**
     * 加载翻译表。
     */
    public static void load() {
        String lang = GrassLoginPlugin.getInstance().getConfig().getString("language", DEFAULT_LANGUAGE);
        try (InputStream is = Translation.class.getClassLoader().getResourceAsStream("lang/" + lang + ".json")) {
            if (is == null) {
                GrassLoginPlugin.getLog().severe("Failed to load " + lang + " language file.");
                return;
            }

            InputStreamReader reader = new InputStreamReader(is);
            translations = (Map<String, String>) gson.fromJson(reader, Map.class);
        } catch (IOException e) {
            GrassLoginPlugin.getLog().severe("Failed to load " + lang + " language file: " + e);
        }
    }

    /**
     * 判断是否存在指定的本地化键。
     * @param key 本地化键
     * @return 存在则返回 true，否则返回 false
     */
    public static boolean isPresent(String key) {
        return translations.containsKey(key);
    }

    /**
     * 获取本地化翻译。
     * @param key 本地化键
     * @return 翻译
     */
    public static String tr(String key) {
        return trOrDefault(key, key);
    }

    /**
     * 获取本地化翻译，如果指定的本地化键不存在则返回默认值。
     * @param key 本地化键
     * @param def 默认值
     * @return 翻译
     */
    public static String trOrDefault(String key, String def) {
        if (!isPresent(key)) {
            return def;
        }

        return ChatColor.translateAlternateColorCodes('&', translations.get(key));
    }

    /**
     * 获取本地化翻译，并替换参数。
     * @param key 本地化键
     * @param args 参数
     * @return 翻译
     */
    public static String tr(String key, Object... args) {
        return trOrDefault(key, key, args);
    }

    /**
     * 获取本地化翻译，并替换参数，如果指定的本地化键不存在则返回默认值。
     * @param key 本地化键
     * @param def 默认值
     * @param args 参数
     * @return 翻译
     */
    public static String trOrDefault(String key, String def, Object... args) {
        String message = trOrDefault(key, def);

        for (int i = 0; i < args.length; i++) {
            String s = "null";
            if (args[i] != null) {
                s = args[i].toString();
            }

            message = message.replaceFirst("\\{}", s).replace("{" + i + "}", s);
        }

        return message;
    }

    /**
     * 清除翻译表。
     */
    public static void shutdown() {
        translations.clear();
    }
}
