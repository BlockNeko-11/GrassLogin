package io.github.blockneko11.grasslogin.database;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import io.github.blockneko11.grasslogin.core.PlayerAuthData;

import java.sql.*;

/**
 * 数据库抽象类。
 */
public abstract class SQL {
    /**
     * 初始化数据库。
     * @throws SQLException 连接数据库时抛出的异常。
     */
    public abstract void initializeDB() throws SQLException;

    /**
     * 获取数据库连接。
     * @return 数据库连接。
     * @throws SQLException 创建数据库连接时抛出的异常。
     */
    public abstract Connection getConnection() throws SQLException;

    /**
     * 关闭数据库连接。
     * @throws SQLException 关闭数据库连接时抛出的异常。
     */
    public void closeConnection() throws SQLException {
        Connection connection = this.getConnection();

        if (connection == null || connection.isClosed()) {
            return;
        }

        connection.close();
    }

    /**
     * 创建数据库表。
     * @throws SQLException 创建数据库表时抛出的异常。
     */
    public void createTable() throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS grasslogin_accounts (name VARCHAR(64) PRIMARY KEY NOT NULL, pwd TEXT NOT NULL);");
        ps.executeUpdate();
        ps.close();
        this.closeConnection();
    }

    /**
     * 插入 {@link PlayerAuthData} 数据。
     * @param data {@link PlayerAuthData} 对象
     * @throws SQLException 插入数据时抛出的异常。
     */
    public void insert(PlayerAuthData data) throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO grasslogin_accounts (name, pwd) VALUES (?, ?);");
        ps.setString(1, data.getName());
        ps.setString(2, data.getPwd());
        ps.executeUpdate();
        ps.close();
        this.closeConnection();
    }

    /**
     * 修改 {@link PlayerAuthData} 数据。
     * @param data {@link PlayerAuthData} 对象
     * @throws SQLException 修改数据时抛出的异常。
     */
    public void modify(PlayerAuthData data) throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE grasslogin_accounts SET pwd = ? WHERE name = ?;");
        ps.setString(1, data.getPwd());
        ps.setString(2, data.getName());
        ps.executeUpdate();
        ps.close();
        this.closeConnection();
    }

    /**
     * 根据玩家名获取 {@link PlayerAuthData} 数据。
     * @param name 玩家名
     * @return {@link PlayerAuthData} 对象
     * @throws SQLException 获取数据时抛出的异常。
     */
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

    /**
     * 根据配置文件指定的数据库类型，创建 {@link SQL} 对象。
     * @return {@link SQL} 对象
     */
    public static SQL create() {
        switch (GrassLoginPlugin.getPluginConfig().getString("database.type", "mysql").toLowerCase()) {
            case "mysql": return new MySQL();
            case "sqlite": return new SQLite();
            default: throw new IllegalArgumentException("Invalid database type");
        }
    }
}
