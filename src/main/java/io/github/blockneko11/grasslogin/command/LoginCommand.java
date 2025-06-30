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
import java.util.Objects;

public final class LoginCommand implements PlayerCommandExecutor {
    @Override
    public boolean onCommand(Player player, String[] args) {
        if (AuthManager.isAuthed(player.getName())) {
            player.sendMessage("§c你已经登录过了！");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage("§c请输入密码！");
            return false;
        }

        GrassLoginPlugin.getScheduler().runTaskAsynchronously(GrassLoginPlugin.getInstance(), () -> {
            try {
                PlayerAuthData data = GrassLoginPlugin.getSql().get(player.getName());

                if (data == null) {
                    player.sendMessage("§c你还未注册呢！");
                    return;
                }

                if (Objects.equals(CryptUtil.encrypt(args[0]), data.getPwd().trim())) {
                    AuthManager.addAuthed(player.getName());
                    player.sendMessage("§a登录成功！");
                    GrassLoginPlugin.getLog().info("玩家 " + player.getName() + " 登录成功！");
                } else {
                    player.sendMessage("§c密码错误！");
                }

            } catch (SQLException e) {
                player.sendMessage("§c登录失败，请询问腐竹！");
                GrassLoginPlugin.getLog().severe("玩家 " + player.getName() + " 登录失败：" + e);
            }
        });
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("密码");
        }

        return Collections.emptyList();
    }
}
