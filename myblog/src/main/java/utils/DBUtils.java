package utils;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 用来连接数据库
public class DBUtils {

    private  static MysqlDataSource dataSource = null;

    // 连接数据库
    public static Connection getConnection() throws SQLException {
        if(dataSource == null){
            // 第一次调用
            dataSource = new MysqlDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/java18blog?charactinEncoding=utf-8&useSSL=true");
            dataSource.setUser("root");
            dataSource.setPassword("123456");
        }
        return dataSource.getConnection();
    }

    // 关闭数据库
    public static void close(Connection connection,
                             PreparedStatement statement,
                             ResultSet resultSet) throws SQLException {
        if(resultSet != null) resultSet.close();
        if(statement != null) statement.close();
        if(connection != null) connection.close();
    }
}
