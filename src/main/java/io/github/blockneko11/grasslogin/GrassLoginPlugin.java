package io.github.blockneko11.grasslogin;

import io.github.blockneko11.grasslogin.command.LoginCommand;
import io.github.blockneko11.grasslogin.listener.PlayerEventsListener;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class GrassLoginPlugin extends JavaPlugin {
    @Getter
    private static GrassLoginPlugin instance;
    @Getter
    private static Logger log;
    @Getter
    private static FileConfiguration pluginConfig;

    @Override
    public void onEnable() {
        instance = this;
        log = getLogger();

        saveDefaultConfig();
        pluginConfig = getConfig();

        getServer().getPluginManager().registerEvents(new PlayerEventsListener(), this);

        getCommand("login").setExecutor(new LoginCommand());

        log.info("GrassLogin plugin enabled!");
    }

    @Override
    public void onDisable() {
        saveConfig();

        log.info("GrassLogin plugin disabled!");

        instance = null;
        log = null;
    }
}
