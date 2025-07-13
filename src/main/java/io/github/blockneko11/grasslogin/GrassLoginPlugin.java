package io.github.blockneko11.grasslogin;

import io.github.blockneko11.grasslogin.command.GrassLoginCommand;
import io.github.blockneko11.grasslogin.command.LoginCommand;
import io.github.blockneko11.grasslogin.command.RegisterCommand;
import io.github.blockneko11.grasslogin.database.SQL;
import io.github.blockneko11.grasslogin.listener.PlayerEventsListener;
import io.github.blockneko11.grasslogin.schedule.Tasks;
import io.github.blockneko11.grasslogin.util.Translation;
import io.github.blockneko11.simpledbc.api.Database;
import io.github.blockneko11.simpledbc.impl.MySQLDatabase;
import io.github.blockneko11.simpledbc.impl.SQLiteDatabase;
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
        String type = pluginConfig.getString("database.type");
        Database database;
        switch (type.toLowerCase()) {
            case "mysql": {
                String host = pluginConfig.getString("database.mysql.host");
                String port = pluginConfig.getString("database.mysql.port");
                String username = pluginConfig.getString("database.mysql.username");
                String password = pluginConfig.getString("database.mysql.password");
                String databaseName = pluginConfig.getString("database.mysql.database");
                database = new MySQLDatabase(host + ":" + port, username, password, databaseName);
                break;
            }
            case "sqlite": {
                database = new SQLiteDatabase(pluginConfig.getString("database.sqlite.path"));
                break;
            }
            default: {
                log.severe(Translation.tr("database.invalid_type", type));
                log.severe(Translation.tr("error.process.disable"));
                getServer().getPluginManager().disablePlugin(this);
                return false;
            }
        }

        sql = new SQL(database);

        try {
            sql.initializeDB();
        } catch (SQLException | ClassNotFoundException e) {
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
