package services;

import dao.ArticleInfoDao;
import models.ArticleInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class InitServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int succ = -1;
        String msg = "";
        // 因为要返回文章的信息，所以后端类要建立一个文章信息类，以供返回给前端内容
        ArticleInfo articleInfo = null;

        // 1、从前端获取参数
        int id = Integer.parseInt(request.getParameter("id"));
        // 2、逻辑业务处理
        if(id > 0){
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            try {
                articleInfo = articleInfoDao.getArtById(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            succ = 1;
        }else{
            msg = "无效参数";
        }
        // 3、将结果返回给前端
        HashMap<String, Object> result = new HashMap<>();
        result.put("succ", succ);
        result.put("msg",msg);
        result.put("art",articleInfo);
        ResultJSONUtils.write(response,result);
    }
}