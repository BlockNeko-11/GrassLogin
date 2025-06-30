package io.github.blockneko11.grasslogin.command;

import io.github.blockneko11.grasslogin.util.Translation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.List;

/**
 * {@link TabExecutor} 的增强版。只允许玩家执行的命令。
 */
public interface PlayerCommandExecutor extends TabExecutor {
    @ApiStatus.NonExtendable
    @Override
    default boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Translation.tr("command.only_player_can_use"));
            return false;
        }

        return onCommand((Player) sender, args);
    }

    /**
     * 命令执行逻辑。
     * @param player 玩家
     * @param args 参数
     * @return 命令执行结果
     */
    boolean onCommand(Player player, String[] args);

    /**
     * Tab 补全逻辑。
     * @param sender 命令发送者
     * @param command 执行的命令
     * @param alias 发送者所使用的命令别名
     * @param args 参数
     * @return 补全结果（一个 {@link List} 对象）
     */
    @Override
    default List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
