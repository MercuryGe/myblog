package services;

import dao.UserInfoDao;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class RegServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int succ = 0;
        String msg ="";
        // 1、获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 2、非空校验
        if(username == "" || username == null){
            msg = "参数不全";
        }
        if(password == "" || password == null){
            msg = "参数不全";
        }
        // 3、业务逻辑处理（操作数据库添加用户）
        UserInfoDao userInfoDao = null;
        try {
            userInfoDao = new UserInfoDao();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            succ = userInfoDao.add(username,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // 4、返回结果
        HashMap<String,Object> result = new HashMap<>();
        result.put("succ", succ);
        result.put("msg", msg);
        ResultJSONUtils.write(response,result);
    }
}
