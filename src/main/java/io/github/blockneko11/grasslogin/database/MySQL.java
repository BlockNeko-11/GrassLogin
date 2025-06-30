package io.github.blockneko11.grasslogin.database;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MySQL extends SQL {
    private Connection connection;

    @Override
    public void initializeDB() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public synchronized Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            String host = GrassLoginPlugin.getPluginConfig().getString("database.mysql.host");
            int port = GrassLoginPlugin.getPluginConfig().getInt("database.mysql.port");
            String database = GrassLoginPlugin.getPluginConfig().getString("database.mysql.database");
            String username = GrassLoginPlugin.getPluginConfig().getString("database.mysql.username");
            String password = GrassLoginPlugin.getPluginConfig().getString("database.mysql.password");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            this.connection = DriverManager.getConnection(url, username, password);
        }

        return this.connection;
    }
}
