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
        if (this.reload(sender, args) ||
                this.help(sender, args) ||
                this.version(sender, args)) {
            return true;
        }

        printHelp(sender);
        return false;
    }

    private boolean reload(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            GrassLoginPlugin.getInstance().reload();
            sender.sendMessage(Translation.tr("command.grasslogin.reload"));
            return true;
        }

        return false;
    }

    private boolean help(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            printHelp(sender);
            return true;
        }

        return false;
    }

    private boolean version(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(Translation.tr("command.grasslogin.version", GrassLoginPlugin.getInstance().getDescription().getVersion()));
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "help", "version");
        }

        return new ArrayList<>();
    }
}
