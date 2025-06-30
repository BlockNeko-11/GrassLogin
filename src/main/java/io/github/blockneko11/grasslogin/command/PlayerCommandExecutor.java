package io.github.blockneko11.grasslogin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.List;

public interface PlayerCommandExecutor extends TabExecutor {
    @ApiStatus.NonExtendable
    @Override
    default boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        return onCommand((Player) sender, args);
    }

    boolean onCommand(Player player, String[] args);

    @Override
    default List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
