package io.github.blockneko11.grasslogin.command;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GrassLoginCommand implements TabExecutor {
    private static final List<String> helpMessages = Arrays.asList(
            "GrassLogin 插件帮助：",
            "/grasslogin reloadConfig - 重载插件配置文件",
            "/grasslogin help - 显示本信息",
            "/grasslogin version - 显示版本",
            "/l(ogin) 密码 - 登录",
            "/reg(ister) 密码 - 注册",
            "更多帮助信息请查看插件仓库：https://github.com/BlockNeko-11/GrassLogin"
    );

    private static void printHelp(CommandSender sender) {
        for (String message : helpMessages) {
            sender.sendMessage(message);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (this.reloadConfig(sender, args) ||
                this.help(sender, args) ||
                this.version(sender, args)) {
            return true;
        }

        printHelp(sender);
        return false;
    }

    private boolean reloadConfig(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reloadconfig")) {
            GrassLoginPlugin.getInstance().reload();
            sender.sendMessage("GrassLogin 插件配置文件重载完成。");
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
            sender.sendMessage("GrassLogin 插件版本：" + GrassLoginPlugin.getInstance().getDescription().getVersion());
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reloadconfig", "help", "version");
        }

        return new ArrayList<>();
    }
}
