package io.github.blockneko11.grasslogin.database;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SQLite extends SQL {
    private Connection connection;

    @Override
    public void initializeDB() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public synchronized Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            File dataFolder = GrassLoginPlugin.getInstance().getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            String dbPath = GrassLoginPlugin.getPluginConfig().getString("database.sqlite.path");
            String path = dataFolder.getAbsolutePath() + "/" + dbPath;

            this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        }

        return this.connection;
    }
}
