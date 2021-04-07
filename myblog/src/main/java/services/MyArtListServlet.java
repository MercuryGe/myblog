package services;

import dao.ArticleInfoDao;
import models.ArticleInfo;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MyArtListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int succ = -1;
        String msg = "";
        List<ArticleInfo> list = null;
        // 1、拿到前端给的参数(在Session中存放)
        HttpSession session = request.getSession(false); // 如果是true，则会在没有session的情况下自动创建一个，为了实现登录才能访问的功能，所以设置为false
        if(session == null){
            //用户未登录
            msg = "非法请求，请先登录！";
        }else{
            // 用户已登录
            UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
            // 那userInfo去查询数据库
            int uid = userInfo.getId(); // 从登陆的用户信息中，拿到该用户在数据库中的id号
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            try {
                // 根据该id号，在文章表中找到对应的列表
                list = articleInfoDao.getListByUID(uid);
                succ = 1;
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }

        // 3、构建和返回后端结果
        HashMap<String, Object> result = new HashMap<>();
        result.put("succ", succ);
        result.put("msg", msg);
        result.put("list", list);
        ResultJSONUtils.write(response,result);
    }
}
