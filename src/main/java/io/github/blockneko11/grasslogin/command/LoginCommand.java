package io.github.blockneko11.grasslogin.command;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import io.github.blockneko11.grasslogin.core.AuthManager;
import io.github.blockneko11.grasslogin.core.PlayerAuthData;
import io.github.blockneko11.grasslogin.util.CryptUtil;
import io.github.blockneko11.grasslogin.util.Translation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class LoginCommand implements PlayerCommandExecutor {
    @Override
    public boolean onCommand(Player player, String[] args) {
        if (AuthManager.isAuthed(player.getName())) {
            player.sendMessage(Translation.tr("command.login.already_authed"));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Translation.tr("command.login.no_password"));
            return false;
        }

        GrassLoginPlugin.getScheduler().runTaskAsynchronously(GrassLoginPlugin.getInstance(), () -> {
            try {
                PlayerAuthData data = GrassLoginPlugin.getSql().get(player.getName());

                if (data == null) {
                    player.sendMessage(Translation.tr("command.login.not_registered"));
                    return;
                }

                if (Objects.equals(CryptUtil.encrypt(args[0]), data.getPwd().trim())) {
                    AuthManager.addAuthed(player.getName());
                    player.sendMessage(Translation.tr("command.login.success"));
                    GrassLoginPlugin.getLog().info(Translation.tr("command.login.success_log", player.getName()));
                } else {
                    player.sendMessage(Translation.tr("command.login.wrong_password"));
                }

            } catch (SQLException e) {
                player.sendMessage(Translation.tr("command.login.internal_error"));
                GrassLoginPlugin.getLog().severe(Translation.tr("command.login.internal_error_log", player.getName(), e));
            }
        });
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList(Translation.tr("command.login.tab_complete.password"));
        }

        return Collections.emptyList();
    }
}
