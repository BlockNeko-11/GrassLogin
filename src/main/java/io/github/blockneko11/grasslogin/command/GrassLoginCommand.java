package io.github.blockneko11.grasslogin.command;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GrassLoginCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        return this.reload(sender, args) ||
          return      this.help(sender, args) ||
                this.version(sender, args);
    }

//    private boolean reload(CommandSender sender, String[] args) {
//        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
//            GrassLoginPlugin.getInstance().reload();
//            sender.sendMessage("GrassLogin 插件重载完成。");
//            return true;
//        }
//
//        return false;
//    }

    private boolean help(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("GrassLogin 插件帮助：");
//            sender.sendMessage("/grasslogin reload - 重载插件");
            sender.sendMessage("/grasslogin help - 显示帮助");
            sender.sendMessage("/grasslogin version - 显示版本");
            return true;
        }

        return false;
    }

    private boolean version(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("version")) {
            sender.sendMessage("GrassLogin 插件版本：" + GrassLoginPlugin.getInstance().getDescription().getVersion());
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("help", "version");
        }

        return new ArrayList<>();
    }
}
