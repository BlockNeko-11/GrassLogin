package io.github.blockneko11.grasslogin.database;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import io.github.blockneko11.grasslogin.core.PlayerAuthData;

import java.sql.*;

public abstract class SQL {
    public abstract void initializeDB() throws SQLException;

    public abstract Connection getConnection() throws SQLException;

    public void closeConnection() throws SQLException {
        Connection connection = this.getConnection();

        if (connection == null || connection.isClosed()) {
            return;
        }

        connection.close();
    }

    public void createTable() throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS grasslogin_accounts (name VARCHAR(64) PRIMARY KEY NOT NULL, pwd TEXT NOT NULL);");
        ps.executeUpdate();
        ps.close();
        this.closeConnection();
    }

    public void insert(PlayerAuthData data) throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO grasslogin_accounts (name, pwd) VALUES (?, ?);");
        ps.setString(1, data.getName());
        ps.setString(2, data.getPwd());
        ps.executeUpdate();
        ps.close();
        this.closeConnection();
    }

    public void modify(PlayerAuthData data) throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE grasslogin_accounts SET pwd = ? WHERE name = ?;");
        ps.setString(1, data.getPwd());
        ps.setString(2, data.getName());
        ps.executeUpdate();
        ps.close();
        this.closeConnection();
    }

    public PlayerAuthData get(String name) throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM grasslogin_accounts WHERE name = ?;");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return null;
        }

        return new PlayerAuthData(
                rs.getString("name"),
                rs.getString("pwd")
        );
    }

//    public void delete(String name) throws SQLException {
//        Connection connection = this.getConnection();
//        PreparedStatement ps = connection.prepareStatement("DELETE FROM grasslogin_accounts WHERE name = ?");
//        ps.setString(1, name);
//        ps.executeUpdate();
//        ps.close();
//        connection.close();
//    }

    public static SQL create() {
        switch (GrassLoginPlugin.getPluginConfig().getString("database.type")) {
            case "mysql": return new MySQL();
            case "sqlite": return new SQLite();
            default: throw new IllegalArgumentException("Invalid database type");
        }
    }
}
