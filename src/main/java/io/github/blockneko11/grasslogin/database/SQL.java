package io.github.blockneko11.grasslogin.database;

import io.github.blockneko11.grasslogin.core.PlayerAuthData;
import io.github.blockneko11.simpledbc.api.Database;

import java.sql.*;

/**
 * 数据库工具类。
 */
public final class SQL {
    private final Database database;

    public SQL(Database database) {
        this.database = database;
    }

    /**
     * 初始化数据库。
     * @throws SQLException 连接数据库时抛出的异常。
     */
    public void initializeDB() throws SQLException, ClassNotFoundException {
        database.connect();
    }

    /**
     * 创建数据库表。
     * @throws SQLException 创建数据库表时抛出的异常。
     */
    public void createTable() throws SQLException {
        this.database.execute("CREATE TABLE IF NOT EXISTS grasslogin_accounts (name VARCHAR(64) PRIMARY KEY NOT NULL, pwd TEXT NOT NULL);");
    }

    /**
     * 插入 {@link PlayerAuthData} 数据。
     * @param data {@link PlayerAuthData} 对象
     * @throws SQLException 插入数据时抛出的异常。
     */
    public void insert(PlayerAuthData data) throws SQLException {
        this.database.execute(
                "INSERT INTO grasslogin_accounts (name, pwd) VALUES (?, ?);",
                data.getName(),
                data.getPwd()
        );
    }

    /**
     * 修改 {@link PlayerAuthData} 数据。
     * @param data {@link PlayerAuthData} 对象
     * @throws SQLException 修改数据时抛出的异常。
     */
    public void modify(PlayerAuthData data) throws SQLException {
        this.database.execute(
                "UPDATE grasslogin_accounts SET pwd = ? WHERE name = ?;",
                data.getPwd(),
                data.getName()
        );
    }

    /**
     * 根据玩家名获取 {@link PlayerAuthData} 数据。
     * @param name 玩家名
     * @return {@link PlayerAuthData} 对象
     * @throws SQLException 获取数据时抛出的异常。
     */
    public PlayerAuthData get(String name) throws SQLException {
        Connection connection = this.database.getConnection();
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
}
