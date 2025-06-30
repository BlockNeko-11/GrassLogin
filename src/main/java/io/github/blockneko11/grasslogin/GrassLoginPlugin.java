package io.github.blockneko11.grasslogin;

import io.github.blockneko11.grasslogin.command.GrassLoginCommand;
import io.github.blockneko11.grasslogin.command.LoginCommand;
import io.github.blockneko11.grasslogin.command.RegisterCommand;
import io.github.blockneko11.grasslogin.database.SQL;
import io.github.blockneko11.grasslogin.listener.PlayerEventsListener;
import io.github.blockneko11.grasslogin.schedule.Tasks;
import io.github.blockneko11.grasslogin.util.Translation;
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

        Translation.load();

        if (!pluginConfig.getString("version", "").equalsIgnoreCase(getDescription().getVersion())) {
            log.severe(Translation.tr("config.version_mismatch"));
            log.severe(Translation.tr("error.process.disable"));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new PlayerEventsListener(), this);

        getCommand("grasslogin").setExecutor(new GrassLoginCommand());
        getCommand("grasslogin").setTabCompleter(new GrassLoginCommand());

        getCommand("login").setExecutor(new LoginCommand());
        getCommand("login").setTabCompleter(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("register").setTabCompleter(new RegisterCommand());

        scheduler = getServer().getScheduler();

        if (!this.connectDB()) {
            return;
        }

        Tasks.runAll();

        log.info(Translation.tr("plugin.enabled"));
    }

    @Override
    public void onDisable() {
        saveConfig();

        log.info(Translation.tr("plugin.disabled"));

        instance = null;
        log = null;
        pluginConfig = null;

        Tasks.cancelAll();

        sql = null;
        scheduler = null;

        Translation.shutdown();
    }

    private boolean connectDB() {
        sql = SQL.create();

        try {
            sql.initializeDB();
        } catch (SQLException e) {
            log.severe(Translation.tr("database.connect_failed", e));
            log.severe(Translation.tr("error.process.disable"));
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }

        try {
            sql.createTable();
        } catch (SQLException e) {
            log.severe(Translation.tr("database.init_failed", e));
            log.severe(Translation.tr("error.process.disable"));
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }

        return true;
    }

    public void reload() {
        saveConfig();
        reloadConfig();

        Tasks.cancelAll();

        if (!this.connectDB()) {
            return;
        }

        Tasks.runAll();

        log.info(Translation.tr("plugin.reloaded"));
    }
}
