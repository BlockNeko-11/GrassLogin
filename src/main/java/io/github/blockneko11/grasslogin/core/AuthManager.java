package io.github.blockneko11.grasslogin.core;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家登录管理器。
 */
public final class AuthManager {
    /**
     * 已经登录的玩家。
     */
    private static final Set<String> authedPlayers = ConcurrentHashMap.newKeySet();

    /**
     * 判断玩家是否已经登录。
     * @param name 玩家名称
     * @return 玩家是否已经登录
     */
    public static boolean isAuthed(String name) {
        return authedPlayers.stream().anyMatch(s -> s.equalsIgnoreCase(name));
    }

    /**
     * 添加一个已经登录的玩家。
     * @param name 玩家名称
     */
    public static void addAuthed(String name) {
        if (!isAuthed(name)) {
            authedPlayers.add(name.toLowerCase());
        }
    }

    /**
     * 移除一个已经登录的玩家。
     * @param name 玩家名称
     */
    public static void removeAuthed(String name) {
        authedPlayers.removeIf(s -> s.equalsIgnoreCase(name));
    }
}
