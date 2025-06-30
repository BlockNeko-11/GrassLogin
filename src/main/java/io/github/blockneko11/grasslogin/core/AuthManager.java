package io.github.blockneko11.grasslogin.core;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class AuthManager {
    private static final Set<String> authedPlayers = ConcurrentHashMap.newKeySet();

    public static boolean isAuthed(String playerName) {
        return authedPlayers.stream().anyMatch(name -> name.equalsIgnoreCase(playerName));
    }

    public static void addAuthed(String playerName) {
        if (!isAuthed(playerName)) {
            authedPlayers.add(playerName.toLowerCase());
        }
    }

    public static void removeAuthed(String playerName) {
        authedPlayers.removeIf(name -> name.equalsIgnoreCase(playerName));
    }
}
