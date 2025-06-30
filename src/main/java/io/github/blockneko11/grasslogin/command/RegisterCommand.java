package io.github.blockneko11.grasslogin.command;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import io.github.blockneko11.grasslogin.core.AuthManager;
import io.github.blockneko11.grasslogin.core.PlayerAuthData;
import io.github.blockneko11.grasslogin.util.CryptUtil;
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
            player.sendMessage("§c你已经登录了！");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage("§c请输入密码！");
            return false;
        }

        if (args.length == 1) {
            player.sendMessage("§c请输入重复密码！");
            return false;
        }

        if (args.length > 2) {
            return false;
        }

        if (!args[0].equals(args[1])) {
            player.sendMessage("§c密码不一致！");
            return false;
        }

        player.sendMessage("§e注册中...");

        GrassLoginPlugin.getScheduler().runTaskAsynchronously(GrassLoginPlugin.getInstance(), () -> {
            try {
                String pwd = CryptUtil.encrypt(args[0]);

                if (pwd == null) {
                    player.sendMessage("§c密码加密失败！");
                    return;
                }

                PlayerAuthData data = new PlayerAuthData(player.getName(), pwd);
                GrassLoginPlugin.getSql().insert(data);
                AuthManager.addAuthed(player.getName());
                player.sendMessage("§a注册成功！");
            } catch (SQLException e) {
                player.sendMessage("§c注册失败，请询问腐竹！");
                GrassLoginPlugin.getLog().severe("玩家 " + player.getName() + " 注册失败：" + e);
            }
        });

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2) {
            return Collections.singletonList("重复密码");
        } else if (args.length == 1) {
            return Collections.singletonList("密码 重复密码");
        }

        return Collections.emptyList();
    }
}
