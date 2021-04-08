package dao;

import models.ArticleInfo;
import utils.DBUtils;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 操作文章列表
public class ArticleInfoDao {

    // 拿到数据库中该uid用户下的文章列表
    public List<ArticleInfo> getListByUID(int uid) throws SQLException {
        List<ArticleInfo> list = new ArrayList<>();
        Connection connection = DBUtils.getConnection();
        String sql = "select * from articleinfo where uid=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,uid);
        // 查询并返回结果
        ResultSet resultSet = statement.executeQuery();
        // 将查询的结果存放在list列表中
        while(resultSet.next()){
            ArticleInfo articleInfo = new ArticleInfo();
            articleInfo.setId(resultSet.getInt("id"));
            articleInfo.setRcount(resultSet.getInt("rcount"));articleInfo.setTitle(resultSet.getString("title"));
            articleInfo.setContent(resultSet.getString("Content"));
            articleInfo.setCreatetime(resultSet.getDate("createtime"));
            list.add(articleInfo);
        }
        return list;
    }

    // 删除文章操作
    public int del(int id) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnection();
        String sql = "delete from articleinfo where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        result = statement.executeUpdate();
        DBUtils.close(connection,statement,null);
        return result;
    }

    public ArticleInfo getArtById(int id) throws SQLException {
        ArticleInfo articleInfo = new ArticleInfo();
        if(id > 0){
            Connection connection = DBUtils.getConnection();
            String sql = "select * from articleinfo where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                articleInfo.setTitle(resultSet.getString("title"));
                articleInfo.setContent(resultSet.getString("content"));
                articleInfo.setId(resultSet.getInt("id"));
            }
            DBUtils.close(connection,statement,resultSet);
        }
        return articleInfo;
    }

    // 修改文章内容
    public int upArt(int id, String title, String content) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnection();
        String sql = "update articleinfo set title=?,content=? where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,title);
        statement.setString(2,content);
        statement.setInt(3,id);
        result = statement.executeUpdate();
        DBUtils.close(connection,statement,null);
        return result;
    }

    // 文章新增
    public int add(String title, String content, int id) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnection();
        String sql = "insert into articleinfo(title,content,uid) values(?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        statement.setString(2, content);
        statement.setInt(3, id);
        result = statement.executeUpdate();
        DBUtils.close(connection, statement, null);
        return result;
    }
}
