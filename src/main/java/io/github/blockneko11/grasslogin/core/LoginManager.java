package io.github.blockneko11.grasslogin.core;

import java.util.ArrayList;
import java.util.List;

public final class LoginManager {
    private static final List<String> loggedPlayers = new ArrayList<>();

    public static boolean isLogged(String playerName) {
        return loggedPlayers.contains(playerName.toLowerCase());
    }

    public static void addLogged(String playerName) {
        if (!isLogged(playerName)) {
            loggedPlayers.add(playerName.toLowerCase());
        }
    }

    public static void removeLogged(String playerName) {
        loggedPlayers.remove(playerName.toLowerCase());
    }
}
