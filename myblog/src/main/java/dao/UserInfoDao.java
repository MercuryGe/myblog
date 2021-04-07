package dao;

import models.UserInfo;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 用来执行在数据库中的具体操作
public class UserInfoDao {

    public UserInfoDao() throws SQLException {
    }

    // 用户添加
    public int add(String username, String password) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnection();
        String sql = "insert into userinfo(username,password) value(?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,username);
        statement.setString(2,password);
        result = statement.executeUpdate();
        // 关闭数据库连接
        DBUtils.close(connection,statement,null);
        return result;
    }


    // 查询用户（登录功能使用）
    public UserInfo getUser(String username, String password) throws SQLException {
        UserInfo userInfo = new UserInfo();
        // 使用JDBC查询数据库
        Connection connection = DBUtils.getConnection();
        String sql = "select * from userinfo where username=? and password=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,username);
        statement.setString(2,password);
        // 查询数据库
        ResultSet resultSet = statement.executeQuery(); // 此处查找到的是username=root，password=123的所有用户
        while(resultSet.next()){
            userInfo.setId(resultSet.getInt("id"));
            userInfo.setUsername(resultSet.getString("username"));
            userInfo.setPassword(resultSet.getString("password"));
        }

        // 关闭数据库
        DBUtils.close(connection,statement,resultSet);
        return userInfo;
    }
}
