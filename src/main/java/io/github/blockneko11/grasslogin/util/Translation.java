package io.github.blockneko11.grasslogin.util;

import com.google.gson.Gson;
import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class Translation {
    public static final String DEFAULT_LANGUAGE = "en_us";
    private static final Gson gson = new Gson();
    private static Map<String, String> translations = new HashMap<>();

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

    public static boolean isPresent(String key) {
        return translations.containsKey(key);
    }

    public static String tr(String key) {
        return trOrDefault(key, key);
    }

    public static String trOrDefault(String key, String def) {
        if (!isPresent(key)) {
            return def;
        }

        return ChatColor.translateAlternateColorCodes('&', translations.get(key));
    }

    public static String tr(String key, Object... args) {
        return trOrDefault(key, key, args);
    }

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

    public static void shutdown() {
        translations.clear();
    }
}
