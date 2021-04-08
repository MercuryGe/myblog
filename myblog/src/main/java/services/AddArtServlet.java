package services;

import dao.ArticleInfoDao;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/addart")
public class AddArtServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int succ = -1;
        String msg = "";

        // 1、从前端获取参数
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        HttpSession session = request.getSession(false); // 获得前端在服务器端暂时储存的用户访问信息
        if(session != null &&session.getAttribute("userinfo") != null){
            // 2、逻辑业务处理
            UserInfo userInfo = (UserInfo) session.getAttribute("userinfo"); //得到用户信息
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();

            try {
                succ = articleInfoDao.add(title,content,userInfo.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            msg = "非登录状态，请先登录";
        }
        // 3、将结果返回给前端
        HashMap<String, Object> result = new HashMap<>();
        result.put("succ", succ);
        result.put("msg",msg);
        ResultJSONUtils.write(response,result);
    }
}
