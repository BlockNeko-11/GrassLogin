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

public final class RegisterCommand implements PlayerCommandExecutor {
    @Override
    public boolean onCommand(Player player, String[] args) {
        if (AuthManager.isAuthed(player.getName())) {
            player.sendMessage(Translation.tr("command.register.already_registered"));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Translation.tr("command.register.no_password"));
            return false;
        }

        if (args.length == 1) {
            player.sendMessage(Translation.tr("command.register.not_repeat_password"));
            return false;
        }

        if (args.length > 2) {
            return false;
        }

        if (!args[0].equals(args[1])) {
            player.sendMessage(Translation.tr("command.register.password_not_match"));
            return false;
        }

        player.sendMessage(Translation.tr("command.register.processing"));

        GrassLoginPlugin.getScheduler().runTaskAsynchronously(GrassLoginPlugin.getInstance(), () -> {
            try {
                String name = player.getName();

                if (GrassLoginPlugin.getPluginConfig().getBoolean("encryption.usernameEncryption", false)) {
                    name = CryptUtil.encrypt(name);

                    if (name == null) {
                        if (GrassLoginPlugin.getPluginConfig().getBoolean("encryption.usernameEncryptionFallback", true)) {
                            name = player.getName();
                        } else {
                            player.sendMessage(Translation.tr("command.register.internal_error"));
                            GrassLoginPlugin.getLog().severe(Translation.tr("command.register.internal_error_log", player.getName(), "用户名加密失败"));
                            return;
                        }
                    }
                }

                String pwd = CryptUtil.encrypt(args[0]);

                if (pwd == null) {
                    player.sendMessage(Translation.tr("command.register.internal_error"));
                    GrassLoginPlugin.getLog().severe(Translation.tr("command.register.internal_error_log", player.getName(), "密码加密失败"));
                    return;
                }

                PlayerAuthData data = new PlayerAuthData(name, pwd);
                GrassLoginPlugin.getSql().insert(data);
                AuthManager.addAuthed(player.getName());
                player.sendMessage(Translation.tr("command.register.success"));
                GrassLoginPlugin.getLog().info(Translation.tr("command.register.success_log", player.getName()));
            } catch (SQLException e) {
                player.sendMessage(Translation.tr("command.register.internal_error"));
                GrassLoginPlugin.getLog().severe(Translation.tr("command.register.internal_error_log", player.getName(), e));
            }
        });

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2) {
            return Collections.singletonList(Translation.tr("command.register.tab_complete.repeat_password"));
        } else if (args.length == 1) {
            return Collections.singletonList(Translation.tr("command.register.tab_complete.password"));
        }

        return Collections.emptyList();
    }
}
