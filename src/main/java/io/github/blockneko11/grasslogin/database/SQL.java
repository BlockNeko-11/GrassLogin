package io.github.blockneko11.grasslogin.database;

import io.github.blockneko11.grasslogin.core.PlayerAuthData;
import io.github.blockneko11.simpledbc.api.Database;
import io.github.blockneko11.simpledbc.api.action.table.Attribute;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        this.database.connect();
    }

    /**
     * 创建数据库表。
     * @throws SQLException 创建数据库表时抛出的异常。
     */
    public void createTable() throws SQLException {
//        this.database.update("CREATE TABLE IF NOT EXISTS grasslogin_accounts (name VARCHAR(64) PRIMARY KEY NOT NULL, pwd TEXT NOT NULL);");
//        this.database.createTable(Table.builder("grasslogin_accounts")
//                .column(Column.builder("name", "VARCHAR(64)")
//                        .addFeature(Column.Feature.PRIMARY_KEY)
//                        .addFeature(Column.Feature.NOT_NULL)
//                        .build())
//                .column(Column.builder("pwd", "TEXT")
//                        .addFeature(Column.Feature.NOT_NULL)
//                        .build())
//                .build());

        this.database.createTable("grasslogin_accounts")
                .column("name", "VARCHAR(64)", Attribute.PRIMARY_KEY, Attribute.NOT_NULL)
                .column("pwd", "TEXT", Attribute.NOT_NULL)
                .execute();
    }

    /**
     * 插入 {@link PlayerAuthData} 数据。
     * @param data {@link PlayerAuthData} 对象
     * @throws SQLException 插入数据时抛出的异常。
     */
    public void insert(PlayerAuthData data) throws SQLException {
//        this.database.update(
//                "INSERT INTO grasslogin_accounts (name, pwd) VALUES (?, ?);",
//                data.getName(),
//                data.getPwd()
//        );
        this.database.columnInsert("grasslogin_accounts")
                .value("name", data.getName())
                .value("pwd", data.getPwd())
                .execute();
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
//        Connection connection = this.database.getConnection();
//        PreparedStatement ps = connection.prepareStatement("SELECT * FROM grasslogin_accounts WHERE name = ?;");
//        ps.setString(1, name);
//        ResultSet rs = ps.executeQuery();
//        if (!rs.next()) {
//            return null;
//        }
//
//        return new PlayerAuthData(
//                rs.getString("name"),
//                rs.getString("pwd")
//        );

        try (ResultSet rs = this.database.query("SELECT * FROM grasslogin_accounts WHERE name = ?;", name)) {
            if (!rs.next()) {
                return null;
            }

            return new PlayerAuthData(
                    rs.getString("name"),
                    rs.getString("pwd")
            );
        }
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
