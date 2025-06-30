package io.github.blockneko11.grasslogin;

import io.github.blockneko11.grasslogin.command.GrassLoginCommand;
import io.github.blockneko11.grasslogin.command.LoginCommand;
import io.github.blockneko11.grasslogin.command.RegisterCommand;
import io.github.blockneko11.grasslogin.database.SQL;
import io.github.blockneko11.grasslogin.listener.PlayerEventsListener;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.SQLException;
import java.util.logging.Logger;

public final class GrassLoginPlugin extends JavaPlugin {
    @Getter
    private static GrassLoginPlugin instance;
    @Getter
    private static Logger log;
    @Getter
    private static FileConfiguration pluginConfig;
    @Getter
    private static BukkitScheduler scheduler;
    @Getter
    private static SQL sql;

    @Override
    public void onEnable() {
        instance = this;
        log = getLogger();

        saveDefaultConfig();
        pluginConfig = getConfig();

        getServer().getPluginManager().registerEvents(new PlayerEventsListener(), this);

        getCommand("grasslogin").setExecutor(new GrassLoginCommand());
        getCommand("grasslogin").setTabCompleter(new GrassLoginCommand());

        getCommand("login").setExecutor(new LoginCommand());
        getCommand("login").setTabCompleter(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("register").setTabCompleter(new RegisterCommand());

        scheduler = getServer().getScheduler();

        sql = SQL.create();
        try {
            sql.initializeDB();
        } catch (SQLException e) {
            log.severe("数据库连接失败：" + e);
            log.severe("GrassLogin 插件将禁用。");
            getServer().getPluginManager().disablePlugin(this);
        }

        try {
            sql.createTable();
        } catch (SQLException e) {
            log.severe("数据库表创建失败：" + e);
            log.severe("GrassLogin 插件将禁用。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        log.info("GrassLogin 插件启用成功！");
    }

    @Override
    public void onDisable() {
        saveConfig();

        log.info("GrassLogin 插件禁用成功！");

        instance = null;
        log = null;
        pluginConfig = null;
        scheduler = null;
        sql = null;
    }

//    public void reload() {
//        saveConfig();
//        reloadConfig();
//
//        log.info("GrassLogin plugin reloaded!");
//    }
}
