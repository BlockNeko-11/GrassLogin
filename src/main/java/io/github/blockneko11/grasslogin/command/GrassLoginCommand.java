package io.github.blockneko11.grasslogin.command;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import io.github.blockneko11.grasslogin.util.Translation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GrassLoginCommand implements TabExecutor {
    private static final List<String> helpMessages = Arrays.asList(
            Translation.tr("command.grasslogin.help.header"),
            Translation.tr("command.grasslogin.help.0"),
            Translation.tr("command.grasslogin.help.1"),
            Translation.tr("command.grasslogin.help.2"),
            Translation.tr("command.grasslogin.help.3"),
            Translation.tr("command.grasslogin.help.ending")
    );

    private static void printHelp(CommandSender sender) {
        for (String message : helpMessages) {
            sender.sendMessage(message);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            printHelp(sender);
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "reload": {
                if (!sender.hasPermission("grasslogin.command.grasslogin.reload")) {
                    sender.sendMessage(Translation.tr("command.no_permission"));
                    return false;
                }

                GrassLoginPlugin.getInstance().reload();
                break;
            }
            case "help": {
                if (!sender.hasPermission("grasslogin.command.grasslogin.help")) {
                    sender.sendMessage(Translation.tr("command.no_permission"));
                    return false;
                }

                printHelp(sender);
                break;
            }
            case "version": {
                if (!sender.hasPermission("grasslogin.command.grasslogin.version")) {
                    sender.sendMessage(Translation.tr("command.no_permission"));
                    return false;
                }

                sender.sendMessage(Translation.tr("command.grasslogin.version", GrassLoginPlugin.getInstance().getDescription().getVersion()));
                break;
            }
            default: {
                printHelp(sender);
                break;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "help", "version");
        }

        return new ArrayList<>();
    }
}
