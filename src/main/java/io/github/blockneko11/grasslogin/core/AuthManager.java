package io.github.blockneko11.grasslogin.core;

import java.util.ArrayList;
import java.util.List;

public final class AuthManager {
    private static final List<String> authedPlayers = new ArrayList<>();

    public static boolean isAuthed(String playerName) {
        return authedPlayers.contains(playerName.toLowerCase());
    }

    public static void addAuthed(String playerName) {
        if (!isAuthed(playerName)) {
            authedPlayers.add(playerName.toLowerCase());
        }
    }

    public static void removeAuthed(String playerName) {
        authedPlayers.remove(playerName.toLowerCase());
    }
}
