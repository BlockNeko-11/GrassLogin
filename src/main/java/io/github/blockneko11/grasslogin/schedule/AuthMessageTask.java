package io.github.blockneko11.grasslogin.schedule;

import io.github.blockneko11.grasslogin.core.AuthManager;
import io.github.blockneko11.grasslogin.util.Translation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class AuthMessageTask implements Runnable {
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String name = player.getName();

            if (AuthManager.isAuthed(name)) {
                continue;
            }

            player.sendMessage(Translation.tr("message.not_authed"));
        }
    }
}
